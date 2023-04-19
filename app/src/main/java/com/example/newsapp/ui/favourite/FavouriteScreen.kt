package com.example.newsapp.ui.favourite

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import org.koin.androidx.compose.getViewModel

@Composable
fun FavouriteScreen(
    viewModel: RoomViewModel = getViewModel(),
    onNavigateDetail: (String) -> Unit = {},
){
    val articles = viewModel.articles.collectAsState(emptyList())

    Column{
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(all = 8.dp),
        ) {
            items(
                items = articles.value,
                ) { article ->
                Card {
                    Row(
                        modifier = Modifier.clickable {
                            onNavigateDetail(article.title)
                        }
                            .padding(16.dp)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(article.urlToImage),
                            contentDescription = null,
                            modifier = Modifier.size(150.dp)
                        )

                        Column(
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Text(
                                text = article.title ?: "-",
                                style = MaterialTheme.typography.h6,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = article.publishedAt ?: "-",
                            )
                        }

                    }
                }
            }
        }
    }
}

