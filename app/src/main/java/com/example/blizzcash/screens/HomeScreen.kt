package com.example.blizzcash.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase



@Composable
fun HomeScreen(navController: NavHostController){
    val user = auth.currentUser
    var ref: DatabaseReference = database.getReference("users").child(user!!.uid)
    Column(modifier=Modifier.fillMaxWidth()){
        Text(text = "hello")
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick={
            var auth: FirebaseAuth = Firebase.auth
            auth.signOut()
        }){
            Text(text="Sign out")
        }
    }
}