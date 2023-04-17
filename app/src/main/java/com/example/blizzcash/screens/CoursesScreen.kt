package com.example.blizzcash.screens

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.blizzcash.Screen
import com.example.blizzcash.theme.MainAppTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

data class CourseInfo(val name:String, val desc:String, var unlocked:Boolean)
var lessonListAllowance = mutableListOf<CourseInfo>(
    CourseInfo("Lesson 1", "Saving money as a teenager", false),
    CourseInfo("Lesson 2", "Investing as a teenager", false),
    CourseInfo("Lesson 3", "Earning money as a teenager", false),
    CourseInfo("Lesson 4", "Why do teenagers waste money", false),
    CourseInfo("Lesson 5", "Credit card", false),
    /*CourseInfo("Lesson 6", "Desc6", false),
    CourseInfo("Lesson 7", "Desc7", false),
    CourseInfo("Lesson 8", "Desc8", false),
    CourseInfo("Lesson 9","Desc9", false),
    CourseInfo("Lesson 10", "Desc10", false)*/
)

var lessonListSalary = mutableListOf<CourseInfo>(
    CourseInfo("Lesson 1", "Desc1", false),
    CourseInfo("Lesson 2", "Desc2", false),
    CourseInfo("Lesson 3", "Desc3", false),
    CourseInfo("Lesson 4", "Desc4", false),
    CourseInfo("Lesson 5", "Desc5", false),
    CourseInfo("Lesson 6", "Desc6", false),
    CourseInfo("Lesson 7", "Desc7", false),
    CourseInfo("Lesson 8", "Desc8", false),
    CourseInfo("Lesson 9","Desc9", false),
    CourseInfo("Lesson 10", "Desc10", false),
    CourseInfo("Lesson 11","Desc11", false),
    CourseInfo("Lesson 12","Desc12", false),
    CourseInfo("Lesson 13","Desc13", false),
    CourseInfo("Lesson 14","Desc14", false),
    CourseInfo("Lesson 15","Desc15", false),
    CourseInfo("Lesson 16","Desc16", false),
    CourseInfo("Lesson 17","Desc17", false),
    CourseInfo("Lesson 18","Desc18", false),
    CourseInfo("Lesson 19","Desc19", false),
    CourseInfo("Lesson 20","Desc20", false)
)

private var auth: FirebaseAuth = Firebase.auth
private val user = auth.currentUser
private var database = FirebaseDatabase.getInstance()
private var ref: DatabaseReference = database.getReference("users").child(user!!.uid)


@Composable
fun CoursesScreen(navController: NavController){
    val contxt = LocalContext.current

    listenerscourses()
    updateflowcourses()

    MainAppTheme() {
        Column(modifier= Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.errorContainer),
        horizontalAlignment = Alignment.CenterHorizontally){
            Box(modifier=Modifier.fillMaxWidth()){
                IconButton(onClick = {navController.navigate(route = Screen.Home.route)},
                    modifier = Modifier
                        .align(Alignment.TopStart),
                ){
                    Icon(Icons.Filled.ArrowBack, contentDescription = "previous", tint = MaterialTheme.colorScheme.onBackground)
                }
            }
            LazyColumn(
                contentPadding = PaddingValues(all = 20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally ,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.errorContainer)
                    .fillMaxSize()
            ){
                if(coursetype == "Allowance")
                    itemsIndexed(lessonListAllowance){ index, model ->
                        CustomItem2(navController,model = model, contxt, index)
                    }
                else if(coursetype == "Salary")
                    itemsIndexed(lessonListSalary){ index, model ->
                        CustomItem2(navController,model = model, contxt, index)
                    }
            }
            Text(
                text = "More material soon to be added",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .alpha(0.5f)
                    .padding(5.dp)
            )
        }

    }
}

@Composable
fun CustomItem2(navController: NavController,model: CourseInfo, context: Context, index: Int){
    MainAppTheme(){
        Box(modifier = Modifier
            .fillMaxWidth(0.96f)
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(25.dp)
            )
            .padding(all = 10.dp)
            .width(60.dp)
        ){
            Column(modifier = Modifier
                .padding(all = 5.dp)
            ){
                Text(model.name, style = MaterialTheme.typography.displayLarge, color = MaterialTheme.colorScheme.onPrimaryContainer)
                Spacer(modifier = Modifier.height(10.dp))
                Text(model.desc, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurface)
                Spacer(modifier = Modifier.height(10.dp))
                Row(horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()){
                    Button(enabled = model.unlocked, onClick={
                        if(coursetype == "Allowance")
                            navController.navigate(route = "allowancelesson"+"$index")
                        else if(coursetype == "Salary")
                            navController.navigate(route = "salarylesson"+"$index")
                    },
                        modifier = Modifier.width(110.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        )){
                        if(model.unlocked)
                            Text("Begin", style = MaterialTheme.typography.labelLarge, textAlign = TextAlign.Center)
                        else
                            Text("???", style = MaterialTheme.typography.labelLarge, textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.error)
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

