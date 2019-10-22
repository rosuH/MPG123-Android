package me.rosuh.decoder

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 101)

        btn.setOnClickListener {
            val url = LocalMusicUtils.loadFileData(this)
            Log.i("MainActivity", url)
            tv.text = me.rosuh.libmpg123.MPG123(url).duration.toString()
        }
    }
}
