package com.example.blizzcash


import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.blizzcash.screens.*

@Composable
fun Navigation(navController: NavHostController){

    //var cond = Strings.user_name

    NavHost(navController = navController, startDestination = Screen.Welcome.route, route = "root"){
        composable(route = Screen.Welcome.route){
            WelcomeScreen(navController = navController)
        }
        navigation(
            startDestination = Screen.EmailSignUp.route,
            route = "signup"
        ){
            composable(route = Screen.EmailSignUp.route){
                SignUpScreen(navController = navController)
            }
            composable(route = Screen.Options.route){
                OptionsScreen(navController = navController)
            }
            composable(route = Screen.Profile.route){
                ProfileScreen(navController = navController)
            }
        }
        navigation(
            startDestination = Screen.Home.route,
            route = "home"
        ){
            composable(route = Screen.Home.route){
                HomeScreen(navController = navController)
            }
        }
    }
}
