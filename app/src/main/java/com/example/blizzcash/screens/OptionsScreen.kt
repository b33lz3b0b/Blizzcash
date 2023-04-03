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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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

private var auth: FirebaseAuth = Firebase.auth
private var database = FirebaseDatabase.getInstance()
private var ref: DatabaseReference = database.getReference("users").child(auth.currentUser!!.uid)
var usernamename = "user"

@Composable
fun OptionsScreen(navController: NavHostController) {

    MainAppTheme() {
        Column( modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            SelectCourseText()
            Spacer(modifier = Modifier.height(20.dp))
            SelectCourseButton("Allowance", navController, true)
            Spacer(modifier = Modifier.height(40.dp))
            SelectCourseButton("Salary", navController, true)
            Spacer(modifier = Modifier.height(40.dp))
            SelectCourseButton("Entrepreneur", navController, false)
        }
    }

}

@Composable
fun SelectCourseText(){
    Text(
        "Which course would you like to choose?",
        style = MaterialTheme.typography.displayLarge,
        modifier = Modifier.padding(10.dp),
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onBackground
    )
}
@Composable
fun SelectCourseButton(course_type: String, navController: NavController, enabled: Boolean){
    changeScoresInfo(IntArray(21){0})
    Button(enabled = enabled, onClick= {
        val information = Information3(course = course_type, level = 0, lesson = 1)
        changeInfo(information)
        navController.navigate(route = Screen.Home.route){
            popUpTo(Screen.Options.route) {inclusive = true}
        }},
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        modifier = Modifier.height(150.dp).width(350.dp),
    ){
        Text(course_type, style = MaterialTheme.typography.bodyMedium)
    }
}

fun changeInfo(information: Information3){
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
}

fun changeScoresInfo(information:IntArray){
    for(i in 0..20){
        ref.child("scores").child("$i").setValue(information[i]).addOnCompleteListener(){task ->
            if(task.isSuccessful){
                Log.d(ContentValues.TAG, "node is completed")
            }
            else{
                Log.d(ContentValues.TAG, "node has FAILED")
            }
        }
    }
}