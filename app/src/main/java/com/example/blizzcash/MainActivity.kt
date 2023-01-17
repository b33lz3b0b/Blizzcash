package com.example.blizzcash

import com.example.blizzcash.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


class MainActivity: ComponentActivity(){

    lateinit var navController: NavHostController
    //var ExistentUser = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            navController = rememberNavController()
            Navigation(navController = navController)
        }
    }

}

/*@Composable
fun VerifyUser(user: String){
    if(user=="")
        OptionScreen()
}*/