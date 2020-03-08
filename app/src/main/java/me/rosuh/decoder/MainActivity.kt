package me.rosuh.decoder

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
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
import java.io.File
import kotlin.math.abs
import kotlin.math.sqrt


class MainActivity : AppCompatActivity() {

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
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            FilePickerManager.REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    val list = FilePickerManager.obtainData()
                    // process audio decode
                    handleDecode(list[0])
                } else {
                    Toast.makeText(this, "没有选择任何东西~", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun handleDecode(url: String) {
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
                shortBuffer.add(calculateRealVolume(pcm))
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
}

private fun MPG123.printAll() {
    println("duration ==>> $duration")
    println("numChannels ==>> ${this.numChannels}")
    println("rate ==>> ${this.rate}")
}
