package com.d4rk.englishwithlidia.plus.ui.components.layouts

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.d4rk.englishwithlidia.plus.R
import com.d4rk.englishwithlidia.plus.data.datastore.DataStore
import com.d4rk.englishwithlidia.plus.data.model.ui.screens.UiLessonsAsset
import com.d4rk.englishwithlidia.plus.ui.components.ads.AdBanner
import com.d4rk.englishwithlidia.plus.ui.components.animations.bounceClick
import com.d4rk.englishwithlidia.plus.ui.components.drawable.homeBanner
import com.d4rk.englishwithlidia.plus.ui.screens.lessons.LessonsActivity
import com.d4rk.englishwithlidia.plus.utils.IntentUtils

@Composable
fun LessonListLayout(context : Context, isLoading : Boolean, dataStore : DataStore, lessons: List<UiLessonsAsset>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            Image(
                imageVector = homeBanner() ,
                contentDescription = null ,
                modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight() ,
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = Modifier.height(height = 16.dp))
        }
        item {
            Row(
                modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(start = 24.dp , end = 24.dp) ,
            ) {
                OutlinedButton(
                    onClick = {
                        IntentUtils.openUrl(
                            context , url = "https://sites.google.com/view/englishwithlidia"
                        )
                    } , modifier = Modifier
                            .weight(1f)
                            .bounceClick()
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_web) ,
                        contentDescription = stringResource(id = R.string.tooltip_website) ,
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text(stringResource(id = R.string.website))
                }

                Spacer(modifier = Modifier.width(24.dp))

                OutlinedButton(
                    onClick = {
                        IntentUtils.openUrl(context , url = "https://www.facebook.com/lidia.melinte")
                    } ,
                    modifier = Modifier
                            .weight(1f)
                            .bounceClick() ,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_find_us) ,
                        contentDescription = stringResource(id = R.string.tooltip_facebook) ,
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text(text = stringResource(id = R.string.find_us))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        if (isLoading) {
            item {
                Box(
                    modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(all = 16.dp) ,
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
        else {
            item {
                AdBanner(modifier = Modifier.fillMaxWidth() , dataStore = dataStore)
                Spacer(modifier = Modifier.height(height = 16.dp))
            }
            items(lessons) { lesson ->
                LessonCard(title = lesson.title , imageResource = lesson.banner , onClick = {

                    // TODO: Use Intent utils or extend the current open class function from the utils
                    val intent = Intent(context , LessonsActivity::class.java).apply {
                        putExtra("lessonDetails" , lesson)
                    }
                    context.startActivity(intent)
                })
            }
        }
        item {
            AdBanner(modifier = Modifier.fillMaxWidth() , dataStore = dataStore)
        }
    }
}

@Composable
fun LessonCard(title : String , imageResource : String , onClick : () -> Unit) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
    ) {
        Card(
            modifier = Modifier
                    .fillMaxWidth()
                    .bounceClick()
                    .clip(RoundedCornerShape(12.dp))
                    .clickable(onClick = onClick)
                    .aspectRatio(2.06f / 1f) ,
        ) {
            AsyncImage(
                model = ImageRequest
                        .Builder(context).data(imageResource).crossfade(enable = true).build() ,
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