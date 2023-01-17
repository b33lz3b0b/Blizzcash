package com.example.blizzcash

sealed class Screen(val route: String){
    object Welcome: Screen("welcome_screen")
    object Options: Screen("options_screen")
    object Profile: Screen("profile_screen")
    object Home: Screen("home_screen")
    object Start: Screen("start_choice")
}

