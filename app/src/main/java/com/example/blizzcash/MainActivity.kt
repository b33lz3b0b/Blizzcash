package com.example.blizzcash

import com.example.blizzcash.R
import android.content.Intent
import android.os.Bundle
import androidx.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity


class MainActivity: AppCompatActivity(){
    val prefs = PreferenceManager.getDefaultSharedPreferences(baseContext)
    val previouslyLogged = prefs.getString("R.string.user_name", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (previouslyLogged==""){
            Welcome()
        }
        else{
            MoveHome()
        }
    }

    fun MoveHome() {
        // use an intent to travel from one activity to another.
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    fun Welcome() {
        // use an intent to travel from one activity to another.
        val intent = Intent(this, WelcomeActivity::class.java)
        startActivity(intent)
    }

}