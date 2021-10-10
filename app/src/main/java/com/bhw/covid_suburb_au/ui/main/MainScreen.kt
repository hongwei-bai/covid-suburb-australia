package com.bhw.covid_suburb_au.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.bhw.covid_suburb_au.ui.dashboard.Dashboard
import com.bhw.covid_suburb_au.ui.map.CovidSuburbMap
import com.bhw.covid_suburb_au.ui.news.News
import com.bhw.covid_suburb_au.ui.settings.Settings
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainScreen() {
    val coroutineScope = rememberCoroutineScope()
    val pages = listOf(
        Screen.Dashboard,
        Screen.Map,
        Screen.News,
        Screen.Settings
    )
    val pagerState = rememberPagerState(pages.size)
    object : PagerListener {
        override fun onBackToDashboard() {
            coroutineScope.launch {
                pagerState.animateScrollToPage(0)
            }
        }
    }
    Scaffold(bottomBar = { BottomNavBar(pagerState) }) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            HorizontalPager(state = pagerState) { page ->
                when (page) {
                    0 -> Dashboard()
                    1 -> CovidSuburbMap()
                    2 -> News()
                    3 -> Settings()
                    else -> Unit
                }
            }
        }
    }
}

interface PagerListener {
    fun onBackToDashboard()
}