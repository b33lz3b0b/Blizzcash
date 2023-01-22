package com.example.blizzcash


import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.blizzcash.screens.*

@Composable
fun Navigation(navController: NavHostController){

    var cond = Strings.user_name

    NavHost(navController = navController, startDestination = /*if(cond.tag=="") Screen.Welcome.route else Screen.Home.route*/ Screen.Welcome.route ){
        composable(route = Screen.Welcome.route){
            WelcomeScreen(navController = navController)
        }
        composable(route = Screen.EmailSignUp.route){
            SignUpScreen(navController = navController)
        }
        composable(route = Screen.Options.route){
            OptionsScreen(navController = navController)
        }
        composable(route = Screen.Profile.route){
            ProfileScreen(navController = navController)
        }
        composable(route = Screen.Home.route){
            HomeScreen(navController = navController)
        }
    }
}
