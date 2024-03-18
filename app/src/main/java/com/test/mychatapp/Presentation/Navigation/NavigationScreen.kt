package com.test.mychatapp.Presentation.Navigation

sealed class NavigationScreen(val route: String) {
    object LoginScreen:NavigationScreen("login-Screen")
    object SignUpScree:NavigationScreen("signupScreen")
    object ChatRoomScreen:NavigationScreen("chatRoomScreen")
    object ChatScreen:NavigationScreen("chatScreen")
}