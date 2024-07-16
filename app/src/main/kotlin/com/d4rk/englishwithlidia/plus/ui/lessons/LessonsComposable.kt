package com.d4rk.englishwithlidia.plus.ui.lessons

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.d4rk.englishwithlidia.plus.R
import com.d4rk.englishwithlidia.plus.data.model.ui.lessons.UiLessonsAsset
import com.d4rk.englishwithlidia.plus.ui.settings.display.theme.style.annotatedStringHtmlParser
import com.d4rk.englishwithlidia.plus.utils.compose.bounceClick

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LessonsComposable(viewModel: LessonsViewModel) {
    val context = LocalContext.current
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Text(viewModel.lessonDetails?.title ?: "")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        (context as? AppCompatActivity)?.finish()
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                viewModel.lessonDetails?.let {
                    viewModel.lessonDetails = it
                    LessonContent(Modifier.padding(16.dp), it, viewModel)
                }
            }
        }
    }
}

@Composable
fun LessonContent(
    modifier: Modifier = Modifier,
    lessonDetails: UiLessonsAsset,
    viewModel: LessonsViewModel,
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.anim_plant))
    val sliderPosition by viewModel.playbackPosition.collectAsState()
    val playbackDuration by viewModel.playbackDuration.collectAsState()
    val isPlaying by viewModel.isPlaying.collectAsState()

    LaunchedEffect(key1 = lessonDetails) {
        viewModel.preparePlayer(lessonDetails.lessonDetails.audioUrl)
    }

    Column(
        modifier = modifier
    ) {
        AudioCardView(
            onPlayClick = { viewModel.playPause() },
            onSeekChange = { newPosition ->
                viewModel.seekTo((newPosition * 1000).toLong())
            },
            sliderPosition = sliderPosition.toFloat() / 1000f,
            playbackDuration = playbackDuration.toFloat() / 1000f,
            isPlaying = isPlaying
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        Text(
            text = lessonDetails.lessonDetails.lessonIntro.annotatedStringHtmlParser(),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        ImageCardView(imageResource = lessonDetails.banner)

        Text(
            text = lessonDetails.lessonDetails.lessonSummary.annotatedStringHtmlParser(),
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LottieAnimation(
            composition = composition,
            iterations = Int.MAX_VALUE,
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        )
    }
}

@Composable
fun AudioCardView(
    onPlayClick: () -> Unit,
    onSeekChange: (Float) -> Unit,
    sliderPosition: Float,
    playbackDuration: Float,
    isPlaying: Boolean,
) {
    val cornerRadius = animateFloatAsState(
        targetValue = if (isPlaying) 16f else 28f,
        animationSpec = tween(durationMillis = 200), label = ""
    ).value

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(28.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                FloatingActionButton(
                    onClick = onPlayClick,
                    modifier = Modifier
                        .weight(1f)
                        .bounceClick(),
                    shape = RoundedCornerShape(cornerRadius.dp)
                ) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Filled.Pause else Icons.Filled.PlayArrow,
                        contentDescription = if (isPlaying) "Pause" else "Play"
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Slider(
                    value = sliderPosition,
                    onValueChange = { newValue ->
                        onSeekChange(newValue)
                    },
                    colors = SliderDefaults.colors(),
                    valueRange = 0f..playbackDuration,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(4f)
                )
            }
        }
    }
}

@Composable
fun ImageCardView(modifier: Modifier = Modifier, imageResource: String) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageResource)
                .crossfade(true)
                .build(),
            contentDescription = "Lesson Illustration",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}