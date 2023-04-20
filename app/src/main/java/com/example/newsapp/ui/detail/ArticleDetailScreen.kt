package com.example.newsapp.ui.detail

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.rememberAsyncImagePainter
import com.example.newsapp.data.db.entities.ArticleEntity
import com.example.newsapp.data.models.Article
import com.example.newsapp.data.models.response.ArticleResponse
import com.example.newsapp.data.models.response.NewsResponse
import com.example.newsapp.ui.base.State
import com.example.newsapp.ui.favourite.RoomViewModel
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ArticleDetailScreen(
    articleName: String,
    viewModel: ArticleDetailViewModel = getViewModel {
        parametersOf(articleName)
    },
    onBack: (String) -> Unit = {},
) {
    val state = viewModel.state.collectAsState()
    val detail = viewModel.articleDetail.collectAsState()
    val context = LocalContext.current

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
                    ArticleDetailView(detail.articles.first(),context,onBack=onBack)
                } ?: run {
                    Text(text = "No data available")
                }
            }
        }
    }


}

@Composable
fun ArticleDetailView(detail: NewsResponse.Article, context: Context, viewModel: RoomViewModel = getViewModel (), onBack: (String) -> Unit = {},) {

    val articles = viewModel.articles.collectAsState(emptyList())
    val isDatabaseArticle = viewModel.isDatabaseArticle.collectAsState()
    val dialogShow  = remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        // ((Activity) context) java
                        /*
                            if (context instanceof Activity) {
                                ((Activity) context).finish()
                            }
                         */
                        onBack
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                        )
                    }
                },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary,
                elevation = 12.dp,
            )
        }
    ) {
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
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = detail.title,
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = detail.author.orEmpty())
        Text(text = detail.publishedAt)
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = detail.content.orEmpty())
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = {
            launchSource(context,detail.url)
        }) {
            Text("Open full article in browser")
        }
        if (isDatabaseArticle(detail.title,articles.value)) {
            Button(onClick = {
                viewModel.removeArticle(detail)
                dialogShow.switch()
            }) {
                Text("Remove article to favourites")
            }
        }
        else{
            Button(onClick = {
                viewModel.addArticle(detail)
                dialogShow.switch()
            }) {
                Text("Save article to favourites")
            }
        }



        //Button(onClick = {dialogShow.switch()}){
        //    Text(text = "${if (dialogShow.value) "Hide" else "Show"} dialog")
        //}

        if(dialogShow.value){
            if (isDatabaseArticle(detail.title,articles.value)) {
                AlertDialog(
                    onDismissRequest = { dialogShow.switch() },
                    buttons = {
                        TextButton(onClick = { dialogShow.switch() }) {
                            Text("Ok")
                        }
                    },
                    title = { Text("Favourite") },
                    text = { Text("Succesfully removed from favourite") },
                )
            }
            else{
                AlertDialog(
                    onDismissRequest = { dialogShow.switch() },
                    buttons = {
                        TextButton(onClick = { dialogShow.switch() }) {
                            Text("Ok")
                        }
                    },
                    title = { Text("Favourite") },
                    text = { Text("Succesfully saved to favourite") },
                )
            }
        }

    }}
}

private fun launchSource(context: Context, uri:String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
    ContextCompat.startActivity(
        context,
        intent,
        null,
    )
}

private fun isDatabaseArticle(articleName:String, articles: List<ArticleEntity>):Boolean{
 val result = articles.find{it.title.contains(articleName)} ?: return false
    return true
}

fun MutableState<Boolean>.switch() {
    value = value.not()
}