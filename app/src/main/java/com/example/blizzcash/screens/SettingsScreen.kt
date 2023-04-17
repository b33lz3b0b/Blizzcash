package com.example.blizzcash.screens

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.blizzcash.Screen
import com.example.blizzcash.theme.MainAppTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

private var auth: FirebaseAuth = Firebase.auth
private val user = auth.currentUser

@Composable
fun SettingsScreen (navController: NavController){
    MainAppTheme(){
        Column(modifier= Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(brush = Brush.verticalGradient(
                colors = listOf(
                    MaterialTheme.colorScheme.errorContainer,
                    MaterialTheme.colorScheme.background
                )
            ))
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                IconButton(
                    onClick = { navController.navigate(route = Screen.Home.route) },
                    modifier = Modifier
                        .align(Alignment.TopStart),
                ) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = "previous",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Log out", style = MaterialTheme.typography.displayMedium, color = MaterialTheme.colorScheme.onBackground, modifier = Modifier.padding(20.dp))
            Button(onClick={
                auth.signOut()
                navController.navigate(route = Screen.Welcome.route){
                    popUpTo(Screen.Settings.route) {
                        inclusive = true
                    }
                }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)){
                Text(text="Log out", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onPrimaryContainer)
            }
        }
    }
}