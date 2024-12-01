package com.d4rk.englishwithlidia.plus.ui.screens.lessons

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.unit.dp
import androidx.core.text.htmlEncode
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.d4rk.englishwithlidia.plus.data.datastore.DataStore
import com.d4rk.englishwithlidia.plus.data.model.ui.screens.UiLessonsAsset
import com.d4rk.englishwithlidia.plus.ui.components.ads.AdBanner
import com.d4rk.englishwithlidia.plus.ui.components.animations.bounceClick
import ir.mahozad.multiplatform.wavyslider.WaveDirection
import ir.mahozad.multiplatform.wavyslider.material3.WavySlider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonsComposable(viewModel : LessonsViewModel) {
    val context = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection) , topBar = {
        LargeTopAppBar(title = {
            Text(text = viewModel.lessonDetails?.title ?: "")
        } , navigationIcon = {
            IconButton(onClick = {
                (context as? AppCompatActivity)?.finish()
            }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack , contentDescription = null)
            }
        } , scrollBehavior = scrollBehavior)
    }) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize() ,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                viewModel.lessonDetails?.let {
                    viewModel.lessonDetails = it
                    LessonContent(Modifier.padding(16.dp) , it , viewModel)
                }
            }
        }
    }
}

@Composable
fun LessonContent(
    modifier : Modifier = Modifier ,
    lessonDetails : UiLessonsAsset ,
    viewModel : LessonsViewModel ,
) {
    val sliderPosition by viewModel.playbackPosition.collectAsState()
    val playbackDuration by viewModel.playbackDuration.collectAsState()
    val isPlaying by viewModel.isPlaying.collectAsState()
    val context = LocalContext.current
    val dataStore = DataStore.getInstance(context)

    val annotatedLessonSummary = AnnotatedString.fromHtml(lessonDetails.lessonDetails.lessonSummary)

    LaunchedEffect(key1 = lessonDetails) {
        viewModel.preparePlayer(lessonDetails.lessonDetails.audioUrl)
    }

    Column(
        modifier = modifier
    ) {
        AudioCardView(onPlayClick = { viewModel.playPause() } ,
                      onSeekChange = { newPosition ->
                          viewModel.seekTo((newPosition * 1000).toLong())
                      } ,
                      sliderPosition = sliderPosition.toFloat() / 1000f ,
                      playbackDuration = playbackDuration.toFloat() / 1000f ,
                      isPlaying = isPlaying)

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        Text(
            text = lessonDetails.lessonDetails.lessonIntro.htmlEncode() ,
            style = MaterialTheme.typography.bodyLarge ,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        ImageCardView(imageResource = lessonDetails.banner)

        Spacer(modifier = Modifier.height(8.dp))
        AdBanner(modifier = Modifier.fillMaxWidth() , dataStore = dataStore)
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = annotatedLessonSummary , modifier = Modifier.padding(bottom = 8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))
        AdBanner(modifier = Modifier.fillMaxWidth() , dataStore = dataStore)
    }
}

@Composable
fun AudioCardView(
    onPlayClick : () -> Unit ,
    onSeekChange : (Float) -> Unit ,
    sliderPosition : Float ,
    playbackDuration : Float ,
    isPlaying : Boolean ,
) {
    val cornerRadius = animateFloatAsState(
        targetValue = if (isPlaying) 16f else 28f ,
        animationSpec = tween(durationMillis = 200) ,
        label = ""
    ).value

    var sliderValue = if (playbackDuration > 0f) sliderPosition / playbackDuration else 0f

    OutlinedCard(
        modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp) , shape = RoundedCornerShape(28.dp)
    ) {
        Column(
            modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth() ,
                verticalAlignment = Alignment.CenterVertically ,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                FloatingActionButton(
                    onClick = onPlayClick ,
                    modifier = Modifier
                            .weight(1f)
                            .bounceClick() ,
                    shape = RoundedCornerShape(cornerRadius.dp)
                ) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow ,
                        contentDescription = if (isPlaying) "Pause" else "Play"
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                WavySlider(value = sliderValue ,
                           onValueChange = { newValue ->
                               sliderValue = newValue
                               if (playbackDuration > 0f) {
                                   val newPosition = newValue * playbackDuration
                                   onSeekChange(newPosition)
                               }
                           } ,
                           waveLength = 24.dp ,
                           waveHeight = 4.dp ,
                           waveVelocity = 4.dp to WaveDirection.HEAD ,
                           waveThickness = 4.dp ,
                           trackThickness = 4.dp ,
                           incremental = false ,
                           modifier = Modifier
                                   .fillMaxWidth()
                                   .weight(weight = 4f))
            }
        }
    }
}

@Composable
fun ImageCardView(modifier : Modifier = Modifier , imageResource : String) {
    Card(
        modifier = modifier
                .fillMaxSize()
                .padding(8.dp) ,
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(imageResource)
                    .crossfade(enable = true).build() ,
            contentDescription = "Lesson Illustration" ,
            modifier = Modifier.fillMaxSize() ,
            contentScale = ContentScale.Crop
        )
    }
}