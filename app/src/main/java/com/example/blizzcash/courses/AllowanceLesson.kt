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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.blizzcash.Screen
import com.example.blizzcash.screens.*
import com.example.blizzcash.theme.MainAppTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

private val lessontexts : Array<Array<String>> = arrayOf(
    arrayOf("Rhonda prided herself on always taking the path less traveled. She'd decided to do this at an early age and had continued to do so throughout her entire life. It was a point of pride and she would explain to anyone who would listen that doing so was something that she'd made great efforts to always do. She'd never questioned this decision until her five-year-old niece asked her, \"So, is this why your life has been so difficult?\" and Rhonda didn't have an answer for her.",
        "It was difficult for him to admit he was wrong. He had been so certain that he was correct and the deeply held belief could never be shaken. Yet the proof that he had been incorrect stood right before his eyes. \"See daddy, I told you that they are real!\" his daughter excitedly proclaimed.",
        "She's asked the question so many times that she barely listened to the answers anymore. The answers were always the same. Well, not exactly the same, but the same in a general sense. A more accurate description was the answers never surprised her. So, she asked for the 10,000th time, \"What's your favorite animal?\" But this time was different. When she heard the young boy's answer, she wondered if she had heard him correctly.",
    "The wolves stopped in their tracks, sizing up the mother and her cubs. It had been over a week since their last meal and they were getting desperate. The cubs would make a good meal, but there were high risks taking on the mother Grizzly. A decision had to be made and the wrong choice could signal the end of the pack."),
    arrayOf("sheesh","yikes")
)

private var auth: FirebaseAuth = Firebase.auth
private var database = FirebaseDatabase.getInstance()
private var ref: DatabaseReference = database.getReference("users").child(auth.currentUser!!.uid)

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AllowanceLesson(navController: NavController, index: Int){
    var count by remember{ mutableStateOf(0) }
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    var scrollToPosition  by remember { mutableStateOf(0F) }

    MainAppTheme() {
        Column(modifier= Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally
            ){
            Box(modifier= Modifier.fillMaxWidth()){
                IconButton(onClick = {navController.navigate(route = Screen.Courses.route)},
                    modifier = Modifier
                        .align(Alignment.TopStart),
                ){
                    Icon(Icons.Filled.ArrowBack, contentDescription = "previous", tint = MaterialTheme.colorScheme.onBackground)
                }
            }
            Text(text = lessonListAllowance[index].name, color = MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.displayMedium)
            Column(modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.9f)
                .verticalScroll(rememberScrollState())
                .clickable(enabled = (count < lessontexts[index].size-1)) {
                    count++
                    coroutineScope.launch{
                        scrollState.animateScrollTo(scrollToPosition.roundToInt())
                    }
                },
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(text = "Click to reveal more", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .alpha(if(count < lessontexts[index].size-1) 0.5f else 0f))
                for(i in 0 until lessontexts[index].size){
                    Text(text = lessontexts[index][i], style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onBackground, modifier = Modifier
                        .alpha(if(i<=count) 1f else 0f)
                        .onGloballyPositioned { coordinates ->
                            //if(i<=count)
                                scrollToPosition = coordinates.positionInParent().y
                        })
                    Spacer(modifier = Modifier.fillMaxWidth().height(10.dp))
                }
            }
            Row(modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ){
                Button(enabled = (count >= lessontexts[index].size-1),
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
                        navController.navigate(route = "course")
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