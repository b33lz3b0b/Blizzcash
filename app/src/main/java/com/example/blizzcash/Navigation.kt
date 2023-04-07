package com.example.blizzcash


import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.blizzcash.courses.AllowanceLesson
import com.example.blizzcash.courses.SalaryLesson
import com.example.blizzcash.screens.*

@Composable
fun Navigation(navController: NavHostController){

    NavHost(navController = navController, startDestination = Screen.Welcome.route) {
        composable(route = Screen.Welcome.route) {
            WelcomeScreen(navController = navController)
        }
        composable(route = Screen.EmailSignUp.route) {
            SignUpScreen(navController = navController)
        }
        composable(route = Screen.Options.route) {
            OptionsScreen(navController = navController)
        }
        composable(route = Screen.Profile.route) {
            ProfileScreen(navController = navController)
        }
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }

        navigation(
            startDestination = Screen.Practice.route,
            route = "practice"
        ) {
            composable(route = Screen.Practice.route) {
                PracticeScreen(navController = navController)
            }
        }
        
        navigation(
            startDestination = Screen.Courses.route,
            route = "course"
        ){
            composable(route = Screen.Courses.route){
                CoursesScreen(navController = navController)
            }
            for(i in 0..9) {
                composable(route = "allowancelesson"+"$i"){
                    AllowanceLesson(navController = navController, i)
                }
            }
            for(i in 0..19) {
                composable(route = "salarylesson"+"$i"){
                    SalaryLesson(navController = navController, i)
                }
            }
        }
    }
}
