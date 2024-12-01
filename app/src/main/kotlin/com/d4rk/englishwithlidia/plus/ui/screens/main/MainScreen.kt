package com.d4rk.englishwithlidia.plus.ui.screens.main

import android.content.Context
import android.view.View
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.d4rk.englishwithlidia.plus.ui.components.navigation.NavigationDrawer
import com.d4rk.englishwithlidia.plus.ui.components.navigation.TopAppBarMain
import com.d4rk.englishwithlidia.plus.ui.screens.home.HomeScreen
import com.d4rk.englishwithlidia.plus.ui.screens.home.HomeViewModel
import kotlinx.coroutines.CoroutineScope

@Composable
fun MainScreen() {
    val drawerState : DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val context : Context = LocalContext.current
    val view : View = LocalView.current

    NavigationDrawer(
        drawerState = drawerState ,
        view = view ,
        context = context ,
    )
}

@Composable
fun MainScreenContent(
    view : View , drawerState : DrawerState , context : Context , coroutineScope : CoroutineScope
) {
    val viewModel : HomeViewModel = viewModel()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(topBar = {
        TopAppBarMain(
            view = view ,
            drawerState = drawerState ,
            context = context ,
            coroutineScope = coroutineScope
        )
    } , snackbarHost = {
        SnackbarHost(hostState = snackbarHostState)
    }) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues)
        ) {
            HomeScreen(
                context = context ,
                view = view ,
                viewModel = viewModel ,
                snackbarHostState = snackbarHostState
            )
        }
    }
}