package com.example.blizzcash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
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
import androidx.navigation.compose.rememberNavController

@Composable
fun OptionsScreen(navController: NavHostController) {
    Column( modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        SelectCourseText()
        Spacer(modifier = Modifier.height(20.dp))
        SelectCourseButton("Allowance", navController)
        Spacer(modifier = Modifier.height(20.dp))
        SelectCourseButton("Salary", navController)
        Spacer(modifier = Modifier.height(20.dp))
        SelectCourseButton("Entrepreneur", navController)
    }
}

@Composable
fun SelectCourseText(){
    androidx.compose.material3.Text(
        "Which course would you like to choose?",
        fontSize = 30.sp
    )
}
@Composable
fun SelectCourseButton(course_type: String, navController: NavController){
    Button(onClick= { navController.navigate(route = Screen.Profile.route)},
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.DarkGray,
            contentColor = Color.LightGray
        )
    ){
        Text("$course_type")
    }
}

