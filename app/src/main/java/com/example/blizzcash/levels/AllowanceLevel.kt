package com.example.blizzcash.levels

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.IconToggleButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.blizzcash.Screen
import com.example.blizzcash.theme.MainAppTheme
import com.google.android.gms.maps.model.Circle
import kotlin.math.roundToInt

private val questions1: Array<Array<String>> = arrayOf(
    arrayOf("question1","question2"),
    arrayOf("question1","question2")
)

private val questions2: Array<Array<String>> = arrayOf(
    arrayOf("question1-1","question1-2","question2-1","question2-2"),
    arrayOf("question1-1","question1-2","question2-1","question2-2")
)

private val options1: Array<Array<List<String>>> = arrayOf(
    arrayOf(
        listOf("option1","option2","option3","option4"),
        listOf("option1","option2","option3","option4")
    ),
    arrayOf(
        listOf("option1","option2","option3","option4"),
        listOf("option1","option2","option3","option4")
    )
)

private val options2: Array<Array<List<String>>> = arrayOf(
    arrayOf(
        listOf("option1","option2","option3"),
        listOf("option1","option2","option3")
    ),
    arrayOf(
        listOf("option1","option2","option3"),
        listOf("option1","option2","option3")
    )
)

private val buttontext = mutableStateOf("Continue")

@Composable
fun AllowanceLevel(navController: NavController, index: Int){
    val prog by remember {mutableStateOf(0)}
    val scorenow by remember {mutableStateOf(0)}
    MainAppTheme() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { navController.navigate(route = Screen.Courses.route) }) {
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = "previous",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
                Text(
                    text = "score: $scorenow/100",
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
                2 -> DragNDrop(prog,index)
                3 -> DragNDrop(prog,index)
                4 -> DragLines(prog,index)
            }
            Box(modifier = Modifier.fillMaxWidth()){
                Button(onClick = { CheckAnswers() }, modifier = Modifier.align(Alignment.CenterEnd),
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
fun DragLines(prog: Int, i: Int) {

}

@Composable
fun DragNDrop(prog: Int, i: Int) {
    MainAppTheme() {
        val selectedValue = remember { mutableStateOf("") }
        Box(modifier = Modifier.fillMaxSize()){
            var offsetX by remember { mutableStateOf(0f) }
            var offsetY by remember { mutableStateOf(0f) }
            Column(Modifier.padding(8.dp)) {
                Row(modifier = Modifier.fillMaxWidth()){
                    Text(text = questions1[i][2*prog],style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onBackground, modifier = Modifier.padding(2.dp))
                    Box(modifier = Modifier.fillMaxHeight().background(MaterialTheme.colorScheme.surfaceVariant).width(5.dp))
                    Text(text = questions1[i][2*prog+1],style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onBackground, modifier = Modifier.padding(2.dp))
                }
                options2[i][prog].forEach { item ->
                    Box(
                        Modifier
                            .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .pointerInput(Unit) {
                                /*detectDragGestures(
                                    onDragStart = { touch ->

                                    },
                                    OnDrag = { change, dragAmount ->
                                        change.consume()
                                        offsetX += dragAmount.x
                                        offsetY += dragAmount.y
                                    }
                                )*/
                            }
                    ){
                        Text(
                            text = item,
                            modifier = Modifier.fillMaxWidth(),
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ChooseCorrectAnswer(prog: Int, i: Int) {
    MainAppTheme() {
        val selectedValue = remember { mutableStateOf("") }
        Column(Modifier.padding(8.dp)) {
            Text(text = questions1[i][prog],style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onBackground, modifier = Modifier.padding(5.dp))
            options1[i][prog].forEach { item ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.selectable(
                        selected = (selectedValue.value == item),
                        onClick = { selectedValue.value = item },
                        role = Role.RadioButton
                    ).padding(3.dp)
                ) {
                    IconToggleButton(
                        checked = selectedValue.value == item,
                        onCheckedChange = { selectedValue.value = item },
                        modifier = Modifier.size(24.dp)
                    ){
                        Icon(
                                if (selectedValue.value == item) {
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
                        text = item,
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}

fun CheckAnswers(){

}
