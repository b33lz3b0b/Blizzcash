package com.example.blizzcash.screens

import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.blizzcash.Information3
import com.example.blizzcash.Screen
import com.example.blizzcash.theme.MainAppTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

/*private var auth: FirebaseAuth = Firebase.auth
private var database = FirebaseDatabase.getInstance()
private var ref: DatabaseReference = database.getReference("users").child(auth.currentUser!!.uid)*/

@Composable
fun OptionsScreen(navController: NavHostController) {
    MainAppTheme() {
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

}

@Composable
fun SelectCourseText(){
    Text(
        "Which course would you like to choose?",
        style = MaterialTheme.typography.displayLarge,
        modifier = Modifier.padding(10.dp),
        textAlign = TextAlign.Center
    )
}
@Composable
fun SelectCourseButton(course_type: String, navController: NavController){
    Button(onClick= {
        val information = Information3(course = course_type,level = 1, lesson = 1)
        //changeInfo(information)
        navController.navigate(route = Screen.Home.route){
            popUpTo(Screen.Options.route) {inclusive = true}
        }},
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.DarkGray,
            contentColor = Color.LightGray
        )
    ){
        Text(course_type)
    }
}

/*fun changeInfo(information: Information3){
    ref.child("course").setValue(information.course).addOnCompleteListener(){task ->
        if(task.isSuccessful){
            Log.d(ContentValues.TAG, "node is completed")
        }
        else{
            Log.d(ContentValues.TAG, "node has FAILED")
        }
    }
    ref.child("level").setValue(information.level).addOnCompleteListener(){task ->
        if(task.isSuccessful){
            Log.d(ContentValues.TAG, "node is completed")
        }
        else{
            Log.d(ContentValues.TAG, "node has FAILED")
        }
    }
    ref.child("lesson").setValue(information.lesson).addOnCompleteListener(){task ->
        if(task.isSuccessful){
            Log.d(ContentValues.TAG, "node is completed")
        }
        else{
            Log.d(ContentValues.TAG, "node has FAILED")
        }
    }
}*/
