package com.example.blizzcash

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun WelcomeScreen(navController: NavHostController) {
    var auth: FirebaseAuth = Firebase.auth
    val user = Firebase.auth.currentUser
    Column( modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        if (user != null) {
            WelcomeText("Welcome back, " + user.displayName.toString())
            Spacer(modifier = Modifier.height(20.dp))
            WelcomeButton(navController, "Continue")
        } else {
            WelcomeText("Welcome to Blizzcash!")
            Spacer(modifier = Modifier.height(20.dp))
            WelcomeButton(navController, "Sign Up/Log In")
        }
    }
}

@Composable
fun WelcomeButton(navController: NavController, next:String) {
    Button(onClick={ navController.navigate(route = Screen.Options.route)
        val user = Firebase.auth.currentUser
        if (user != null) {
            navController.navigate(route = Screen.Home.route)
        } else {
            navController.navigate(route = Screen.EmailSignUp.route)
        } },
        colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray, contentColor = Color.LightGray)
    ){
        Text(next)
    }
}
@Composable
fun WelcomeText(greeting:String){
    androidx.compose.material3.Text(
        greeting,
        fontSize = 30.sp
    )
}

