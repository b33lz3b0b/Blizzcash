package com.example.blizzcash.screens

import android.media.session.MediaController
import android.net.Uri
import android.provider.MediaStore
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DataSpec
import androidx.media3.datasource.RawResourceDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.extractor.mp3.Mp3Extractor
import androidx.media3.extractor.mp4.Mp4Extractor
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import com.example.blizzcash.R
import com.example.blizzcash.Screen
import com.example.blizzcash.theme.MainAppTheme
import kotlin.math.absoluteValue


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LensScreen(navController: NavController) {
    val contxt = LocalContext.current
    val pagerState = rememberPagerState()
    val pageCount = /*if(coursetype == "Allowance") 5 else 7*/2
    val lifecycleOwner by rememberUpdatedState(LocalLifecycleOwner.current)
    val videos : Array<String> = arrayOf(
        "android.resource://" + contxt.packageName + "/" + R.raw.miyyuu,
        "android.resource://" + contxt.packageName + "/" + R.raw.mrniceguy
    )
    MainAppTheme() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.background)
        ) {
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
            HorizontalPager(pageCount = pageCount, state = pagerState, modifier = Modifier.padding((5.dp))) { page ->
                val exoPlayer = remember {
                    ExoPlayer.Builder(contxt).build().apply{
                        setMediaItem(
                            MediaItem.fromUri(
                                videos[page.toInt()]
                            )
                        )
                        prepare()
                        playWhenReady = true
                    }
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .fillMaxHeight(0.9f)
                        .graphicsLayer {
                            val pageOffset = (
                                    (pagerState.currentPage - page) + pagerState.currentPageOffsetFraction
                                    ).absoluteValue
                            alpha = lerp(
                                start = 0.5f,
                                stop = 1f,
                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                            )
                        },
                    colors = CardDefaults.cardColors(
                        containerColor =  MaterialTheme.colorScheme.primary,
                    )
                ) {
                    DisposableEffect(lifecycleOwner) { val lifecycle = lifecycleOwner.lifecycle
                        val observer = LifecycleEventObserver { _, event ->
                            when (event) {
                                Lifecycle.Event.ON_PAUSE -> {
                                    exoPlayer.playWhenReady = false
                                }
                                Lifecycle.Event.ON_RESUME -> {
                                    exoPlayer.playWhenReady = true
                                }
                                Lifecycle.Event.ON_DESTROY -> {
                                    exoPlayer.run{
                                        stop()
                                        release()
                                    }
                                }
                                else -> {}
                            }
                        }
                        lifecycle.addObserver(observer)
                        onDispose {
                            lifecycle.removeObserver(observer)
                        }
                    }
                    AndroidView(
                        factory = {
                            PlayerView(contxt).apply {
                                player = exoPlayer
                                layoutParams =
                                    FrameLayout.LayoutParams(
                                        ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.MATCH_PARENT
                                    )
                            }
                        },
                        update = { view ->
                            if(pagerState.currentPage != page)
                                view.player?.stop()
                            else
                                view.player?.play()
                        }
                    )

                }
            }
            Row(
                Modifier
                    .height(50.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pageCount) { iteration ->
                    val color = if (pagerState.currentPage == iteration) MaterialTheme.colorScheme.onTertiaryContainer else MaterialTheme.colorScheme.onTertiary
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(10.dp)

                    )
                }
            }
        }
    }
}
