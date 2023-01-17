package com.example.blizzcash

import com.example.blizzcash.R
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.lang.Boolean


class WelcomeActivity: AppCompatActivity(){
    var prevStarted = "yes"
    override fun onResume() {
        super.onResume()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    fun MoveNext() {
        // use an intent to travel from one activity to another.
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}