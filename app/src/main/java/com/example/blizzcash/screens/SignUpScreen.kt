package com.example.blizzcash.screens

import android.content.ContentValues
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.getSelectedText
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.blizzcash.Screen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase


var auth: FirebaseAuth = Firebase.auth
var database = FirebaseDatabase.getInstance()
private var ref: DatabaseReference = database.getReference("users")
var verif = 0



@Composable
fun SignUpScreen(navController: NavHostController) {
    // var focusManager = LocalFocusManager
    var email: TextFieldValue by remember { mutableStateOf(TextFieldValue("")) }
    var password: TextFieldValue by remember { mutableStateOf(TextFieldValue("")) }
    val contxt = LocalContext.current
    val (showDialog, setShowDialog) =  remember { mutableStateOf(false) }
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
        Button(onClick ={
                if((email.composition== TextRange(0,0) && email.composition==null) || (password.selection== TextRange(0,0) && password.composition==null)){
                    Log.d(ContentValues.TAG, "nothing")
                    Toast.makeText(contxt, "Please input email and password", Toast.LENGTH_SHORT).show()
                }
                else{
                    SignIn(email.text,password.text,navController)
                    setShowDialog(true)
                    Log.d(ContentValues.TAG, email.toString())
                }
        }){
            Text("Next")
        }
        DialogDemo(showDialog,setShowDialog,verif,email.text,password.text,navController,contxt)
    }
}

fun SignIn(email: String, password: String, navController: NavHostController) {
    val query:Query = ref.orderByChild("email").equalTo(email.trim())
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener() { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(ContentValues.TAG, "signInWithEmail:success")
                val user = auth.currentUser
                navController.navigate(route = Screen.Home.route)
            } else {
                // If sign in fails, display a message to the user.
                Log.w(ContentValues.TAG, "signInWithEmail:failure", task.exception)
                query.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        verif = if (dataSnapshot.exists()){
                            Log.w(ContentValues.TAG, "EmailFound", task.exception)
                            1
                        } else{
                            Log.w(ContentValues.TAG, "EmailNeedsToBeCreated", task.exception)
                            2
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        throw error.toException();
                    }
                })
                    //CustomAlertDialog()
            }
        }
}

fun SignUpInstead(email: String, password: String, navController: NavHostController, context: Context){
    auth.createUserWithEmailAndPassword(email.toString(), password.toString())
        .addOnCompleteListener() { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(ContentValues.TAG, "createUserWithEmail:success")
                val user = auth.currentUser

            } else {
                // If sign in fails, display a message to the user.
                Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
                Toast.makeText(context, "There seems to be a problem. Please try again.", Toast.LENGTH_SHORT).show()
            }
        }
}

@Composable
fun DialogDemo(showDialog: Boolean, setShowDialog: (Boolean) -> Unit, type:Int, email: String, password: String, navController: NavHostController, context: Context) {
    if(verif==1){
        if (showDialog) {
            AlertDialog(
                onDismissRequest = {
                },
                title = {
                    Text("Wrong password")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            // Change the state to close the dialog
                            setShowDialog(false)
                        },
                    ) {
                        Text("OK")
                    }
                },
                text = {
                    Text("There is an account with this email, but the password seems to be incorrect. Please try again.")
                }
            )
        }
    }
    else if(verif==2){
        if (showDialog) {
            AlertDialog(
                onDismissRequest = {
                },
                title = {
                    Text("Nonexistent account")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            SignUpInstead(email,password,navController,context)
                            setShowDialog(false)
                        },
                    ) {
                        Text("Yes")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            // Change the state to close the dialog
                            setShowDialog(false)
                        },
                    ) {
                        Text("No")
                    }
                },
                text = {
                    Text("There isn't any existing account with this email. Would you like so sign up?")
                }
            )
        }
    }

}