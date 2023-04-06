package com.example.newsapp.data


import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.newsapp.data.api.NewsService
import com.example.newsapp.data.db.ArticleDatabase
import com.example.newsapp.data.repositories.NewsRepository
import com.example.newsapp.ui.detail.ArticleDetailViewModel
import com.example.newsapp.ui.homescreen.BreakingNewsViewModel
import com.example.newsapp.util.Constants.Companion.BASE_URL
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit

val appModules by lazy { listOf(dataModule, uiModule) }

val dataModule = module {
    repositories()
    apiServices()
}

val uiModule = module {
    viewModel { BreakingNewsViewModel(get()) }
    viewModel { (articleName: String) -> ArticleDetailViewModel(articleName, get()) }
}

private fun Module.repositories() {
    single { NewsRepository(get()) }
}

private fun Module.apiServices() {
    single { createRetrofit(createClient()) }
    single { get<Retrofit>().create(NewsService::class.java) }
}

//private fun Module.dataStorage() {
//    single { DataStorage(androidApplication().dataStore) }
//}

private fun Module.db() {
    // DB
    single {
        Room
            .databaseBuilder(
                context = androidApplication(),
                klass = ArticleDatabase::class.java,
                name = ArticleDatabase.Name,
            )
            .build()
    }
    // Dao
    single { get<ArticleDatabase>().articleDao() }
}


private val json = Json {
    ignoreUnknownKeys = true
}

@OptIn(ExperimentalSerializationApi::class)
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

private const val DataStoreName = "NewsDataStore"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(DataStoreName)