package com.example.blizzcash


import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.blizzcash.courses.AllowanceLesson
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
            /*for(i in 1..10) {
                composable(route = "allowancelesson"+"$i"){
                    AllowanceLesson(navController = navController, i-1)
                }
            }*/
            composable(route = Screen.AllowanceLesson0.route) {
                AllowanceLesson(navController = navController, 0)
            }
            composable(route = Screen.AllowanceLesson1.route) {
                AllowanceLesson(navController = navController, 1)
            }
            composable(route = Screen.AllowanceLesson2.route) {
                AllowanceLesson(navController = navController, 2)
            }
            composable(route = Screen.AllowanceLesson3.route) {
                AllowanceLesson(navController = navController, 3)
            }
            composable(route = Screen.AllowanceLesson4.route) {
                AllowanceLesson(navController = navController, 4)
            }
            composable(route = Screen.AllowanceLesson5.route) {
                AllowanceLesson(navController = navController, 5)
            }
            composable(route = Screen.AllowanceLesson6.route) {
                AllowanceLesson(navController = navController, 6)
            }
            composable(route = Screen.AllowanceLesson7.route) {
                AllowanceLesson(navController = navController, 7)
            }
            composable(route = Screen.AllowanceLesson8.route) {
                AllowanceLesson(navController = navController, 8)
            }
            composable(route = Screen.AllowanceLesson9.route) {
                AllowanceLesson(navController = navController, 9)
            }
        }
    }
}
