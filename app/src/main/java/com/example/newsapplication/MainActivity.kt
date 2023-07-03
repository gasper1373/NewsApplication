package com.example.newsapplication

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.NewsScreen
import com.example.Screen
import com.example.newsapplication.ui.theme.NewsApplicationTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    private lateinit var viewModel: NewsViewModel

    private lateinit var navController: NavHostController


    @SuppressLint("ComposableDestinationInComposeScope", "RememberReturnType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsApplicationTheme {
                val newsRepository = NewsRepository(ArticleDatabase(this))
                val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)
                viewModel =
                    ViewModelProvider(this, viewModelProviderFactory)[NewsViewModel::class.java]
                val breakingNewsState by viewModel.breakingNews.observeAsState()
                val articles = breakingNewsState?.data?.articles ?: emptyList()
                navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.Home.route
                ) {
                    composable(
                        route = Screen.Home.route,

                        ) {
                        NewsScreen(viewModel = viewModel, article = articles, navController)
                    }
                    composable(
                        "web_view?url={url}",
                        arguments = listOf(navArgument("url") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val url = backStackEntry.arguments?.getString("url")
                        WebViewScreen(url = url ?: "https://google.com")
                    }
                }
            }
        }
    }
}


/*
                            navBackStackEntity ->
                        val article = navBackStackEntity.arguments?.getString("articleId")
                        if (article != null)
                            DetailScreen(articleId = article as Article, viewModel)
                        else {
                            /*
                            article is always null i have to figure it out how to pass values
                             */
                            Column(
                                modifier = Modifier
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    "No articles loser",
                                    fontSize = 64.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                             */
