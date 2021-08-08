package com.bhw.covid_suburb_au.datasource.network.service

import com.bhw.covid_suburb_au.datasource.network.model.NbaTeamTheme
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NbaThemeService {
    @GET("teamTheme.do")
    suspend fun getTeamTheme(
        @Query("team") team: String,
        @Query("dataVersion") dataVersion: Long = -1
    ): Response<NbaTeamTheme>

    @GET("teamTheme.do")
    suspend fun getTeamThemeFlow(
        @Query("team") team: String,
        @Query("dataVersion") dataVersion: Long = -1
    ): Flow<Response<NbaTeamTheme>>
}