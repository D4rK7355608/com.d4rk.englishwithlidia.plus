package com.d4rk.englishwithlidia.plus.ui.components.navigation

import android.content.Context
import android.view.SoundEffectConstants
import android.view.View
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.VolunteerActivism
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import com.d4rk.android.libs.apptoolkit.utils.helpers.IntentsHelper
import com.d4rk.englishwithlidia.plus.R
import com.d4rk.englishwithlidia.plus.ui.components.animations.bounceClick
import com.d4rk.englishwithlidia.plus.ui.screens.support.SupportActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarMain(
    view : View , drawerState : DrawerState , coroutineScope : CoroutineScope , context : Context
) {
    TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primary ,
        titleContentColor = MaterialTheme.colorScheme.onPrimary ,
        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary ,
        actionIconContentColor = MaterialTheme.colorScheme.onSecondary
    ) , title = { Text(text = stringResource(id = R.string.app_name)) } , navigationIcon = {
        IconButton(modifier = Modifier.bounceClick() , onClick = {
            view.playSoundEffect(
                SoundEffectConstants.CLICK
            )
            coroutineScope.launch {
                drawerState.apply {
                    if (isClosed) open() else close()
                }
            }
        }) {
            Icon(
                imageVector = Icons.Default.Menu ,
                contentDescription = stringResource(id = R.string.navigation_drawer_open)
            )
        }
    } , actions = {
        IconButton(modifier = Modifier.bounceClick() , onClick = {
            view.playSoundEffect(
                SoundEffectConstants.CLICK
            )
            IntentsHelper.openActivity(
                context = context , activityClass = SupportActivity::class.java
            )
        }) {
            Icon(
                Icons.Outlined.VolunteerActivism ,
                contentDescription = stringResource(id = R.string.support_us)
            )
        }
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarScaffoldWithBackButton(
    title : String , onBackClicked : () -> Unit , content : @Composable (PaddingValues) -> Unit
) {
    val scrollBehaviorState : TopAppBarScrollBehavior =
            TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val view : View = LocalView.current

    Scaffold(modifier = Modifier.nestedScroll(scrollBehaviorState.nestedScrollConnection) ,
             topBar = {
                 LargeTopAppBar(title = { Text(text = title) } , navigationIcon = {
                     IconButton(modifier = Modifier.bounceClick() , onClick = {
                         onBackClicked()
                         view.playSoundEffect(SoundEffectConstants.CLICK)
                     }) {
                         Icon(Icons.AutoMirrored.Filled.ArrowBack , contentDescription = null)
                     }
                 } , scrollBehavior = scrollBehaviorState)
             }) { paddingValues ->
        content(paddingValues)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarScaffold(
    title : String , content : @Composable (PaddingValues) -> Unit
) {
    val scrollBehaviorState : TopAppBarScrollBehavior =
            TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(modifier = Modifier.nestedScroll(scrollBehaviorState.nestedScrollConnection) ,
             topBar = {
                 LargeTopAppBar(title = { Text(text = title) } ,
                                scrollBehavior = scrollBehaviorState)
             }) { paddingValues ->
        content(paddingValues)
    }
}