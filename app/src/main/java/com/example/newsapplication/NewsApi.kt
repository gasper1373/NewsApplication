package com.example.newsapplication

import com.example.newsapplication.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {


    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        countryCode : String = "us",
        @Query("page")
        pageNumber : Int = 1,
        @Query("apikey")
        apiKey : String = API_KEY
    ) : Response<NewsResponse>


    @GET("v2/everything")
    suspend fun searchForNews(
        @Query("q")
        searchQuery : String  ,
        @Query("page")
        pageNumber : Int = 1,
        @Query("apikey")
        apiKey : String = API_KEY
    ) : Response<NewsResponse>

    //last
    @GET("v2/top-headlines/sources")
    suspend fun getArticleNews(
        @Query("id")
        id : String ,
        @Query("apikey")
        apiKey : String = API_KEY
    ) : Article

}