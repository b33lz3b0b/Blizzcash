package com.example.blizzcash.screens

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.blizzcash.Screen
import com.example.blizzcash.theme.MainAppTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

private var auth: FirebaseAuth = Firebase.auth
private val user = auth.currentUser
private var database = FirebaseDatabase.getInstance()
private var ref: DatabaseReference = database.getReference("users").child(user!!.uid)

var lessoncounter= 1
var coursetype = ""
var levelcounter = 0
var scoresnumbers = IntArray(21){0}
var pfp = mutableStateOf(0)

@Composable
fun HomeScreen(navController: NavHostController){

    listenercoursetype()
    listenerslevels()
    updateflowlevels()
    listenerscourses()
    updateflowcourses()
    listenername()
    listenerspfp()

    val visible by remember { mutableStateOf(true) }

    MainAppTheme() {
        Column(modifier= Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.background)){
            Box(modifier=Modifier.fillMaxWidth()){
                IconButton( onClick = {},
                    modifier = Modifier
                        .align(Alignment.TopStart),
                ){
                    Icon(Icons.Outlined.Settings, contentDescription = "Settings",tint = MaterialTheme.colorScheme.onBackground)
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            AnimatedVisibility(
                visible = visible,
                enter = slideInHorizontally(animationSpec = tween(durationMillis = 200)){  fullWidth -> -fullWidth / 2} + fadeIn(animationSpec = tween(durationMillis = 200))
            ){
                Row(modifier = Modifier.fillMaxWidth().padding(15.dp),
                    verticalAlignment = Alignment.CenterVertically){
                    Image(
                        painterResource(PfpArray[pfp.value]),
                        contentDescription = "pfp",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(123.dp)
                            .clip(CircleShape)
                            .background(color = MaterialTheme.colorScheme.onBackground)
                            .border(
                                BorderStroke(2.dp, MaterialTheme.colorScheme.onBackground),
                                CircleShape
                            )
                    )
                    Column(modifier=Modifier.fillMaxWidth().padding(15.dp),
                        horizontalAlignment = Alignment.Start){
                        Text(text = "hello,", color = MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.displaySmall, )
                        Text(text = usernamename, color = MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.displaySmall)
                    }
                }
            }


            Spacer(modifier = Modifier.height(40.dp))
            Column(modifier=Modifier.fillMaxWidth().height(500.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround){
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround){
                    Button(onClick ={ navController.navigate(route = "course") },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        ),
                        modifier = Modifier.width(180.dp).height(240.dp)
                    ) {
                        Text("Course", style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center)
                    }
                    Button(onClick = { navController.navigate(route = "practice")  },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        ),
                        modifier = Modifier.width(180.dp).height(240.dp),

                    ) {
                        Text("Practice", style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center)
                    }
                }
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround){
                    Button(onClick = { navController.navigate(route = Screen.Lens.route)  },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        ),
                        modifier = Modifier.width(180.dp).height(240.dp)
                    ) {
                        Text("Lens", style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center)
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick={
                auth.signOut()
            }){
                Text(text="Sign out")
            }
        }
    }

}

fun listenercoursetype(){
    val typeListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // Get Post object and use the values to update the UI
            coursetype = dataSnapshot.child("course").value.toString()
        }
        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Post failed, log a message
            Log.w(ContentValues.TAG, "load:onCancelled", databaseError.toException())
        }
    }
    ref.addValueEventListener(typeListener)
}



fun listenerslevels(){
    val practiceListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // Get Post object and use the values to update the UI
            levelcounter = (dataSnapshot.child("level").value as Long).toInt()
            for(i in 0..20)
                scoresnumbers[i] = (dataSnapshot.child("scores").child("$i").value as Long).toInt()
        }
        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Post failed, log a message
            Log.w(ContentValues.TAG, "load:onCancelled", databaseError.toException())
        }
    }
    ref.addValueEventListener(practiceListener)
}

fun listenerspfp(){
    val pfpListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // Get Post object and use the values to update the UI
            pfp.value = (dataSnapshot.child("pfp").value as Long).toInt()
        }
        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Post failed, log a message
            Log.w(ContentValues.TAG, "load:onCancelled", databaseError.toException())
        }
    }
    ref.addValueEventListener(pfpListener)
}

fun updateflowlevels(){
    if(coursetype=="Allowance"){
        for(i in 0 until levelcounter){
            levelListAllowance[i].unlocked = true
            levelListAllowance[i].highscore = scoresnumbers[i]
        }
    }
    else if(coursetype=="Salary"){
        for(i in 0 until levelcounter){
            levelListSalary[i].unlocked = true
            levelListSalary[i].highscore = scoresnumbers[i]
        }
    }
}

fun listenerscourses(){
    val practiceListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // Get Post object and use the values to update the UI
            lessoncounter = (dataSnapshot.child("lesson").value as Long).toInt()
            //Log.d(TAG, "$lessoncounter")
        }
        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Post failed, log a message
            Log.w(ContentValues.TAG, "load:onCancelled", databaseError.toException())
        }
    }
    ref.addValueEventListener(practiceListener)
}

fun updateflowcourses(){
    if(coursetype=="Allowance"){
        for(i in 0 until lessoncounter){
            lessonListAllowance[i].unlocked = true
        }
    }
    else if(coursetype=="Salary"){
        for(i in 0 until lessoncounter){
            lessonListSalary[i].unlocked = true
        }
    }
}

fun listenername(){
    val nameListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // Get Post object and use the values to update the UI
            usernamename = dataSnapshot.child("username").value.toString()
        }
        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Post failed, log a message
            Log.w(TAG, "load:onCancelled", databaseError.toException())
        }
    }
    ref.addValueEventListener(nameListener)
}