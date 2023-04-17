package com.example.blizzcash.screens

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
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
import com.example.blizzcash.theme.MainAppTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import com.example.blizzcash.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import kotlin.math.absoluteValue


private var auth: FirebaseAuth = Firebase.auth
private var database = FirebaseDatabase.getInstance()
private var ref: DatabaseReference = database.getReference("users")
private var verif = mutableStateOf(0)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SignUpScreen(navController: NavHostController) {
    var email: TextFieldValue by remember { mutableStateOf(TextFieldValue("")) }
    var password: TextFieldValue by remember { mutableStateOf(TextFieldValue("")) }
    val contxt = LocalContext.current
    val focusManager = LocalFocusManager.current
    var focusRequester = remember { FocusRequester() }
    MainAppTheme() {
        Column( modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(brush = Brush.verticalGradient(
                colors = listOf(
                    MaterialTheme.colorScheme.errorContainer,
                    MaterialTheme.colorScheme.background
                )
            ))
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ){

            OutlinedTextField(
                value = email,
                label = { Text(text = "E-mail", style = MaterialTheme.typography.labelLarge) },
                placeholder = { Text(text = "Enter E-mail", style = MaterialTheme.typography.labelLarge) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next),
                onValueChange = {
                    email = it
                },
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    },
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
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
            OutlinedTextField(
                value = password,
                label = { Text(text = "Password", style = MaterialTheme.typography.labelLarge) },
                placeholder = { Text(text = "Enter Password", style = MaterialTheme.typography.labelLarge) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next),
                onValueChange = {
                    password = it
                },
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    },
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    }
                ),
                maxLines = 1,
                visualTransformation = PasswordVisualTransformation(),
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
            NextButton(email,password,navController,contxt)
        }
    }
}

fun SignIn(email: String, password: String, navController: NavHostController) {
    val query:Query = ref.orderByChild("email").equalTo(email.trim())
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Sign in success
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
                    navController.navigate(route = Screen.Home.route){
                        popUpTo(Screen.Welcome.route){inclusive = true}
                    }
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
                val information = Information1(email = email)
                changeInfo(information)
                user.reload()
                navController.navigate(route = Screen.Profile.route){
                    popUpTo(Screen.EmailSignUp.route){inclusive = true}
                }
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
        MainAppTheme() {
            AlertDialog(
                onDismissRequest = { onDismiss() },
                title = {
                    Text("Wrong password", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onBackground)
                },
                confirmButton = {
                    Button(
                        onClick = {
                            onDismiss()
                        },
                        colors = androidx.compose.material.ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    ) {
                        Text("OK")
                    }
                },
                text = {
                    Text("There is an account with this email, but the password seems to be incorrect. Please try again.", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary)
                }
            )
        }

    }
    else if (verif.value == 2) {
        MainAppTheme() {
            AlertDialog(
                onDismissRequest = {onDismiss()},
                title = {
                    Text("Nonexistent account", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onBackground)
                },
                confirmButton = {
                    Button(
                        onClick = {
                            SignUpInstead(email, password, navController, context)
                            onDismiss()
                        },
                        colors = androidx.compose.material.ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    ) {
                        Text("Yes")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            onDismiss()
                        },
                        colors = androidx.compose.material.ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    ) {
                        Text("No")
                    }
                },
                text = {
                    Text("There isn't any existing account with this email. Would you like so sign up?", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onBackground)
                }
            )
        }
    }
}

@Composable
fun NextButton(email:TextFieldValue, password:TextFieldValue, navController: NavHostController,context: Context){
    var ok: Int by remember { mutableStateOf(0) }
    var showDialog: Boolean by  remember { mutableStateOf(false) }
    MainAppTheme() {
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
            Text("Next", color = MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.labelLarge)
        }
    }

}

