package com.example.blizzcash

import android.content.ContentValues.TAG
import com.example.blizzcash.R
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.preference.PreferenceManager
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class MainActivity: ComponentActivity(){

    lateinit var navController: NavHostController
    var auth: FirebaseAuth = Firebase.auth
    private var database: DatabaseReference = Firebase.database.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            navController = rememberNavController()
            Navigation(navController = navController)
        }
    }
    public override fun onStart() {
        super.onStart()
        val pushNotificationPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ){}
        pushNotificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        pushNotificationPermissionLauncher.launch(android.Manifest.permission.ACCESS_NETWORK_STATE)

    }
}