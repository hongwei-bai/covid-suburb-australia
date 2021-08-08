package com.bhw.covid_suburb_au.view.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.bhw.covid_suburb_au.repository.NbaTeamRepository
import com.bhw.covid_suburb_au.view.theme.NbaTeamTheme
import com.bhw.covid_suburb_au.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var nbaTeamRepository: NbaTeamRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val viewModel = hiltNavGraphViewModel<MainViewModel>()

            NbaTeamTheme(viewModel.teamTheme.observeAsState().value) {
                SystemUiController()

                NavComposeApp()
            }
        }
    }
}

@Composable
fun SystemUiController() {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = false //MaterialTheme.colors.isLight
    val systemBarColor = MaterialTheme.colors.primary

    SideEffect {
        // Update all of the system bar colors to be transparent, and use
        // dark icons if we're in light theme
        systemUiController.setSystemBarsColor(
            color = systemBarColor,
            darkIcons = useDarkIcons
        )

        // setStatusBarsColor() and setNavigationBarsColor() also exist
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NavComposeApp() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen(navController)
        }
        composable("main") {
            MainScreen()
        }
    }
}