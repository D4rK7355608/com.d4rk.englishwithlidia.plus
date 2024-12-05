package com.d4rk.englishwithlidia.plus.ui.components.layouts

import android.content.Context
import android.os.Build.VERSION.SDK_INT
import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.CopyAll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import coil3.compose.AsyncImage
import com.d4rk.englishwithlidia.plus.constants.ui.lessons.LessonContentTypes
import com.d4rk.englishwithlidia.plus.data.datastore.DataStore
import com.d4rk.englishwithlidia.plus.data.model.ui.screens.home.UiLessonContent
import com.d4rk.englishwithlidia.plus.data.model.ui.screens.home.UiLessonScreen
import com.d4rk.englishwithlidia.plus.ui.components.ads.AdBanner
import com.d4rk.englishwithlidia.plus.ui.components.ads.LargeBannerAdsComposable
import com.d4rk.englishwithlidia.plus.ui.components.animations.bounceClick
import com.d4rk.englishwithlidia.plus.ui.screens.home.HomeViewModel
import com.d4rk.englishwithlidia.plus.ui.screens.lessons.LessonsViewModel
import com.d4rk.englishwithlidia.plus.ui.screens.settings.display.theme.style.Colors
import com.d4rk.englishwithlidia.plus.ui.screens.settings.display.theme.style.TextStyles
import ir.mahozad.multiplatform.wavyslider.WaveDirection
import ir.mahozad.multiplatform.wavyslider.material3.WavySlider

@Composable
fun LessonContentLayout(
    paddingValues : PaddingValues ,
    scrollState : ScrollState ,
    dataStore : DataStore ,
    lesson : UiLessonScreen ,
    viewModel : LessonsViewModel ,
) {
    Column(
        modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState)
    ) {
        lesson.lessonContent.forEachIndexed { index , contentItem ->
            when (contentItem.contentType) {
                LessonContentTypes.HEADER -> {
                    StyledText(
                        text = contentItem.contentText ,
                        style = TextStyles.header() ,
                        color = Colors.primaryText()
                    )
                }

                LessonContentTypes.TEXT -> {
                    StyledText(
                        text = contentItem.contentText ,
                        style = TextStyles.body() ,
                        color = Colors.secondaryText()
                    )
                }

                LessonContentTypes.CONTENT_PLAYER -> {
                    val sliderPosition by viewModel.playbackPosition.collectAsState()
                    val playbackDuration by viewModel.playbackDuration.collectAsState()
                    val isPlaying by viewModel.isPlaying.collectAsState()

                    LaunchedEffect(key1 = lesson.lessonContent.contentAudioUrl) {
                        viewModel.preparePlayer(lessonDetails.UiLessonContent.audioUrl)
                    }

                    AudioCardView(onPlayClick = { viewModel.playPause() } ,
                                  onSeekChange = { newPosition ->
                                      viewModel.seekTo((newPosition * 1000).toLong())
                                  } ,
                                  sliderPosition = sliderPosition.toFloat() / 1000f ,
                                  playbackDuration = playbackDuration.toFloat() / 1000f ,
                                  isPlaying = isPlaying)

                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                }

                LessonContentTypes.IMAGE -> {
                    StyledImage(
                        imageUrl = contentItem.contentImageUrl , contentDescription = "Lesson Image"
                    )
                }

                LessonContentTypes.AD_BANNER -> {
                    AdBanner(dataStore = dataStore)
                }

                LessonContentTypes.AD_LARGE_BANNER -> {
                    LargeBannerAdsComposable(dataStore = dataStore)
                }

                else -> {
                    Text(text = "Unsupported content type: ${contentItem.contentType}")
                }
            }
            if (index < lesson.lessonContent.lastIndex) {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun StyledText(
    text : String ,
    style : TextStyle = TextStyles.body() ,
    color : Color = Colors.primaryText() ,
) {
    val annotatedString = AnnotatedString.fromHtml(text)

    Text(
        text = annotatedString , style = style , color = color
    )
}

@Composable
fun StyledImage(
    imageUrl : String ,
    contentDescription : String? = null ,
    modifier : Modifier = Modifier ,
) {
    Card(
        modifier = modifier.fillMaxWidth() ,
    ) {
        AsyncImage(
            model = imageUrl ,
            contentScale = ContentScale.FillWidth ,
            contentDescription = contentDescription ,
            modifier = Modifier.fillMaxWidth() ,
        )
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