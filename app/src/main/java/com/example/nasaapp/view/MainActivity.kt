package com.example.nasaapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nasaapp.R
import com.example.nasaapp.view.picture.PODFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, PODFragment.newInstance())
                    .commitNow()
        }
    }
}