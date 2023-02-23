package com.example.blizzcash.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.blizzcash.theme.MainAppTheme
import com.google.firebase.database.snapshot.Index

data class LevelInfo(val name:String, val desc:String, val highscore:Int, val unlocked:Boolean)
private val levelList = mutableListOf<LevelInfo>(
    LevelInfo("Level 1", "Desc1",20, true),
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

@Composable
fun CourseScreen(navController: NavController){
    MainAppTheme() {
        LazyColumn(
            contentPadding = PaddingValues(all = 20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally ,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
        ){
            items(levelList){ model ->
                    CustomItem(model = model)
            }
        }
    }
}

@Composable
fun CustomItem(model: LevelInfo){
    MainAppTheme(){
        val txt = model.highscore
        Box(modifier = Modifier
            .fillMaxWidth(0.96f)
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(5.dp)
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
                    Button(enabled = model.unlocked, onClick= {},
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
