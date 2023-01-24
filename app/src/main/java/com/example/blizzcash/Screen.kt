package com.example.blizzcash

//const val KEY_NAME = "name"

sealed class Screen(val route: String){
    object Welcome: Screen("welcome_screen")
    object EmailSignUp: Screen("singup_screen")
    object Options: Screen("options_screen")
    object Profile: Screen("profile_screen")
    object Home: Screen("home_screen")
}

