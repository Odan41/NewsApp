package com.example.umte_project_news.data.api

import com.example.newsapp.util.Constants
import com.example.newsapp.util.Constants.Companion.BASE_URL
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

private val json = Json {
    ignoreUnknownKeys = true
}
private fun createRetrofit(
    client: OkHttpClient,
    baseUrl: String = BASE_URL,
) = Retrofit.Builder().apply {
    client(client)
    baseUrl(baseUrl)
    addConverterFactory(
        json.asConverterFactory(
            MediaType.get("application/json")
        )
    )
}.build()
private fun createClient() = OkHttpClient.Builder().build()