package me.rosuh.decoder

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.Telephony
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.robinhood.spark.SparkAdapter
import com.robinhood.spark.animation.MorphSparkAnimator
import kotlinx.android.synthetic.main.activity_main.*
import me.rosuh.filepicker.bean.FileItemBeanImpl
import me.rosuh.filepicker.config.AbstractFileFilter
import me.rosuh.filepicker.config.FilePickerManager
import me.rosuh.filepicker.filetype.AudioFileType
import me.rosuh.libmpg123.MPG123
import me.rosuh.libmpg123.SeekMode
import java.io.File
import kotlin.math.abs
import kotlin.math.sqrt


class MainActivity : AppCompatActivity() {

    private var url: String = ""

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // request permission
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            101
        )
        // show file picker
        btn.setOnClickListener {
            FilePickerManager
                .from(this)
                .filter(object : AbstractFileFilter() {
                    override fun doFilter(listData: ArrayList<FileItemBeanImpl>): ArrayList<FileItemBeanImpl> {
                        return ArrayList(listData.filter { item ->
                            ((item.isDir) || (item.fileType is AudioFileType))
                        })
                    }
                })
                .enableSingleChoice()
                .forResult(FilePickerManager.REQUEST_CODE)
        }

        btn_seek.setOnClickListener {
            if (url.isEmpty()) {
                Toast.makeText(this, "Choose file first", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            handleDecodeWithSeek(url, false)
        }

        btn_seek_frame.setOnClickListener {
            if (url.isEmpty()) {
                Toast.makeText(this, "Choose file first", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            handleDecodeWithSeek(url, true)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            FilePickerManager.REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    val list = FilePickerManager.obtainData()
                    url = list.first()
                    // process audio decode
                    handleDecode(url)
                } else {
                    Toast.makeText(this, "没有选择任何东西~", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun handleDecode(
        url: String,
    ) {
        val decoder = MPG123(url)
        decoder.printAll()
        Thread {
            runOnUiThread { tv?.text = "We are loading now" }
            val list = decode(decoder, File(url).length())
            runOnUiThread {
                tv?.text = "Finish"
                sparkview.sparkAnimator = MorphSparkAnimator()
                sparkview.adapter = MyAdapter(list)
            }
        }.start()
    }

    private fun handleDecodeWithSeek(
        url: String,
        isSeekFrame: Boolean
    ) {
        val decoder = MPG123(url)
        decoder.printAll()
        Thread {
            runOnUiThread { tv?.text = "We are loading now" }
            if (isSeekFrame) {
                val tf = decoder.getTimeFrame((decoder.duration / 2).toDouble())
                Log.i(TAG, "getTimeFrame ==>> $tf")
                val seekResult = decoder.seekFrame(tf.toFloat(), SeekMode.SEEK_SET)
                Log.i(TAG, "seekFrame ==>> $seekResult, current pos = ${decoder.position}")
            } else {
                val seekResult = decoder.seek((decoder.duration / 2))
                Log.i(TAG, "seek sec ==>> $seekResult, current pos = ${decoder.position}")
            }
            val list = decode(decoder, File(url).length())
            runOnUiThread {
                tv?.text = "Finish"
                sparkview_seek.sparkAnimator = MorphSparkAnimator()
                sparkview_seek.adapter = MyAdapter(list)
            }
            decoder.close()
        }.start()
    }


    @SuppressLint("SetTextI18n")
    private fun decode(decoder: MPG123, length: Long): ArrayList<Int> {
        val shortBuffer = ArrayList<Int>((length * 4).toInt())
        var count = 0
        var percent: Double
        while (true) {
            val pcm: ShortArray? = decoder.readFrame()
            if (pcm == null || pcm.isEmpty()) {
                break
            } else {
                count++
                shortBuffer.add(convertToWaveForm(pcm))
                if (count > 50 && count % 50 == 0) {
                    percent = (count).toDouble() / (decoder.duration * 1000 / 26) * 100
                    runOnUiThread {
                        tv?.text = "${percent.toInt()}%"
                    }
                }
            }
        }
        return shortBuffer
    }

    /**
     * Just convert frame data to wave form.
     * Not that important, you can create your own.
     */
    private fun calculateRealVolume(array: ShortArray): Int {
        var sum = 0.0
        array.forEachIndexed { index, sh ->
            if (index != array.size - 1) {
                sum += (sh.toInt() * sh.toInt()).toDouble()
            }
        }
        return sqrt(sum).toInt()
    }

    private fun convertToWaveForm(array: ShortArray): Int {
        var j: Int
        var gain: Int = -1
        var value = 0
        array.forEach {
            value += abs(it.toInt())
            if (gain < value) {
                gain = value
            }
        }
        return sqrt(gain.toDouble()).toInt()
    }

    internal class MyAdapter(private val yData: ArrayList<Int>) : SparkAdapter() {
        override fun getCount(): Int {
            return yData.size
        }

        override fun getItem(index: Int): Any {
            return yData[index]
        }

        override fun getY(index: Int): Float {
            return yData[index].toFloat()
        }

    }

    companion object {
        const val TAG = "MPG123"
    }
}

private fun MPG123.printAll() {
    Log.i("MPG123", "duration ==>> $duration")
    Log.i("MPG123", "numChannels ==>> ${this.numChannels}")
    Log.i("MPG123", "rate ==>> ${this.rate}")
}
