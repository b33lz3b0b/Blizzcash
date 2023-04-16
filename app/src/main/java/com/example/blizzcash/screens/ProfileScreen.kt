package com.example.blizzcash.screens

import com.example.blizzcash.R
import android.content.ContentValues
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.blizzcash.Information2
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

val PfpArray = intArrayOf(
    R.drawable.pfp_1,
    R.drawable.pfp_2,
    R.drawable.pfp_3
)

@OptIn(ExperimentalAnimationApi::class)
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
            var flag : Int by remember { mutableStateOf(0) }
            var enabledPrev by remember { mutableStateOf(true) }
            var enabledNext by remember { mutableStateOf(true) }
            Row(modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically){
                IconButton(onClick = {if(flag>0) flag-- else flag = 2}, enabled = enabledPrev){
                    Icon(Icons.Filled.ArrowBack, contentDescription = "previous", tint = MaterialTheme.colorScheme.onBackground)
                }
                AnimatedContent(
                    flag,
                    transitionSpec = {
                        fadeIn(animationSpec = tween(durationMillis = 1000)) with
                                fadeOut(animationSpec = tween(durationMillis = 500))
                    }
                ) { targetState ->
                    Image(
                        painterResource(PfpArray[targetState]),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(164.dp)
                            .clip(CircleShape)
                            .background(color = MaterialTheme.colorScheme.onBackground)
                            .border(
                                BorderStroke(2.dp, MaterialTheme.colorScheme.onBackground),
                                CircleShape
                            )
                    )
                }
                IconButton(onClick = {if(flag<2) flag++ else flag = 0}, enabled = enabledNext){
                    Icon(Icons.Filled.ArrowForward, contentDescription = "next", tint = MaterialTheme.colorScheme.onBackground)
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
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
                val user = auth.currentUser
                if(name.selection== TextRange(0,0) && name.composition==null){
                    Log.d(ContentValues.TAG, "nothing")
                    Toast.makeText(contxt, "Please input username", Toast.LENGTH_SHORT).show()
                }
                else{
                    val information = Information2(username = name.text,pfp = flag)
                    changeInfo(information)
                    Log.d(ContentValues.TAG, name.toString())
                    navController.navigate(route = Screen.Options.route){
                        popUpTo(Screen.Profile.route){
                            inclusive = true
                        }
                    }
                }
            }){
                Text("Create profile", color = MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}

fun changeInfo(information:Information2){
    ref.child("username").setValue(information.username).addOnCompleteListener(){ task ->
        if(task.isSuccessful){
            Log.d(ContentValues.TAG, "node is completed")
        }
        else{
            Log.d(ContentValues.TAG, "node has FAILED")
        }
    }
    ref.child("pfp").setValue(information.pfp).addOnCompleteListener(){ task ->
        if(task.isSuccessful){
            Log.d(ContentValues.TAG, "node is completed")
        }
        else{
            Log.d(ContentValues.TAG, "node has FAILED")
        }
    }
}