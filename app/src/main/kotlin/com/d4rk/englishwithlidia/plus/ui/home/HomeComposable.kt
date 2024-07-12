package com.d4rk.englishwithlidia.plus.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.d4rk.englishwithlidia.plus.R
import com.d4rk.englishwithlidia.plus.ads.BannerAdsComposable
import com.d4rk.englishwithlidia.plus.data.datastore.DataStore
import com.d4rk.englishwithlidia.plus.ui.home.repository.LessonRepository
import com.d4rk.englishwithlidia.plus.utils.Utils

@Composable
fun HomeComposable() {
    val context = LocalContext.current
    val dataStore = DataStore.getInstance(context)
    val repository = LessonRepository(context)
    val viewModel : HomeViewModel = viewModel(factory = HomeViewModelFactory(repository))
    val lessons by viewModel.lessons.collectAsState()
    LazyColumn(
        modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp , end = 16.dp , bottom = 8.dp)
    ) {
        item {
            Image(
                painter = painterResource(id = R.drawable.il_drawer) ,
                contentDescription = null ,
                modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight() ,
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            Row(
                modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
            ) {
                OutlinedButton(onClick = {
                    Utils.openUrl(context , "https://sites.google.com/view/englishwithlidia")
                } , modifier = Modifier.weight(1f)) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_web) ,
                        contentDescription = stringResource(id = R.string.tooltip_website) ,
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text(stringResource(id = R.string.website))
                }

                Spacer(modifier = Modifier.weight(1f))

                OutlinedButton(
                    onClick = {
                        Utils.openUrl(context , "https://www.facebook.com/lidia.melinte")
                    } ,
                    modifier = Modifier.weight(1f) ,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_find_us) ,
                        contentDescription = stringResource(id = R.string.tooltip_facebook) ,
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    Text(stringResource(id = R.string.find_us))
                }
            }
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        items(lessons) { lesson ->
            LessonCard(title = lesson.title , imageResource = lesson.imageResource , onClick = {

            })
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            BannerAdsComposable(modifier = Modifier.fillMaxWidth() , dataStore = dataStore)
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            LottieAnimation(
                composition = rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.anim_learn)).value ,
                iterations = LottieConstants.IterateForever ,
                modifier = Modifier.size(260.dp)
            )
        }
    }
}

@Composable
fun LessonCard(title : String , imageResource : Int , onClick : () -> Unit) {
    Card(
        modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .aspectRatio(2.06f / 1f) ,
    ) {
        Image(
            painter = painterResource(id = imageResource) ,
            contentDescription = null ,
            modifier = Modifier.fillMaxSize() ,
            contentScale = ContentScale.Crop
        )
    }
    Text(
        text = title ,
        style = MaterialTheme.typography.headlineMedium ,
        modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
    )
}