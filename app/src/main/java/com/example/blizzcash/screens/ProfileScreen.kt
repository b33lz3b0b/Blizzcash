package com.example.blizzcash.screens

import android.content.ContentValues
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.blizzcash.Information1
import com.example.blizzcash.Information2
import com.example.blizzcash.Screen
import com.example.blizzcash.theme.MainAppTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlin.random.Random

/*private var auth: FirebaseAuth = Firebase.auth
private var database = FirebaseDatabase.getInstance()
private var ref: DatabaseReference = database.getReference("users").child(auth.currentUser!!.uid)*/
@Composable
fun ProfileScreen(navController: NavHostController){
    val focusManager = LocalFocusManager.current
    MainAppTheme() {
        Column( modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
            .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            var name: TextFieldValue by remember { mutableStateOf(TextFieldValue("")) }
            val maxChar = 20
            val contxt = LocalContext.current
            OutlinedTextField(
                value = name,
                label = { Text(text = "Enter Your Name", style = MaterialTheme.typography.labelLarge) },
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
                maxLines = 1,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = MaterialTheme.colorScheme.secondary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onBackground,
                    focusedLabelColor = MaterialTheme.colorScheme.secondary,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onBackground,
                    textColor = MaterialTheme.colorScheme.onBackground,
                    cursorColor = MaterialTheme.colorScheme.secondary,
                    errorBorderColor = MaterialTheme.colorScheme.error,
                    errorCursorColor = MaterialTheme.colorScheme.error,
                    errorLabelColor = MaterialTheme.colorScheme.error,
                    placeholderColor = MaterialTheme.colorScheme.onBackground,
                    disabledPlaceholderColor = MaterialTheme.colorScheme.onBackground
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            TextButton(onClick ={
                //val user = auth.currentUser
                if(name.selection== TextRange(0,0) && name.composition==null){
                    Log.d(ContentValues.TAG, "nothing")
                    Toast.makeText(contxt, "Please input username", Toast.LENGTH_SHORT).show()
                }
                else{
                    val information = Information2(username = name.text,pfp = Random.nextInt(0,10))
                    //changeInfo(information)
                    Log.d(ContentValues.TAG, name.toString())
                }
                navController.navigate(route = Screen.Options.route){
                    popUpTo(Screen.Profile.route){
                        inclusive = true
                    }
                }
            }){
                Text("Create profile", color = MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}

/*fun changeInfo(information:Information2){
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
}*/