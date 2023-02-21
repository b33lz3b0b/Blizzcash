package com.example.blizzcash.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.blizzcash.Screen
import com.example.blizzcash.theme.MainAppTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.ktx.Firebase
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import kotlin.random.Random


//private val user = auth.currentUser
//private var ref: DatabaseReference = database.getReference("users").child(user!!.uid)
private var quotes: Array<String> = arrayOf("wtf","wdym","bruh")

@Composable
fun HomeScreen(navController: NavHostController){
    MainAppTheme() {
        Column(modifier= Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.background)){
            Box(modifier=Modifier.fillMaxWidth()){
                TextButton(onClick = { /*TODO*/ },
                    modifier = Modifier
                        .align(Alignment.TopStart)) {
                    Text(text = "settings")
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Column(modifier=Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally){
                Text(text = "hello,", color = MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.displaySmall)
                Text(text = "username", color = MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.displaySmall)
                Text(text = quotes[Random.nextInt(0,2)], color = MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.displaySmall)
            }
            Spacer(modifier = Modifier.height(40.dp))
            Column(modifier=Modifier.fillMaxWidth().height(500.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround){
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround){
                    Button(onClick = { navController.navigate(route = "course") },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        ),
                        modifier = Modifier.width(180.dp).height(240.dp)
                    ) {
                        Text("Course", style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center)
                    }
                    Button(onClick = { /*TODO*/ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        ),
                        modifier = Modifier.width(180.dp).height(240.dp),

                    ) {
                        Text("Practice", style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center)
                    }
                }
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround){
                    Button(onClick = { /*TODO*/ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        ),
                        modifier = Modifier.width(180.dp).height(240.dp)
                    ) {
                        Text("Apply your skills", style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center)
                    }
                    Button(onClick = { /*TODO*/ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        ),
                        modifier = Modifier.width(180.dp).height(240.dp)
                    ) {
                        Text("Exp journal", style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center)
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick={
                //auth.signOut()
            }){
                Text(text="Sign out")
            }
        }
    }

}