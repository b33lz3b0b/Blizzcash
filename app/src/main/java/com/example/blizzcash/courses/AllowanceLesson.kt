package com.example.blizzcash.courses

import android.content.ContentValues
import android.util.Log
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.blizzcash.Screen
import com.example.blizzcash.screens.*
import com.example.blizzcash.theme.MainAppTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

private val lessontexts : Array<Array<String>> = arrayOf(
    arrayOf("Saving money can be difficult, especially when you’re a teenager. But with a little bit of effort and some creative thinking, it is possible to save money even without a job. There are plenty of reasons to save money as a teenager. Whether you're looking to save for your first car, college, or a gap-year trip around the world, the most important part of saving as a teen is getting started.\n" +
            "Here are our advices on how to save money:",
        "1. OPEN A SAVINGS ACCOUNT\n" +
                "Start your savings account now, and you'll always have a place to put the money you want to save for later." +
                "If you're under age 18, you'll likely need a parent to help you set it up by joining theirs with your newly open one until you become an adult.\n" +
                "You'll probably want to find one without a monthly fee or minimum balance requirement, and a high interest rate is a bonus. Then, you'll just need to bring the right documents and sign the paperwork to set one up to start saving.",
        "2. USE THAT SAVINGS ACCOUNT\n" +
                "Start using it as soon as you open it. Deposit gifts, a certain amount each month, or any other money you might have laying around. It won't do you any good if you forget about it.",
    "3. SET A GOAL FOR YOURSELF\n" +
            "It's a lot harder to do things when you don't have a goal in mind. To make saving easier, make a specific and measurable goal.",
    "4. MAKE A BUDGET AND STICK TO IT\n" +
            "If you know how much you need to save in order to meet your goal, you'll also be able to make a clear budget that prioritizes your savings.",
    "5. DELAY PURCHASES\n" +
            "One way to avoid overspending is to give yourself a cooling-off period between the time an item catches your eye and when you actually make the purchase. If you’re shopping online, consider putting the item in your shopping cart and then walking away until you’ve had more time to think it over.",
    "6. GET CREATIVE WITH GIFTS\n" +
            "You can save money with affordable gift ideas, like herb gardens and books, or go the do-it-yourself route. Baking cookies, creating art or preparing someone dinner can demonstrate that you care just as much as making an expensive purchase, and perhaps even more so."),
    arrayOf("Are you a teenager and you want to start investing for your future self or to achieve a certain goal you set? Well that's great, because as a teenager, you have a massive advantage over those starting their investment journey later in life.\n" +
            "\n" +
            "Teens like you can let the wonders of compounding work to their advantages for a longer period, which could make you wealthier by a certain time.\n" +
            "\n" +
            "We have prepared you a list of a few steps you can follow to learn how to start investing.\n",
        "1. LEARN THE BASICS OF INVESTING\n" +
                "As with any new adventure, investing might seem challenging at first. However, it's relatively simple once you understand stock market basics and how to invest in stocks. Read as much as you can about investing so you know how it works, what mistakes to avoid, and the best practices to follow. There are multiple books and sites that can help you understand what you need to know to invest!",
        "2. OPEN AND FUND YOUR BROKERAGE ACCOUNT\n" +
                "Once you're ready to start investing, it's time to open and fund a brokerage account. Giving the fact that you're a teenager, you'll need a parent's assistance. Parents can either open a brokerage account on their teen's behalf or set up a custodial account. The process is relatively simple and usually takes less than 15 minutes.\n",
        "3. MAKE YOUR FIRST INVESTMENT\n" +
                "Once the funds clear in your brokerage account, it's time to make your first stock purchase. Decide which of the stocks you want to buy and set up the order. When you're ready, submit the order during market hours. Before you know it, you'll be the proud owner of a small piece of what you believe is a great company!"
    ),
    arrayOf("Despite not being able to have a full time job before the age of 18, you'd be surprised by the multitude of ways one can earn money without being an employee. Though our solutions might be considered common knowledge, because of that very fact, teens usually forget they have these options. We decided to freshen up your memory.",
        "1. SOCIAL MEDIA\n" +
                "This generation has grown up mostly with the internet at their disposal, including social media. With a little study on marketing tactics and your favourite social media influencers, any teen can become successful enough to access a creator's fund and earn some extra cash. If you go viral, you basically struck gold.",
        "2. PART-TIME JOBS\n" +
                "Depending on the minimum age in your country, you can taste the life of an adult by working a part-time job. The common picks are working as a barista, cashier, babysitter or tutor. If none of them appeal to you, the search doesn't stop here. Browse some job hunting websites and make sure it seems like a proper business. You wouldn't want to end up in some shady places to earn some pocket money.",
        "3. SELLING\n" +
                "Even though you can't just simply open a store of your own, there are places out there which allow anyone and everyone to make a sale. A teen can easily sell their old stuff to a second hand store or use their crafting skills to sell goods on platforms such as Etsy.",
        "4. ONLINE COURSES\n" +
                "Online courses are quite in vogue because you can learn everything from the comfort of your home. This has resulted in numerous platforms created specifically for that, for example Skillshare or Udemy. If you believe you are quite skilled at something, you can share your knowledge and methods with others. Just make sure you are knowledgeable enough to consider yourself a tutor. Else, you'd just look ... a bit stupid.",
        "5. SURVEYS\n" +
                "Studies are done all the time by scientists and data analysts in all sorts of fields. Studies needing the opinion of the public will inevitably require surveys. Make sure you fit in the category of people they are looking for. Depending on the study, there is a buget provided. You'd be surprised how willing are people to pay for your opinion."
    ),
    arrayOf("Teenagers are expected to start their journey towards maturity, but lets be honest. Teens are still very easy to influence and the consumerist world we live in is a harsh one. If you also struggle with keeping your money in your wallet, in order to solve that problem you need to identify which one of the following might be the source.",
        "1. IMPULSIVE BUYING\n" +
                "To ensure sales, companies will spend a big chuck of their budget to put you under their spell. Before acting rash just at the sight of a nice packaging or a commercial with a catchy tune, always ask yourself if you truly need that thing. It's ok if you don't, as long as you consider your budget generous enough.",
        "2. BEING IN TREND\n" +
                "Modern times are dominated by trends. People want to feel like they fit in so they result in doing what everybody else does. Teenagers are a big target to this phenomenon, so evaluate your situation. Do you even like that product or is it simply what everybody else says you should like?",
        "3. SPENDING MONEY ON OTHERS\n" +
                "I won't encourage you to be stingy and not even buy your friend a snack from now and then. But consider your funds. If you have enough, you have a choice, but if you can't spare money, make sure of one thing especially. If someone asks to borrow quite the sum of money, ensure yourself they will return it. You can't afford losing cash on simple lies."
    ),
    arrayOf("A credit card is a type of credit facility, provided by banks that allow customers to borrow funds within a pre-approved credit limit. It enables customers to make purchase transactions on goods and services. The credit card limit is determined by the credit card issuer based on factors such as income and credit score, which also decides the credit limit.",
        "After knowing what a credit card is, let us find out the benefits that come with it. Depending on your needs, a credit card comes with a host of features and benefits to save money while making transactions. Here are the top benefits to help you to know more about credit cards:",
        "1. HASSLE-FREE SHOPPING EXPERIENCE\n" +
                "The benefit of having the best credit card is that it has made shopping easier and convenient. You no longer need to visit malls or stores to make purchases. With your credit card, you can make big purchases from the comfort of your home. It can help you avoid putting any burden on your monthly budget with a credit card.",
        "2. NO NEED TO CARRY CASH\n" +
                "Credit cards are the best alternative to cash, as it eliminates the need for carrying cash. Credit cards are accepted almost anywhere you go. If you do not have cash, you can simply use your credit card to cover your expenses and repay the outstanding amount at the end of the month. The process of making a transaction is simple. All you have to do is swipe your card at the PoS terminal or enter your card details to make online payments.",
        "3. REWARDS, CASHBACK AND OFFERS\n" +
                "The best credit card comes with rewards and cashback that fits your needs and requirements. Depending on your credit card issuer, your credit card comes with a host of special discounts, cashback, or rewards points for purchases made through it. Before getting a credit card, it is imperative to assess your needs. For instance, if you are a frequent traveller, it would be wise to choose a credit card that offers free airport lounge access or have travel insurance. Also, there are credit cards that offer special discounts on shopping, travel tickets, and accommodations.",
        "4. EASY CASH WITHDRAWAL\n" +
                "Another benefit of having the best credit card is that it allows you to withdraw cash whenever the need arises. However, it is worth noting that using your credit card to withdraw cash follows a small fee that you will have to bear while repaying your outstanding amount.",
        "5. WIDELY ACCEPTED\n" +
                "The best thing about a credit card is that it is accepted worldwide, as it is the most common mode of payment across the world. You can use credit card to make international bookings and payments at airlines, restaurants, hotels, stores, and petrol pumps with ease. It makes your travels around the world convenient. However, using your credit card abroad can cost you high foreign transaction fees and high foreign exchange rates."
    )
)

