package com.example.mindmaster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e("MainActivity","Erstellt")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }



}