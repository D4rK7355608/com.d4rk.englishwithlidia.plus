package com.d4rk.englishwithlidia.plus.ui.components.navigation

import android.content.Context
import android.view.SoundEffectConstants
import android.view.View
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.EventNote
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.d4rk.englishwithlidia.plus.R
import com.d4rk.englishwithlidia.plus.data.model.ui.navigation.NavigationDrawerItem
import com.d4rk.englishwithlidia.plus.ui.components.animations.bounceClick
import com.d4rk.englishwithlidia.plus.ui.components.animations.hapticDrawerSwipe
import com.d4rk.englishwithlidia.plus.ui.screens.help.HelpActivity
import com.d4rk.englishwithlidia.plus.ui.screens.main.MainScreenContent
import com.d4rk.englishwithlidia.plus.ui.screens.settings.SettingsActivity
import com.d4rk.englishwithlidia.plus.utils.IntentUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun NavigationDrawer(
    drawerState : DrawerState ,
    view : View ,
    context : Context ,
) {
    val coroutineScope : CoroutineScope = rememberCoroutineScope()
    val drawerItems : List<NavigationDrawerItem> = listOf(
        NavigationDrawerItem(
            title = R.string.settings ,
            selectedIcon = Icons.Outlined.Settings ,
        ) ,
        NavigationDrawerItem(
            title = R.string.help_and_feedback ,
            selectedIcon = Icons.AutoMirrored.Outlined.HelpOutline ,
        ) ,
        NavigationDrawerItem(
            title = R.string.updates ,
            selectedIcon = Icons.AutoMirrored.Outlined.EventNote ,
        ) ,
        NavigationDrawerItem(
            title = R.string.share , selectedIcon = Icons.Outlined.Share
        ) ,
    )

    val selectedItemIndex : Int by rememberSaveable { mutableIntStateOf(value = - 1) }

    ModalNavigationDrawer(modifier = Modifier.hapticDrawerSwipe(drawerState) ,
                          drawerState = drawerState ,
                          drawerContent = {
                              ModalDrawerSheet {
                                  Spacer(modifier = Modifier.height(16.dp))
                                  drawerItems.forEachIndexed { index , item ->
                                      val title : String = stringResource(id = item.title)
                                      NavigationDrawerItem(label = { Text(text = title) } ,
                                                           selected = index == selectedItemIndex ,
                                                           onClick = {
                                                               when (item.title) {
                                                                   R.string.settings -> {
                                                                       view.playSoundEffect(
                                                                           SoundEffectConstants.CLICK
                                                                       )
                                                                       IntentUtils.openActivity(
                                                                           context ,
                                                                           SettingsActivity::class.java
                                                                       )
                                                                   }

                                                                   R.string.help_and_feedback -> {
                                                                       view.playSoundEffect(
                                                                           SoundEffectConstants.CLICK
                                                                       )
                                                                       IntentUtils.openActivity(
                                                                           context ,
                                                                           HelpActivity::class.java
                                                                       )
                                                                   }

                                                                   R.string.updates -> {
                                                                       view.playSoundEffect(
                                                                           SoundEffectConstants.CLICK
                                                                       )
                                                                       IntentUtils.openUrl(
                                                                           context ,
                                                                           url = "https://github.com/D4rK7355608/${context.packageName}/blob/master/CHANGELOG.md"
                                                                       )
                                                                   }

                                                                   R.string.share -> {
                                                                       view.playSoundEffect(
                                                                           SoundEffectConstants.CLICK
                                                                       )
                                                                       IntentUtils.shareApp(context)
                                                                   }
                                                               }
                                                               coroutineScope.launch { drawerState.close() }
                                                           } ,
                                                           icon = {
                                                               Icon(
                                                                   item.selectedIcon ,
                                                                   contentDescription = title
                                                               )
                                                           } ,
                                                           modifier = Modifier
                                                                   .padding(
                                                                       NavigationDrawerItemDefaults.ItemPadding
                                                                   )
                                                                   .bounceClick())
                                  }
                              }
                          } ,
                          content = {
                              MainScreenContent(
                                  view = view ,
                                  drawerState = drawerState ,
                                  context = context ,
                                  coroutineScope = coroutineScope
                              )
                          })
}