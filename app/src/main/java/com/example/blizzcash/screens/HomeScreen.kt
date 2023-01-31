package com.example.blizzcash.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
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
    MainAppTheme(){
        Column(modifier=Modifier.fillMaxWidth()
            .fillMaxHeight()){
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
                Text(text = "hello,")
                Text(text = "username")
                Text(text = quotes[Random.nextInt(0,2)])
            }

            Spacer(modifier = Modifier.height(80.dp))

            Button(onClick={
                //auth.signOut()
            }){
                Text(text="Sign out")
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun HomeScreenPreview(){
    HomeScreen(navController = rememberNavController())
}