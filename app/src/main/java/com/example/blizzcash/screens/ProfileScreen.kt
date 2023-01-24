package com.example.blizzcash.screens

import android.content.ContentValues
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.blizzcash.Information1
import com.example.blizzcash.Information2
import com.example.blizzcash.Screen
import com.example.blizzcash.Strings
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlin.random.Random

private var ref: DatabaseReference = database.getReference("users").child(auth.currentUser!!.uid)

@Composable
fun ProfileScreen(navController: NavHostController){
    val focusManager = LocalFocusManager.current
    Column( modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth()
        .pointerInput(Unit) {
        detectTapGestures(onTap = {
            focusManager.clearFocus()
        })},
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        var name: TextFieldValue by remember { mutableStateOf(TextFieldValue("")) }
        val maxChar = 20
        val contxt = LocalContext.current
        OutlinedTextField(
            value = name,
            label = { Text(text = "Enter Your Name") },
            onValueChange = {
                if(it.text.length <= maxChar)
                    name = it
                else
                   Toast.makeText(contxt, "Can't input more than 20 characters", Toast.LENGTH_SHORT).show()
            },
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick ={
            val user = auth.currentUser
            if(name.selection== TextRange(0,0) && name.composition==null){
                Log.d(ContentValues.TAG, "nothing")
                Toast.makeText(contxt, "Please input username", Toast.LENGTH_SHORT).show()
            }
            else{
                val information = Information2(username = name.text,pfp = Random.nextInt(0,10))
                changeInfo(information)
                Log.d(ContentValues.TAG, name.toString())
            }
            navController.navigate(route = Screen.Options.route){
                popUpTo(Screen.Profile.route){
                    inclusive = true
                }
            }
        }){
            Text("Create profile")
        }
    }
}

fun changeInfo(information:Information2){
    ref.child("username").setValue(information.username).addOnCompleteListener(){task ->
        if(task.isSuccessful){
            Log.d(ContentValues.TAG, "node is completed")
        }
        else{
            Log.d(ContentValues.TAG, "node has FAILED")
        }
    }
    ref.child("pfp").setValue(information.pfp).addOnCompleteListener(){task ->
        if(task.isSuccessful){
            Log.d(ContentValues.TAG, "node is completed")
        }
        else{
            Log.d(ContentValues.TAG, "node has FAILED")
        }
    }
}