package com.example.blizzcash.levels

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.IconToggleButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.blizzcash.Screen
import com.example.blizzcash.theme.MainAppTheme
import kotlinx.coroutines.launch
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
    var prog by remember {mutableStateOf(0)}
    val scorenow by remember {mutableStateOf(0)}
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
                2 -> DragNDrop(prog-2,index)
                3 -> DragNDrop(prog-2,index)
                4 -> DragLines(prog-4,index)
            }
            Box(modifier = Modifier.fillMaxWidth()){
                Button(onClick = { CheckAnswers()
                    prog++}, modifier = Modifier.align(Alignment.CenterEnd),
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
    val textcomp = remember { mutableStateOf("") }
    MaterialTheme(){
        /*------------------------------------------------------------------*/
        val dividerId = "inlineDividerId"
        val text = buildAnnotatedString {
            append(AnnotatedString(questions2[i][2*prog], spanStyle = SpanStyle(MaterialTheme.colorScheme.onBackground)))

            appendInlineContent(dividerId, "[divider]")

            append(AnnotatedString(questions2[i][2*prog+1], spanStyle = SpanStyle(MaterialTheme.colorScheme.onBackground)))

        }
        /*-------------------------------------------------------------------*/
        val inlineDividerContent = mapOf(
            Pair(
                dividerId,
                InlineTextContent(
                    Placeholder(
                        placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter,
                        height = 35.sp,
                        width = 200.sp
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RectangleShape)
                            .background(MaterialTheme.colorScheme.onBackground)
                            .padding(5.dp)
                    ){
                        Text(text = textcomp.value, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.background)
                    }
                }
            )
        )
        /*-----------------------------------------------------------------------*/
        Box(modifier = Modifier.fillMaxWidth()){
            Column(Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
                BasicText(text = text, inlineContent = inlineDividerContent, style = MaterialTheme.typography.titleMedium)
                options2[i][prog].forEach { item ->
                    Box(
                        Modifier
                            .background(MaterialTheme.colorScheme.primaryContainer)
                            .clickable{
                                textcomp.value = item
                            }
                    ){
                        Text(
                            text = item,
                            modifier = Modifier.padding(2.dp),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
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
            Text(text = questions1[i][prog],style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onBackground, modifier = Modifier.padding(5.dp))
            options1[i][prog].forEach { item ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.selectable(
                        selected = (selectedValue.value == item),
                        onClick = { selectedValue.value = item },
                        role = Role.RadioButton
                    ).padding(10.dp)
                ) {
                    IconToggleButton(
                        checked = selectedValue.value == item,
                        onCheckedChange = { selectedValue.value = item },
                        modifier = Modifier.size(56.dp)
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
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}


fun CheckAnswers(){

}
