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

@Composable
fun WelcomeScreen(navController: NavHostController) {
    Column( modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        WelcomeText()
        Spacer(modifier = Modifier.height(20.dp))
        WelcomeButton(navController)
    }
}

@Composable
fun WelcomeButton(navController: NavController) {
    Button(onClick={ navController.navigate(route = Screen.Options.route)},
        colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray, contentColor = Color.LightGray)
    ){
        Text("Continue")
    }
}
@Composable
fun WelcomeText(){
    androidx.compose.material3.Text(
        "Welcome to Blizzcash!",
        fontSize = 30.sp
    )
}

