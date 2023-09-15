package com.example.mindmaster.ui

import android.content.Intent
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
        Log.e("Splasscreen","Erstellt")
        // Hier wird der Splashscreen für 6 Sekunden angezeigt
        val splashDuration = 6000 //
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
