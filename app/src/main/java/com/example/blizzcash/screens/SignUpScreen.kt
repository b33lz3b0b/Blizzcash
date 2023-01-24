package com.example.blizzcash.screens

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.blizzcash.Information1
import com.example.blizzcash.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlin.math.absoluteValue


var auth: FirebaseAuth = Firebase.auth
var database = FirebaseDatabase.getInstance()
private var ref: DatabaseReference = database.getReference("users")
var verif = mutableStateOf(0)

@Composable
fun SignUpScreen(navController: NavHostController) {
    // var focusManager = LocalFocusManager
    var email: TextFieldValue by remember { mutableStateOf(TextFieldValue("")) }
    var password: TextFieldValue by remember { mutableStateOf(TextFieldValue("")) }
    val contxt = LocalContext.current
    val focusManager = LocalFocusManager.current

    Column( modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth()
        .pointerInput(Unit) {
            detectTapGestures(onTap = {
                focusManager.clearFocus()
            })
        },
        //.clickable { focusManager.clearFocus() },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        OutlinedTextField(
            value = email,
            label = { Text(text = "E-mail") },
            placeholder = { Text(text = "Enter E-mail") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            onValueChange = {
                email = it
            },
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            value = password,
            label = { Text(text = "Password") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = {
                password = it
            },
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            maxLines = 1,
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(20.dp))
        NextButton(email,password,navController,contxt)
    }
}

fun SignIn(email: String, password: String, navController: NavHostController) {
    val query:Query = ref.orderByChild("email").equalTo(email.trim())
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "signInWithEmail:success")
                val user = auth.currentUser
                if(user?.isEmailVerified!!)
                        navController.navigate(route = Screen.Home.route)
                else{
                    user.sendEmailVerification()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d(TAG, "Email sent.")
                            }
                        }
                    user.reload()
                    navController.navigate(route = Screen.Home.route)
                }
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInWithEmail:failure", task.exception)
                query.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (dataSnapshot.exists()){
                            Log.w(TAG, "EmailFound", task.exception)
                            verif.value = 1
                            //Log.d(TAG, verif.toString())
                        } else{
                            Log.w(TAG, "EmailNeedsToBeCreated", task.exception)
                            verif.value = 2
                            //Log.d(TAG, verif.toString())
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        throw error.toException()
                    }
                })
            }
        }
}

fun verify(ok:Int):Int{
    return ok
}

fun changeInfo(information:Information1){
    var user = auth.currentUser!!.uid
    ref.child(user).setValue(information).addOnCompleteListener(){task ->
        if(task.isSuccessful){
            Log.d(TAG, "node is completed")
        }
        else{
            Log.d(TAG, "node has FAILED")
        }
    }
}

fun SignUpInstead(email: String, password: String, navController: NavHostController, context: Context){
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "createUserWithEmail:success")
                val user = auth.currentUser
                user!!.sendEmailVerification()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "Email sent.")
                        }
                    }
                val information = Information1(email = email, password = password)
                changeInfo(information)
                user.reload()
                navController.navigate(route = Screen.Profile.route)
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "createUserWithEmail:failure", task.exception)
                Toast.makeText(context, "There seems to be a problem. Please try again.", Toast.LENGTH_SHORT).show()
            }
        }
}

@Composable
fun DialogDemo(onDismiss: () -> Unit, email: String, password: String, navController: NavHostController, context: Context) {
    if (verif.value == 1) {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = {
                Text("Wrong password", color = Color.Black)
            },
            confirmButton = {
                Button(
                    onClick = {
                        onDismiss()
                    },
                ) {
                    Text("OK")
                }
            },
            text = {
                Text("There is an account with this email, but the password seems to be incorrect. Please try again.", color = Color.Black)
            }
        )
    }
    else if (verif.value == 2) {
        AlertDialog(
            onDismissRequest = {onDismiss()},
            title = {
                Text("Nonexistent account", color = Color.Black)
            },
            confirmButton = {
                Button(
                    onClick = {
                        SignUpInstead(email, password, navController, context)
                        onDismiss()
                    },
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        onDismiss()
                    },
                ) {
                    Text("No")
                }
            },
            text = {
                Text("There isn't any existing account with this email. Would you like so sign up?", color = Color.Black)
            }
        )
    }
}

@Composable
fun NextButton(email:TextFieldValue, password:TextFieldValue, navController: NavHostController,context: Context){
    var ok: Int by remember { mutableStateOf(0) }
    var showDialog: Boolean by  remember { mutableStateOf(false) }
    TextButton(onClick ={
        if((email.selection== TextRange(0,0) && email.composition==null) || (password.selection== TextRange(0,0) && password.composition==null)){
            Log.d(TAG, "nothing")
            Toast.makeText(context, "Please input email and password", Toast.LENGTH_SHORT).show()
        }
        else{
            SignIn(email.text,password.text,navController)
        }
    }){
        if(verif.value!=0)
            DialogDemo(onDismiss = {verif.value=0},email.text,password.text,navController,context)
        Surface {
            Text("Next")
        }
    }
}

