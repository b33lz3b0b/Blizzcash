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
    arrayOf("At what age can you open a savings account?",
        "Which one is an affordable gift that would save you money?",
        "Which one isn't a priority when you spend money from your budget? ",
        "Which one is a bad habit when shopping online?"),
    arrayOf("What is an investment?",
        "What is a stock?",
        "Why is it important to start investing as a teenager?",
        "What is the importance of diversification in investing?"),
    arrayOf("Which one isn't one of the ways for a teenager to earn money?",
        "Which one isn't one of the ways for a teenager to earn money?",
        "At what age can you get a part-time job?",
        "If a typical teenager intends to tutor online, what can they teach?"),
    arrayOf("How should a teenager spend their pocket money?",
        "What is a usual useless purchase teenagers make?",
        "How should pocket money be managed?",
        "In which instance the purchase is justified?"),
    arrayOf("What is the purpose of a credit card?",
        "What is a credit limit?",
        "What is a late payment fee?",
        "What is a cash advance on a credit card?")
)


private val options: Array<Array<Array<String>>> = arrayOf(
    arrayOf(
        arrayOf("Over 18 years old",
            "Any age, without need of parental assistance",
            "Over 16 years old, with parental approval",
            "At any age as long as it is joint with a parent's account"),
        arrayOf("Silver jewellery",
            "Computer accessory",
            "Handmade gift",
            "Brand clothing"),
        arrayOf("School supplies",
            "Figurines",
            "Groceries for dinner",
            "Clothes for PE class"),
        arrayOf("Using coupons",
            "Finding a trusted website with the best discount",
            "Immediately buying what catches your eye",
            "Making a wishlist until you think you can afford buying something")
    ),
    arrayOf(
        arrayOf("A way to earn money",
            "A way to spend money",
            "A way to save money",
            "A way to borrow money"),
        arrayOf("A type of fruit",
            "A type of investment that represents ownership in a company",
            "A type of clothing item",
            "A type of musical instrument"),
        arrayOf("Because you'll have more money to spend",
            "Because it can help you reach your financial goals in the future",
            "Because it's required by law",
            "Because your friends are doing it"
        ),
        arrayOf("It helps to reduce risk by spreading investments across multiple assets and sectors",
            "It guarantees a higher rate of return",
            "It ensures that all investments will perform well",
            "It is not important in investing"
        )
    ),
    arrayOf(
        arrayOf("Competitions with money prizes",
            "Part-time job",
            "Selling things on platforms such as Etsy",
            "Full-time job"),
        arrayOf("Surveys",
            "Social media creator's fund",
            "Video game tester",
            "All of the above"),
        arrayOf("16 years old",
            "13 years old",
            "15 years old",
            "Depends with each country"
        ),
        arrayOf("Advanced astrophysics",
            "Arts and crafts",
            "Music masterclass",
            "Professional writing tips"
        )
    ),
    arrayOf(
            arrayOf("Small purchases frequently",
                "Large purchases rarely",
                "Purchases when necessary, small or big",
                "Regular purchases of any kind"
            ),
            arrayOf("Daily coffee",
                "Snacks for lunch break",
                "Digital courses",
                "Occasional cultural events"
            ),
            arrayOf("Impulsive purchases",
                "Buying what is in trend",
                "Manage your budget and make a goal",
                "Purchase on behalf of others"
            ),
            arrayOf("The newest gaming console launched",
                "There is a new coffee mix at the caffe in centre",
                "The teacher wants to work on a new workbook",
                "Favourite youtuber launched overpriced merch"
            ),

    ),
    arrayOf(
        arrayOf("To borrow money and pay it back over time",
            "To earn rewards and cash back on purchases",
            "To avoid carrying cash or checks",
            "To increase your credit score"
        ),
        arrayOf("The maximum amount of money you can borrow on your credit card",
            "The minimum amount of money you have to spend each month on your credit card",
            "The interest rate charged on purchases made with your credit card",
            "The fee charged for using your credit card overseas"
        ),
        arrayOf("A fee charged if you don't make at least the minimum payment on your credit card by the due date",
            "A fee charged if you make a payment early",
            "A fee charged if you make a payment over the phone",
            "A fee charged if you use your credit card to withdraw cash from an ATM"
        ),
        arrayOf("Withdrawing cash from an ATM using your credit card",
            "Paying off your credit card balance in full each month",
            "Earning cash back rewards for making purchases with your credit card",
            "Getting a loan from a bank using your credit card as collateral"
        ),

    )
)

private val solutions: Array<Array<String>> = arrayOf(
    arrayOf("At any age as long as it is joint with a parent's account",
        "Handmade gift",
        "Figurines",
        "Immediately buying what catches your eye"),
    arrayOf("A way to spend money",
        "A type of investment that represents ownership in a company",
        "Because it can help you reach your financial goals in the future",
        "It helps to reduce risk by spreading investments across multiple assets and sectors"),
    arrayOf("Full-time job",
        "All of the above",
        "Depends with each country",
        "Arts and crafts"),
    arrayOf("Purchases when necessary, small or big",
        "Daily coffee",
        "Manage your budget and make a goal",
        "The teacher wants to work on a new workbook"),
    arrayOf("To borrow money and pay it back over time",
        "The maximum amount of money you can borrow on your credit card",
        "A fee charged if you don't make at least the minimum payment on your credit card by the due date",
        "Withdrawing cash from an ATM using your credit card")
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
fun AllowanceLevel(navController: NavController, index: Int){
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
                0 -> ChooseCorrectAnswer(prog,index)
                1 -> ChooseCorrectAnswer(prog,index)
                2 -> ChooseCorrectAnswer(prog,index)
                3 -> ChooseCorrectAnswer(prog,index)
                4 -> FinalScreen(index)
            }
            Box(modifier = Modifier.fillMaxWidth()){
                Button(onClick = {
                    if(prog==3) buttontext.value = "Finish"
                    if(prog==4) {
                        LevelFinale(index,navController)}
                    else{CheckAnswers(prog,index)
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
fun FinalScreen(index: Int) {
    Column(Modifier.padding(8.dp).fillMaxWidth(0.9f),
        horizontalAlignment = Alignment.CenterHorizontally){
        Text(text="score: " + scorenow.value + "/100",style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onBackground, modifier = Modifier.padding(5.dp))
        Text(text="highscore: " + scoresnumbers[index] + "/100",style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onBackground, modifier = Modifier.padding(5.dp).alpha(0.5f))
    }
}

fun LevelFinale(i: Int, navController: NavController) {
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
fun ChooseCorrectAnswer(prog: Int, i: Int) {
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


fun CheckAnswers(prog: Int, i : Int){
    if(input == solutions[i][prog]){
        scorenow.value += 25
    }
    input = ""
}
@Composable
fun DialogLevel(onDismiss: () -> Unit, navController: NavController, context: Context) {
    MainAppTheme() {
        AlertDialog(
            onDismissRequest = { onDismiss() },
            title = {
                Text("Exit level?", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onBackground)
            },
            confirmButton = {
                androidx.compose.material.Button(
                    onClick = {
                        input = ""
                        onDismiss()
                        navController.navigate(route = "level")
                    },
                    colors = androidx.compose.material.ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                androidx.compose.material.Button(
                    onClick = {
                        onDismiss()
                    },
                    colors = androidx.compose.material.ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                ) {
                    Text("No")
                }
            },
            text = {
                Text("If you exit now, all your current progress will be lost.", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary)
            }
        )
    }

}