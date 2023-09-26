package com.example.mindmaster.ui

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.mindmaster.MainActivity
import com.example.mindmaster.R


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.mindmaster.R.layout.activity_splashscreen)



        val mediaplayer = MediaPlayer.create(this,R.raw.audio2)
        mediaplayer.start()


        val splashDuration = 8000 //
        val animation = AnimationUtils.loadAnimation(this, R.anim.rotate_scale)
        val imageView = findViewById<ImageView>(R.id.splashScreenIV)


        // Hinzufügen des "accelerate-decelerate" Interpolators zur Drehungsanimation
        animation.interpolator = AccelerateDecelerateInterpolator()
        imageView.startAnimation(animation)





        Handler().postDelayed(Runnable { // Starte die Haupt-Activity
            val mainIntent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(mainIntent)
            finish()
        }, splashDuration.toLong())
    }
}
