package com.example.blizzcash.screens

import android.app.AlertDialog
import android.app.PendingIntent.getActivity
import android.content.ContentValues
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.example.blizzcash.MainActivity
import com.example.blizzcash.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

var auth: FirebaseAuth = Firebase.auth

@Composable
fun SignUpScreen(navController: NavHostController) {
    // var focusManager = LocalFocusManager
    val user = Firebase.auth.currentUser
    var email: TextFieldValue by remember { mutableStateOf(TextFieldValue("")) }
    var password: TextFieldValue by remember { mutableStateOf(TextFieldValue("")) }
    val contxt = LocalContext.current
    Column( modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth(),
        //.clickable { focusManager.clearFocus() },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TextFieldEmail(email)
        Spacer(modifier = Modifier.height(20.dp))
        TextFieldPass(password)
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick ={
                if(email.toString()=="" || password.toString()=="")
                    Toast.makeText(contxt, "Please input email and password", Toast.LENGTH_SHORT).show()
                else
                    FinishSign(email,password,navController)
        }){
            Text("Create profile")
        }
    }
}

fun FinishSign(email: TextFieldValue, password: TextFieldValue, navController: NavHostController) {
    val currentUser = auth.currentUser
    auth.createUserWithEmailAndPassword(email.toString(), password.toString())
        .addOnCompleteListener() { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(ContentValues.TAG, "createUserWithEmail:success")
                val user = auth.currentUser
                navController.navigate(route = Screen.Profile.route)
            } else {
                // If sign in fails, display a message to the user.
                Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
                if (currentUser != null) {
                    // if(email.toString() == currentUser.email)
                    // else
                }
            }
        }
}

@Composable
fun TextFieldEmail (email:TextFieldValue){
    var aux = email
    OutlinedTextField(
        value = aux,
        label = { Text(text = "E-mail") },
        placeholder = { Text(text = "Enter E-mail") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        onValueChange = {
            aux = it
        },
        maxLines = 1
    )
}

@Composable
fun TextFieldPass (password:TextFieldValue){
    var aux = password
    OutlinedTextField(
        value = aux,
        label = { Text(text = "Password") },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        onValueChange = {
            aux = it
        },
        maxLines = 1
    )
}

@Composable
fun CustomAlertDialog(onDismiss: () -> Unit, onExit: () -> Unit, wrong_password:Boolean){
    Dialog(onDismissRequest = { onDismiss() },
        DialogProperties(
            dismissOnBackPress = true, dismissOnClickOutside = true
        )
    ) {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                //if wrong password text + ok button
                //else would u like to sign up , yes + no
                Text(
                    text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard",
                    modifier = Modifier.padding(8.dp)
                )

                Row(Modifier.padding(top = 10.dp)) {
                    OutlinedButton(
                        onClick = { onDismiss() },
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1F)
                    ) {
                        Text(text = "Cancel")
                    }


                    Button(
                        onClick = { onExit() },
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1F)
                    ) {
                        Text(text = "Exit")
                    }
                }
            }
        }
    }
}