package com.example.newsapp.ui.favourite

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import org.koin.androidx.compose.getViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun FavouriteScreen(
    viewModel: RoomViewModel = getViewModel(),
    onNavigateDetail: (String) -> Unit = {},
){
    val articles = viewModel.articles.collectAsState(emptyList())


    Column{
        Text(
            text = "Favourite",
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            color = MaterialTheme.colors.primary,
            modifier = Modifier.padding(8.dp)
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(all = 8.dp),
        ) {
            items(
                items = articles.value,
                ) { article ->
                Card (
                    backgroundColor = MaterialTheme.colors.secondary,
                    shape = RoundedCornerShape(10.dp),
                    elevation = 4.dp

                ){
                    Row(
                        modifier = Modifier.clickable {
                            onNavigateDetail(article.title)
                        }
                            .padding(8.dp)
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(article.urlToImage),
                            contentDescription = null,
                            modifier = Modifier.size(150.dp).padding(8.dp)
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
                                text = convertDate(article.publishedAt) ?: "-",
                            )
                        }

                    }
                }
            }
        }
    }
}

private fun convertDate(apiDateString:String):String{
    val apiDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    apiDateFormat.timeZone = TimeZone.getTimeZone("UTC")
    val apiDate = apiDateFormat.parse(apiDateString)

    val articleDateFormat = SimpleDateFormat("dd. MM. yyyy HH:mm", Locale.getDefault())
    articleDateFormat.timeZone = TimeZone.getDefault()
    val articleDate = articleDateFormat.format(apiDate)
    return articleDate
}

