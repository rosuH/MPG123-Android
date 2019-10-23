package me.rosuh.decoder

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*
import me.rosuh.libmpg123.MPG123
import java.nio.ShortBuffer
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            101
        )

        val url = LocalMusicUtils.loadFileData(this)
        val decoder = MPG123(url)

        btn.setOnClickListener {
            Thread {
                nextFrames(decoder)
            }.start()
            decoder.all()
        }
    }

    private fun nextFrames(decoder: MPG123) {
        val pcm = ShortArray(2304)
        val buffer = ShortBuffer.allocate((1 shl 20) * 5)
        var count = 0

        while (true) {
            val samples = decoder.readFrame(pcm)
            if (samples == 0 || samples == -1 && decoder.isStreamComplete) {
                break
            } else if (samples == -2) {
                break
            } else {
                Log.i("Audio", pcm.contentToString())
                buffer.clear()
                count++
            }
        }
    }
}

private fun MPG123.all() {
    println("duration ==>> $duration")
    println("numChannels ==>> ${this.numChannels}")
    println("rate ==>> ${this.rate}")
}
