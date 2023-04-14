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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

data class LevelInfo(val name:String, val desc:String, var highscore:Int, var unlocked:Boolean)
var levelListAllowance = mutableListOf<LevelInfo>(
    LevelInfo("Level 1", "Desc1",0, false),
    LevelInfo("Level 2", "Desc2",0, false),
    LevelInfo("Level 3", "Desc3",0, false),
    LevelInfo("Level 4", "Desc4",0, false),
    LevelInfo("Level 5", "Desc5",0, false),
    LevelInfo("Level 6", "Desc6",0, false),
    LevelInfo("Level 7", "Desc7",0, false),
    LevelInfo("Level 8", "Desc8",0, false),
    LevelInfo("Level 9","Desc9",0, false),
    LevelInfo("Level 10", "Desc10",0, false)
)

var levelListSalary = mutableListOf<LevelInfo>(
    LevelInfo("Level 1", "Desc1",0, false),
    LevelInfo("Level 2", "Desc2",0, false),
    LevelInfo("Level 3", "Desc3",0, false),
    LevelInfo("Level 4", "Desc4",0, false),
    LevelInfo("Level 5", "Desc5",0, false),
    LevelInfo("Level 6", "Desc6",0, false),
    LevelInfo("Level 7", "Desc7",0, false),
    LevelInfo("Level 8", "Desc8",0, false),
    LevelInfo("Level 9","Desc9",0, false),
    LevelInfo("Level 10", "Desc10",0, false),
    LevelInfo("Level 11","Desc11",0, false),
    LevelInfo("Level 12","Desc12",0, false),
    LevelInfo("Level 13","Desc13",0, false),
    LevelInfo("Level 14","Desc14",0, false),
    LevelInfo("Level 15","Desc15",0, false),
    LevelInfo("Level 16","Desc16",0, false),
    LevelInfo("Level 17","Desc17",0, false),
    LevelInfo("Level 18","Desc18",0, false),
    LevelInfo("Level 19","Desc19",0, false),
    LevelInfo("Level 20","Desc20",0, false)
)

private var auth: FirebaseAuth = Firebase.auth
private val user = auth.currentUser
private var database = FirebaseDatabase.getInstance()
private var ref: DatabaseReference = database.getReference("users").child(user!!.uid)


@Composable
fun PracticeScreen(navController: NavController){
    val contxt = LocalContext.current

    listenerslevels()
    updateflowlevels()

    MainAppTheme() {
        Column(modifier= Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.background)) {
            Box(modifier = Modifier.fillMaxWidth()) {
                IconButton(
                    onClick = { navController.navigate(route = Screen.Home.route) },
                    modifier = Modifier
                        .align(Alignment.TopStart),
                ) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = "previous",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
            LazyColumn(
                contentPadding = PaddingValues(all = 20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxSize()
            ) {
                if (coursetype == "Allowance")
                    itemsIndexed(levelListAllowance) { index , model ->
                        CustomItem(navController,model = model, contxt, index)
                    }
                else if (coursetype == "Salary")
                    itemsIndexed(levelListSalary) { index, model ->
                        CustomItem(navController,model = model, contxt, index)
                    }
            }
        }
    }
}

@Composable
fun CustomItem(navController: NavController,model: LevelInfo, context: Context, index: Int){
    MainAppTheme(){
        val txt = model.highscore
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
                Row(horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()){
                    Text("highscore:$txt/100",style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurface)
                    Button(enabled = model.unlocked, onClick={
                        listenerscores()
                         if(coursetype == "Allowance")
                             navController.navigate(route = "allowancelevel"+"$index")
                        else if(coursetype == "Salary")
                            navController.navigate(route = "salarylevel"+"$index")
                    },
                        modifier = Modifier.width(110.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        )){
                        if(model.unlocked)
                            Text("Begin level", style = MaterialTheme.typography.labelLarge, textAlign = TextAlign.Center)
                        else
                            Text("???", style = MaterialTheme.typography.labelLarge, textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.error)
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

fun listenerscores(){
    val scoresListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            // Get Post object and use the values to update the UI
            for(i in 0..19)
                scoresnumbers[i] = (dataSnapshot.child("scores").child("$i").value as Long).toInt()
            //Log.d(TAG, "$lessoncounter")
        }
        override fun onCancelled(databaseError: DatabaseError) {
            // Getting Post failed, log a message
            Log.w(ContentValues.TAG, "load:onCancelled", databaseError.toException())
        }
    }
    ref.addValueEventListener(scoresListener)
}