private var auth: FirebaseAuth = Firebase.auth
private var database = FirebaseDatabase.getInstance()
private var ref: DatabaseReference = database.getReference("users").child(auth.currentUser!!.uid)

@Composable
fun AllowanceLesson(navController: NavController, index: Int){
    var count by remember{ mutableStateOf(0) }
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberLazyListState()

    MainAppTheme() {
        Column(modifier= Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally
            ){
            Box(modifier= Modifier.fillMaxWidth()){
                IconButton(onClick = {navController.navigate(route = Screen.Courses.route)},
                    modifier = Modifier
                        .align(Alignment.TopStart),
                ){
                    Icon(Icons.Filled.ArrowBack, contentDescription = "previous", tint = MaterialTheme.colorScheme.onBackground)
                }
            }
            Text(text = lessonListAllowance[index].name, color = MaterialTheme.colorScheme.onBackground, style = MaterialTheme.typography.displayMedium)
            Text(text = "Click to reveal more", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .alpha(if(count < lessontexts[index].size-1) 0.5f else 0f)
                    .padding(5.dp))
            LazyColumn(state = scrollState,
                modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.9f)
                .clickable(enabled = (count < lessontexts[index].size-1)) {
                    coroutineScope.launch{
                        scrollState.animateScrollToItem(index = count, scrollOffset = -100)
                    }
                    count++
                },
                horizontalAlignment = Alignment.CenterHorizontally){
                    items(count = lessontexts[index].size){ i->
                        Text(text = lessontexts[index][i], style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onBackground, modifier = Modifier
                            .alpha(if(i<=count) 1f else 0f)
                            .padding(10.dp))
                    }
            }
            Row(modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ){
                Button(enabled = (count >= lessontexts[index].size-1),
                    onClick = {
                        if(lessoncounter == index+1){
                            lessoncounter++
                            levelcounter++
                        }
                        /*---------------------------------------------------------------------------------*/
                        ref.child("level").setValue(levelcounter).addOnCompleteListener(){ task ->
                            if(task.isSuccessful){
                                Log.d(ContentValues.TAG, "node is completed")
                            }
                            else{
                                Log.d(ContentValues.TAG, "node has FAILED")
                            }
                        }
                        /*---------------------------------------------------------------------------------*/
                        ref.child("lesson").setValue(lessoncounter).addOnCompleteListener(){ task ->
                            if(task.isSuccessful){
                                Log.d(ContentValues.TAG, "node is completed")
                            }
                            else{
                                Log.d(ContentValues.TAG, "node has FAILED")
                            }
                        }
                        /*---------------------------------------------------------------------------------*/
                        updateflowcourses()
                        updateflowlevels()
                        listenerscourses()
                        navController.navigate(route = "course")
                    }, colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer),
                    ){
                    Text(text = "Finish", style = MaterialTheme.typography.labelMedium)
                }
            }
        }
    }
}