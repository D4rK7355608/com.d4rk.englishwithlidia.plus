package com.d4rk.englishwithlidia.plus

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.EventNote
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.VolunteerActivism
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.d4rk.englishwithlidia.plus.data.model.ui.navigation.NavigationItem
import com.d4rk.englishwithlidia.plus.ui.help.HelpActivity
import com.d4rk.englishwithlidia.plus.ui.home.HomeComposable
import com.d4rk.englishwithlidia.plus.ui.settings.SettingsActivity
import com.d4rk.englishwithlidia.plus.ui.support.SupportActivity
import com.d4rk.englishwithlidia.plus.utils.Utils
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainComposable() {
    val drawerItems = listOf(
        NavigationItem(
            title = R.string.settings,
            selectedIcon = Icons.Outlined.Settings,
        ),
        NavigationItem(
            title = R.string.help_and_feedback,
            selectedIcon = Icons.AutoMirrored.Outlined.HelpOutline,
        ),
        NavigationItem(
            title = R.string.updates,
            selectedIcon = Icons.AutoMirrored.Outlined.EventNote,
        ),
        NavigationItem(
            title = R.string.share, selectedIcon = Icons.Outlined.Share
        ),
    )
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val selectedItemIndex by rememberSaveable { mutableIntStateOf(-1) }
    ModalNavigationDrawer(drawerState = drawerState, drawerContent = {
        ModalDrawerSheet {
            Spacer(modifier = Modifier.height(16.dp))
            drawerItems.forEachIndexed { index, item ->
                val title = stringResource(item.title)
                NavigationDrawerItem(
                    label = { Text(text = title) },
                    selected = index == selectedItemIndex,
                    onClick = {
                        when (item.title) {

                            R.string.settings -> {
                                Utils.openActivity(
                                    context, SettingsActivity::class.java
                                )
                            }

                            R.string.help_and_feedback -> {
                                Utils.openActivity(
                                    context, HelpActivity::class.java
                                )
                            }

                            R.string.updates -> {
                                Utils.openUrl(
                                    context,
                                    "https://github.com/D4rK7355608/${context.packageName}/blob/master/CHANGELOG.md"
                                )
                            }

                            R.string.share -> {
                                Utils.shareApp(context)
                            }
                        }
                        scope.launch {
                            drawerState.close()
                        }
                    },
                    icon = {
                        Icon(
                            item.selectedIcon, contentDescription = title
                        )
                    },
                    badge = {
                        item.badgeCount?.let {
                            Text(text = item.badgeCount.toString())
                        }
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
            }
        }

    }, content = {
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                        actionIconContentColor = MaterialTheme.colorScheme.onSecondary
                    ),
                    title = {
                        Text(text = stringResource(R.string.app_name))
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Menu, contentDescription = "Menu"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            Utils.openActivity(context, SupportActivity::class.java)
                        }) {
                            Icon(
                                Icons.Outlined.VolunteerActivism, contentDescription = "Support"
                            )
                        }
                    },
                )
            },
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                HomeComposable()
            }
        }
    })
}