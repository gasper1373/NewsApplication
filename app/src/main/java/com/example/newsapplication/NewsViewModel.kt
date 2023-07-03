package com.example.newsapplication

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Response

class NewsViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {


    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage = 1

    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    private var searchPageNumber = 1


    init {
        getBreakingNews("us")
    }

    private fun getBreakingNews(countryCode: String) = viewModelScope.launch {

        Log.d("API_CALL", "Fetching breaking news for country code: $countryCode")
        breakingNews.postValue(Resource.Loading())
        val response = newsRepository.getBreakingNews(countryCode, breakingNewsPage)
        breakingNews.postValue(handleBreakingNewsResponse(response))
    }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            Log.d("API_RESPONSE", "API call successful. Response: ${response.body()}")
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        Log.e("API_ERROR", "API call failed. Error message: ${response.message()}")
        return Resource.Error(response.message())
    }

    fun searchNews(searchQuery: String) = viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        val response = newsRepository.searchForNews(searchQuery, searchPageNumber)
        searchNews.postValue(handleSearch(response))
    }

    private fun handleSearch(response: Response<NewsResponse>): Resource<NewsResponse> {
        if (response.isSuccessful) {
            Log.d("API_RESPONSE", "API call successful. Response: ${response.body()}")
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        Log.e("API_ERROR", "API call failed. Error message: ${response.message()}")
        return Resource.Error(response.message())
    }

    fun saveArticle(article : Article) = viewModelScope.launch {
        newsRepository.upsert(article)
    }

    fun getSavedNews() = newsRepository.getSavedNews()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }
}
