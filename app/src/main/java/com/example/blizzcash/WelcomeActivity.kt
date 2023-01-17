package com.example.blizzcash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable


class WelcomeActivity: AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            WelcomeText()
        }
    }
}

@Composable
fun WelcomeText(){
    Text("Welcome to Blizzcash!")
}