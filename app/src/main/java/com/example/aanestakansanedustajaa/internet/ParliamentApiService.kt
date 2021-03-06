package com.example.aanestakansanedustajaa.internet

import com.example.aanestakansanedustajaa.database.ParliamentData
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://avoindata.eduskunta.fi/" // Base url for the API

// create an instance of Moshi
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

// create an instance of Retrofit and pass an instance of MoshiConverter
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ParliamentApiService {
    @GET("api/v1/seating/") //End point of the API
    suspend fun getParliamentRecords(): List<ParliamentData>
}

object ParliamentApi {
    val retrofitService : ParliamentApiService by lazy {
        retrofit.create(ParliamentApiService::class.java) }
}