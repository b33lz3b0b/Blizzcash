package com.example.blizzcash.courses

import android.content.ContentValues
import android.graphics.Paint
import android.util.Log
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.navigation.NavController
import com.example.blizzcash.Screen
import com.example.blizzcash.screens.*
import com.example.blizzcash.theme.MainAppTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

private val lessontexts : Array<Array<String>> = arrayOf(
    arrayOf("hey","hello"),
    arrayOf("sheesh","yikes")
)

private var auth: FirebaseAuth = Firebase.auth
private var database = FirebaseDatabase.getInstance()
private var ref: DatabaseReference = database.getReference("users").child(auth.currentUser!!.uid)

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AllowanceLesson(navController: NavController, index: Int){
    var count: Int = 0
    MainAppTheme() {
        Column(modifier= Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.background)
            .clickable(enabled = (count < lessontexts[index].size)) {
                count++
            }){
            Box(modifier= Modifier.fillMaxWidth()){
                IconButton(onClick = {navController.navigate(route = Screen.Home.route)},
                    modifier = Modifier
                        .align(Alignment.TopStart),
                ){
                    Icon(Icons.Filled.ArrowBack, contentDescription = "previous", tint = MaterialTheme.colorScheme.onBackground)
                }
            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)
                .verticalScroll(rememberScrollState())
            ){
                AnimatedContent(targetState = count,
                transitionSpec = {
                    //if(targetState!=)
                    slideInVertically { height -> height } + fadeIn()  with
                            slideOutVertically { height -> -height }
                }) {targetCount ->
                    Text(text = lessontexts[index][targetCount], style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onBackground)
                }
            }
            Row(modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ){
                Text(text = "Click to reveal more", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .alpha(if(count < lessontexts[index].size) 0.5f else 0f))
                Button(enabled = (count < lessontexts[index].size),
                    onClick = {
                        if(lessoncounter == index+1){
                            lessoncounter++
                            levelcounter++
                        }
                        /*---------------------------------------------------------------------------------*/
                        ref.child("level").setValue(levelcounter).addOnCompleteListener(){ task ->
                            if(task.isSuccessful){
                                Log.d(ContentValues.TAG, "node is completed")
                            }
                            else{
                                Log.d(ContentValues.TAG, "node has FAILED")
                            }
                        }
                        /*---------------------------------------------------------------------------------*/
                        ref.child("lesson").setValue(lessoncounter).addOnCompleteListener(){ task ->
                            if(task.isSuccessful){
                                Log.d(ContentValues.TAG, "node is completed")
                            }
                            else{
                                Log.d(ContentValues.TAG, "node has FAILED")
                            }
                        }
                        /*---------------------------------------------------------------------------------*/
                        updateflowcourses()
                        updateflowlevels()
                        listenerscourses()
                    }, colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer),
                    ){
                    Text(text = "Finish", style = MaterialTheme.typography.labelMedium)
                }
            }
        }
    }
}