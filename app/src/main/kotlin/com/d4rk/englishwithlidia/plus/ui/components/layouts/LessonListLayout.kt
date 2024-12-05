package com.d4rk.englishwithlidia.plus.ui.components.layouts

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Language
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.d4rk.englishwithlidia.plus.R
import com.d4rk.englishwithlidia.plus.constants.ui.lessons.LessonConstants
import com.d4rk.englishwithlidia.plus.data.datastore.DataStore
import com.d4rk.englishwithlidia.plus.data.model.ui.screens.home.UiHomeLesson
import com.d4rk.englishwithlidia.plus.ui.components.ads.AdBanner
import com.d4rk.englishwithlidia.plus.ui.components.ads.LargeBannerAdsComposable
import com.d4rk.englishwithlidia.plus.ui.components.animations.animateVisibility
import com.d4rk.englishwithlidia.plus.ui.components.animations.bounceClick
import com.d4rk.englishwithlidia.plus.ui.components.buttons.OutlinedUrlButtons
import com.d4rk.englishwithlidia.plus.ui.components.drawable.homeBanner
import com.d4rk.englishwithlidia.plus.ui.screens.lessons.LessonActivity

@Composable
fun LessonListLayout(
    context : Context ,
    lessons : List<UiHomeLesson> ,
    visibilityStates : List<Boolean> ,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(lessons) { index , lesson ->
            val isVisible = visibilityStates.getOrElse(index) { false }
            LessonItem(
                lesson = lesson ,
                context = context ,
                modifier = Modifier
                        .animateVisibility(visible = isVisible)
                        .animateItem()
            )
        }
    }
}

@Composable
fun LessonItem(lesson : UiHomeLesson , context : Context , modifier : Modifier = Modifier) {
    val dataStore = DataStore.getInstance(context)
    when (lesson.lessonType) {
        LessonConstants.TYPE_BANNER_IMAGE_LOCAL -> {
            Image(
                imageVector = homeBanner() ,
                contentDescription = null ,
                modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight() ,
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        LessonConstants.TYPE_ROW_BUTTONS_LOCAL -> {
            Row(
                modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(start = 24.dp , end = 24.dp) ,
            ) {
                OutlinedUrlButtons(
                    vectorIcon = Icons.Outlined.Language ,
                    context = context ,
                    modifier = Modifier
                            .weight(1f)
                            .bounceClick() ,
                    text = R.string.website ,
                    url = "https://sites.google.com/view/englishwithlidia"
                )

                Spacer(modifier = Modifier.width(24.dp))

                OutlinedUrlButtons(
                    painterIcon = painterResource(id = R.drawable.ic_find_us) ,
                    context = context ,
                    modifier = Modifier
                            .weight(1f)
                            .bounceClick() ,
                    text = R.string.website ,
                    url = "https://www.facebook.com/lidia.melinte"
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        LessonConstants.TYPE_AD_VIEW_BANNER -> {
            AdBanner(modifier = Modifier.fillMaxWidth() , dataStore = dataStore)
        }

        LessonConstants.TYPE_AD_VIEW_BANNER_LARGE -> {
            LargeBannerAdsComposable(dataStore = dataStore)
        }

        LessonConstants.TYPE_FULL_IMAGE_BANNER -> {
            LessonCard(title = lesson.lessonTitle ,
                       imageResource = lesson.lessonThumbnailImageUrl ,
                       onClick = {
                           val intent = Intent(context , LessonActivity::class.java).apply {
                               putExtra("lessonDetails" , lesson.lessonId)
                           }
                           context.startActivity(intent)
                       } ,
                       modifier = modifier)
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

@Composable
fun LessonCard(
    title : String , imageResource : String , onClick : () -> Unit , modifier : Modifier = Modifier
) {
    Column(
        modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp) ,
    ) {
        Card(
            modifier = Modifier
                    .fillMaxWidth()
                    .bounceClick()
                    .clip(RoundedCornerShape(12.dp))
                    .clickable(onClick = onClick)
                    .aspectRatio(ratio = 2.06f / 1f) ,
        ) {
            AsyncImage(
                model = imageResource ,
                contentDescription = null ,
                modifier = Modifier.fillMaxSize() ,
                contentScale = ContentScale.Crop ,
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = title ,
            style = MaterialTheme.typography.headlineMedium ,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(16.dp))
    }
}