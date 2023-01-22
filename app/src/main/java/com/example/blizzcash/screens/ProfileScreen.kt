package com.example.blizzcash.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.blizzcash.Screen
import com.example.blizzcash.Strings
import com.google.firebase.database.FirebaseDatabase

@Composable
fun ProfileScreen(navController: NavHostController){
   // var focusManager = LocalFocusManager

    Column( modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth(),
        //.clickable { focusManager.clearFocus() },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        var text: TextFieldValue by remember { mutableStateOf(TextFieldValue("")) }
        val maxChar = 20
        val contxt = LocalContext.current
        OutlinedTextField(
            value = text,
            label = { Text(text = "Enter Your Name") },
            onValueChange = {
                if(it.text.length <= maxChar)
                    text = it
                else
                   Toast.makeText(contxt, "Can't input more than 20 charaters", Toast.LENGTH_SHORT).show()
            },
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick ={
            val user = auth.currentUser
            navController.navigate(route = Screen.Home.route)
        }){
            Text("Create profile")
        }
    }
}