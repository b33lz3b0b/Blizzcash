package com.example.blizzcash.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.blizzcash.Strings

@Composable
fun HomeScreen(navController: NavHostController){
    Text(text = "hello" + Strings.user_name.tag)
}