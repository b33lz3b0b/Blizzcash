package com.example.blizzcash.levels

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.IconToggleButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.blizzcash.screens.*
import com.example.blizzcash.theme.MainAppTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

private val questions: Array<Array<String>> = arrayOf(
    arrayOf("question1","question2","question3","question4"),
    arrayOf("question1","question2","question3","question4"),
    arrayOf("question1","question2","question3","question4"),
    arrayOf("question1","question2","question3","question4")
)


private val options: Array<Array<Array<String>>> = arrayOf(
    arrayOf(
        arrayOf("option1","option2","option3","option4"),
        arrayOf("option1","option2","option3","option4"),
        arrayOf("option1","option2","option3","option4"),
        arrayOf("option1","option2","option3","option4")
    ),
    arrayOf(
        arrayOf("option1","option2","option3","option4"),
        arrayOf("option1","option2","option3","option4")
    )
)

private val solutions: Array<Array<String>> = arrayOf(
    arrayOf("option2","option3","option1","option2")
)


private val buttontext = mutableStateOf("Continue")
private var input = ""
private var rest = mutableStateOf(1f)
private var scorenow = mutableStateOf(0)
private var clicked = mutableStateOf(false)

private var auth: FirebaseAuth = Firebase.auth
private var database = FirebaseDatabase.getInstance()
private var ref: DatabaseReference = database.getReference("users").child(auth.currentUser!!.uid)

@Composable
fun SalaryLevel(navController: NavController, index: Int){
    var prog by remember {mutableStateOf(0)}
    val contxt = LocalContext.current
    MainAppTheme() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { clicked.value = true
                    Log.d(ContentValues.TAG, "clicked = " + clicked)}) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = "previous",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                    if(clicked.value)
                        DialogLevel(onDismiss = {clicked.value = false},navController,contxt)
                }
                Text(
                    text = "score: " + scorenow.value + "/100",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .alpha(0.5f)
                        .padding(5.dp)
                )
            }
            when (prog) {
                0 -> ChooseCorrectAnswer2(prog,index)
                1 -> ChooseCorrectAnswer2(prog,index)
                2 -> ChooseCorrectAnswer2(prog,index)
                3 -> ChooseCorrectAnswer2(prog,index)
                4 -> FinalScreen2(index)
            }
            Box(modifier = Modifier.fillMaxWidth()){
                Button(onClick = {
                    if(prog==3) buttontext.value = "Finish"
                    if(prog==4) {
                        LevelFinale2(index,navController)}
                    else{CheckAnswers2(prog,index)
                        prog++}
                }, modifier = Modifier.align(Alignment.CenterEnd).padding(start = 0.dp, top = 0.dp, end = 20.dp, bottom = 20.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )){
                    Text(text = buttontext.value, style = MaterialTheme.typography.labelMedium)
                }
            }
        }
    }
}

@Composable
fun FinalScreen2(index: Int) {
    Column(Modifier.padding(8.dp).fillMaxWidth(0.9f),
        horizontalAlignment = Alignment.CenterHorizontally){
        Text(text="score: " + scorenow.value + "/100",style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onBackground, modifier = Modifier.padding(5.dp))
        Text(text="highscore: " + scoresnumbers[index] + "/100",style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onBackground, modifier = Modifier.padding(5.dp).alpha(0.5f))
    }
}

fun LevelFinale2(i: Int, navController: NavController) {
    if(scoresnumbers[i] < scorenow.value){
        ref.child("scores").child("$i").setValue(scorenow.value).addOnCompleteListener(){ task ->
            if(task.isSuccessful){
                Log.d(ContentValues.TAG, "node is completed")
            }
            else{
                Log.d(ContentValues.TAG, "node has FAILED")
            }
        }
    }
    listenerscores()
    navController.navigate(route = "level")
}

@Composable
fun ChooseCorrectAnswer2(prog: Int, i: Int) {
    MainAppTheme() {
        val selectedValue = remember { mutableStateOf("") }
        Column(Modifier.padding(8.dp).fillMaxWidth(0.9f),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = questions[i][prog],style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onBackground, modifier = Modifier.padding(5.dp))
            for(j in 0 until options[i][prog].size){
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.selectable(
                        selected = (selectedValue.value == options[i][prog][j]),
                        onClick = { selectedValue.value = options[i][prog][j]
                            input = options[i][prog][j]},
                        role = Role.RadioButton
                    ).padding(10.dp)
                ) {
                    IconToggleButton(
                        checked = selectedValue.value == options[i][prog][j],
                        onCheckedChange = { selectedValue.value = options[i][prog][j] },
                        modifier = Modifier.size(56.dp)
                    ){
                        Icon(
                            if (selectedValue.value == options[i][prog][j]) {
                                Icons.Filled.CheckCircle
                            } else {
                                Icons.Outlined.CheckCircle
                            }
                            ,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    Text(
                        text = options[i][prog][j],
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}

fun CheckAnswers2(prog: Int, i : Int){
    if(input == solutions[i][prog]){
        scorenow.value += 25
    }
    input = ""
}