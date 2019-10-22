package me.rosuh.decoder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn.setOnClickListener {
            val url = LocalMusicUtils.loadFileData(this)
            Log.i("MainActivity", url)
            tv.text = MPG123(url).duration.toString()
        }
    }
}
