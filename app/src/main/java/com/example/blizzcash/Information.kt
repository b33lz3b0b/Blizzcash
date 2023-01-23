package com.example.blizzcash

import com.google.firebase.database.Exclude

data class Information1(
    var email:String? = null,
    var password:String? = null
){
    @Exclude
    fun toMap(): Map<String, Any?>{
        return mapOf(
            "email" to email,
            "password" to password
        )
    }
}

data class Information2(
    var username:String? = null,
    var pfp:Int? = null
){
    @Exclude
    fun toMap(): Map<String, Any?>{
        return mapOf(
            "username" to username,
            "pfp" to pfp
        )
    }
}

data class Information3(
    var course:String? = null,
    var level:Int? = null,
    var lesson:Int? = null
){
    @Exclude
    fun toMap(): Map<String, Any?>{
        return mapOf(
            "course" to course,
            "level" to level,
            "lesson" to lesson
        )
    }
}