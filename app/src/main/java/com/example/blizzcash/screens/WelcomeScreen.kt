package com.example.blizzcash.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.blizzcash.Screen
import com.example.blizzcash.theme.MainAppTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

/*private var auth: FirebaseAuth = Firebase.auth
private var database = FirebaseDatabase.getInstance()
lateinit var username : String*/

@Composable
fun WelcomeScreen(navController: NavHostController) {
    var auth: FirebaseAuth = Firebase.auth
    val user = Firebase.auth.currentUser
    MainAppTheme() {
        Column( modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            if (user != null) {
                //var ref: DatabaseReference = database.getReference("users").child(user!!.uid)
                WelcomeText("Welcome back, ")
                Spacer(modifier = Modifier.height(20.dp))
                WelcomeButton(navController, "Continue")
            } else {
                WelcomeText("Welcome to Blizzcash!")
                Spacer(modifier = Modifier.height(20.dp))
                WelcomeButton(navController, "Sign Up/Log In")
            }
        }
    }

}

@Composable
fun WelcomeButton(navController: NavController, next:String) {
    MainAppTheme() {
        Button(onClick={
            val user = Firebase.auth.currentUser
            if (user != null) {
                navController.navigate(route = "home")
            } else {
                navController.navigate(route = "signup")
            } },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary, contentColor = MaterialTheme.colorScheme.onPrimary)
        ){
            Text(next)
        }
    }

}
@Composable
fun WelcomeText(greeting:String){
    MainAppTheme() {
        Text(
            greeting,
            fontSize = 30.sp,
            color = MaterialTheme.colorScheme.onBackground
        )
    }

}

