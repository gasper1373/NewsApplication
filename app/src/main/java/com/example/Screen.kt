package com.example


sealed class Screen(val route : String){
    object Home : Screen(route = "home_screen")
    object Detail : Screen(route = "detail_screen/{url}")
    object Saved : Screen(route = "detail_screen/{saved}")
    object Category : Screen(route = "detail_screen/{category}")
//    object Detail : Screen(route = "detail_screen/{source}")

}
