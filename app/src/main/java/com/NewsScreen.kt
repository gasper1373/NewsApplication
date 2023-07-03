package com

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.newsapplication.Article
import com.example.newsapplication.NewsViewModel
import com.example.newsapplication.R
import com.example.newsapplication.Resource

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(
    viewModel: NewsViewModel,
    article: List<Article>,
    navController: NavController
) {
    val searchNewState by viewModel.searchNews.observeAsState()
    Scaffold(
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            SearchBar(
                onSearch = { query ->
                    viewModel.searchNews(query)
                }
            )
            when (searchNewState) {
                is Resource.Loading -> {
                    Box(
                        modifier = Modifier
                            .align(CenterHorizontally)
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is Resource.Success -> {
                    val searchResults = (searchNewState as Resource.Success).data!!.articles
                    LazyColumn {
                        items(searchResults) { article ->
                            NewsItem(article = article) {
                                navController.navigate("web_view?url=${Uri.encode(article.url)}")
                            }
                        }
                    }
                }
                is Resource.Error -> {
                    val errorMessage = (searchNewState as Resource.Error).message
                }
                else -> {}
            }//When
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                items(article) { articles ->
                    NewsItem(article = articles) { articleId ->
                        navController.navigate("web_view?url=${Uri.encode(articles.url)}")
                    }
                }
            }//LazyColumn
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun NewsItem(
    article: Article,
    onClick: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 8.dp,
                top = 8.dp
            )
            .clickable { onClick(article.id.toString()) }
            .border(
                width = 1.dp,
                Color.Black,
                shape = RoundedCornerShape(4.dp)
            ), elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ), shape = RoundedCornerShape(4.dp)
    ) {
        Image(
            painter = rememberImagePainter(
                article.urlToImage,
                builder = {
                    crossfade(true)
                    placeholder(R.drawable.baseline_image_not_supported_24)
                }
            ),
            contentDescription = null,
            modifier = Modifier
                .height(120.dp)
                .fillMaxWidth()
                .clip(
                    shape = RoundedCornerShape(6.dp)
                ),
            contentScale = ContentScale.FillWidth
        )

        Text(
            text = article.title,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(8.dp)
        )
        Text(
            text = article.description ?: "",
            style = TextStyle(fontSize = 14.sp),
            modifier = Modifier.padding(8.dp),
        )
        Box(
            modifier = Modifier.align(End)
                .padding(8.dp)
        ){
            IconButton(onClick = { /*TODO*/ },
                modifier = Modifier.size(25.dp)
            ) {
                Icon(Icons.Filled.Favorite, contentDescription = "Favorite")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    onSearch: (String) -> Unit
) {
    var searchText by remember {
        mutableStateOf("")
    }
    TextField(
        value = searchText,
        onValueChange = { searchText = it },
        placeholder = { Text(text = "Search articles") },
        modifier = Modifier
            .fillMaxWidth(),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = { onSearch(searchText) }
        ),
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
            )
        }
    )
}


/*
//
//@OptIn(ExperimentalCoilApi::class)
//@Composable
//fun ArticleDetailScreen(
//    article: Article,
//) {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        article.title?.let {
//            Text(
//                text = it,
//                style = MaterialTheme.typography.titleLarge,
//                modifier = Modifier.padding(bottom = 8.dp)
//            )
//        }
//        article.description.let {
//            Text(
//                text = it,
//                style = MaterialTheme.typography.titleSmall,
//                modifier = Modifier.padding(bottom = 8.dp)
//            )
//        }
//        Image(
//            painter = rememberImagePainter(article.urlToImage),
//            contentDescription = null,
//            modifier = Modifier
//                .height(200.dp)
//                .fillMaxWidth()
//                .clip(shape = RoundedCornerShape(4.dp)),
//            contentScale = ContentScale.Crop
//        )
//        article.content.let {
//            Text(
//                text = it,
//                style = MaterialTheme.typography.bodySmall,
//                modifier = Modifier.padding(vertical = 16.dp)
//            )
//        }
//        article.publishedAt?.let {
//            Text(
//                text = it,
//                style = MaterialTheme.typography.bodySmall
//            )
//        }
//    }
//}


 */

