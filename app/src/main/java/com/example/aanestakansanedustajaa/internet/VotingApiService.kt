package com.example.aanestakansanedustajaa.internet

import com.example.aanestakansanedustajaa.database.ParliamentData
import com.example.aanestakansanedustajaa.database.VotingData
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://users.metropolia.fi/~jonne/" // Start point for the voteAPI

// create an instance of Moshi
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

// create an instance of Retrofit and pass an instance of MoshiConverter
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface VotingApiService {
    // Get the whole API database
    @GET("eduskunta/?action=getall")
    suspend fun getAllVotingRecords(): List<VotingData>
    // When user likes the member, it will take the hetekaID as the value and send the info to API
    @GET("eduskunta")
    suspend fun voteUp(@Query("id") id:String, @Query("action") a:String="plus")
    // When user dislikes the member, it will take the hetekaID as the value and send the info to API
    @GET("eduskunta")
    suspend fun voteDown(@Query("id") id: String, @Query("action") a:String="minus")
}

object VotingApi {
    val retrofitService : VotingApiService by lazy {
        retrofit.create(VotingApiService::class.java) }
}