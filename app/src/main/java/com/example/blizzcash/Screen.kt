package com.example.blizzcash

//const val KEY_NAME = "name"

sealed class Screen(val route: String){
    object Welcome: Screen("welcome_screen")
    object EmailSignUp: Screen("singup_screen")
    object Options: Screen("options_screen")
    object Profile: Screen("profile_screen")
    object Home: Screen("home_screen")
    object Practice: Screen("practice_screen")
    object Courses: Screen("courses_screen")
    object AllowanceLesson1: Screen("allowancelesson1")
    object AllowanceLesson2: Screen("allowancelesson2")
    object AllowanceLesson3: Screen("allowancelesson3")
    object AllowanceLesson4: Screen("allowancelesson4")
    object AllowanceLesson5: Screen("allowancelesson5")
    object AllowanceLesson6: Screen("allowancelesson6")
    object AllowanceLesson7: Screen("allowancelesson7")
    object AllowanceLesson8: Screen("allowancelesson8")
    object AllowanceLesson9: Screen("allowancelesson9")
    object AllowanceLesson10: Screen("allowancelesson10")
}

