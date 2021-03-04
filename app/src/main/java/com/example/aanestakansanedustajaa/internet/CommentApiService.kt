package com.example.aanestakansanedustajaa.internet

import com.example.aanestakansanedustajaa.database.CommentData
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://users.metropolia.fi/~jonne/" // Start point for the CommentAPI

// create an instance of Moshi
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

// create an instance of Retrofit and pass an instance of MoshiConverter
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface CommentApiService {
    // Get the whole API database
    @GET("eduskuntacomments/?action=getall")
    suspend fun getAllCommentRecords(): List<CommentData>
    // When user comments, it will take the hetekaID and the comment and send the info to API
    @GET("eduskuntacomments")
    suspend fun addComment(@Query("id") id:String, @Query("comment") comment:String, @Query("action") a:String="addcomment")
}

object CommentApi {
    val retrofitService : CommentApiService by lazy {
        retrofit.create(CommentApiService::class.java) }
}