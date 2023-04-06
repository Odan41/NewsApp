package com.example.newsapp.ui.detail

import androidx.compose.runtime.Composable
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun RocketDetailScreen(
    articleId: String,
    viewModel: ArticleDetailViewModel = getViewModel {
        parametersOf(articleId)
    },
) {

}