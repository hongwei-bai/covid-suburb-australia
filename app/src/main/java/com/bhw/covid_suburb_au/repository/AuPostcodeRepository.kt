package com.bhw.covid_suburb_au.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.bhw.covid_suburb_au.datasource.model.AuPostcodeSource
import com.bhw.covid_suburb_au.datasource.room.AuPostcodeDao
import com.bhw.covid_suburb_au.datasource.room.AuPostcodeEntity
import com.bhw.covid_suburb_au.datasource.room.AuSuburbEntity
import com.bhw.covid_suburb_au.util.LocalJsonReader
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AuPostcodeRepository @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val auPostcodeDao: AuPostcodeDao
) {
    suspend fun getPostcodeBriefByPrefix(postcodePrefix: Long, limit: Int): List<AuPostcodeEntity> =
        when (postcodePrefix) {
            in 0..9 -> auPostcodeDao.findPostcodesInRange(postcodePrefix * 1000, postcodePrefix * 1000 + limit)
            in 10..99 -> auPostcodeDao.findPostcodesInRange(postcodePrefix * 100, postcodePrefix * 100 + limit)
            in 100..999 -> auPostcodeDao.findPostcodesInRange(postcodePrefix * 10, postcodePrefix * 10 + limit)
            else -> emptyList()
        }.filterIndexed { i, _ -> i < limit }

    suspend fun getPostcodeBriefByPrefix0xxx(postcodePrefix: Long, limit: Int): List<AuPostcodeEntity> =
        when (postcodePrefix) {
            in 0..9 -> auPostcodeDao.findPostcodesInRange(postcodePrefix * 100, postcodePrefix * 100 + limit)
            in 10..99 -> auPostcodeDao.findPostcodesInRange(postcodePrefix * 10, postcodePrefix * 10 + limit)
            else -> emptyList()
        }.filterIndexed { i, _ -> i < limit }

    suspend fun getPostcode(postcode: Long): AuPostcodeEntity? =
        auPostcodeDao.findPostcode(postcode)

    suspend fun getPostcodes(): List<AuPostcodeEntity> = auPostcodeDao.getAllPostcodes()

    fun getPostcodesPagingSource(): PagingSource<Int, AuPostcodeEntity> = auPostcodeDao.getPostcodesPagingSource()

    suspend fun checkNeedInitialization(): Boolean =
        auPostcodeDao.getAllPostcodes().size >= 3000

    suspend fun initialize(): Boolean {
        Log.w("bbbb", "initialize Australian postcodes.")
        val postcodeList = Gson().fromJson<List<AuPostcodeSource>>(
            LocalJsonReader.loadJSONFromAsset(appContext, "au_postcodes.json"),
            object : TypeToken<List<AuPostcodeSource>>() {}.type
        ).groupBy { it.postcode }.map { postcodeToInfoMap ->
            val list = postcodeToInfoMap.value
            AuPostcodeEntity(
                postcode = postcodeToInfoMap.key,
                suburbs = list.map {
                    AuSuburbEntity(
                        suburb = it.place_name,
                        stateCode = it.state_code,
                        state = it.state_name,
                        longitude = it.longitude,
                        latitude = it.latitude,
                        accuracy = it.accuracy
                    )
                }
            )
        }

        auPostcodeDao.clear()
        auPostcodeDao.saveAll(postcodeList)
        return true
    }
}