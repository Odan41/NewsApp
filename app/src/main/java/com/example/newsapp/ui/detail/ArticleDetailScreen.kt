package com.example.newsapp.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.newsapp.data.models.response.ArticleResponse
import com.example.newsapp.ui.base.State
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ArticleDetailScreen(
    articleName: String,
    viewModel: ArticleDetailViewModel = getViewModel {
        parametersOf(articleName)
    },
) {
    val state = viewModel.state.collectAsState()
    val detail = viewModel.articleDetail.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        when (val result = state.value) {
            State.None, State.Loading -> {
                CircularProgressIndicator()
            }
            is State.Failure -> {
                Button(onClick = { result.repeat() }) {
                    Text(text= "Retry")
                }
            }
            is State.Success -> {
                detail.value?.let { detail ->
                    ArticleDetailView(detail)
                } ?: run {
                    Text(text = "No data available")
                }
            }
        }
    }


}

@Composable
fun ArticleDetailView(detail: ArticleResponse) {
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = rememberAsyncImagePainter(detail.urlToImage),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = detail.title,
            style = MaterialTheme.typography.h2,
            modifier = Modifier.weight(1F)
        )
        Text(text = detail.publishedAt)
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = detail.content)
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = detail.author)

    }
}