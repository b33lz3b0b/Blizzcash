package com.example.blizzcash.courses

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.blizzcash.Screen
import com.example.blizzcash.theme.MainAppTheme


@Composable
fun AllowanceLesson(navController: NavController, index: Int){
    val currentprogress: Int = 0
    MainAppTheme() {
        Column(modifier= Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.background)){
            Box(modifier= Modifier.fillMaxWidth()){
                IconButton(onClick = {navController.navigate(route = Screen.Home.route)},
                    modifier = Modifier
                        .align(Alignment.TopStart),
                ){
                    Icon(Icons.Filled.ArrowBack, contentDescription = "previous", tint = MaterialTheme.colorScheme.onBackground)
                }
            }
            Box(modifier = Modifier.fillMaxWidth()){

            }
        }
    }
}