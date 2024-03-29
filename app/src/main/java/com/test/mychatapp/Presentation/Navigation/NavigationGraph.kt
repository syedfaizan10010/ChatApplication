package com.test.mychatapp.Presentation.Navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.test.mychatapp.Presentation.View.ChatRoomListScreen
import com.test.mychatapp.Presentation.View.ChatScreen
import com.test.mychatapp.Presentation.View.SignUpScreen
import com.test.mychatapp.Presentation.View.SigninScreen
import com.test.mychatapp.Presentation.ViewModel.AuthViewModel

@Composable
fun NavigationGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel
){
    NavHost(navController = navController,
            startDestination = NavigationScreen.SignUpScreen.route){
        composable(NavigationScreen.SignUpScreen.route){
            SignUpScreen (
                authViewModel =authViewModel,
                onNavigateToLogin ={
                    navController.navigate(NavigationScreen.LoginScreen.route)
                }
            )
        }
        composable(NavigationScreen.LoginScreen.route){
            SigninScreen (
                authViewModel = authViewModel,
                onNavigateToSignUp = {
                    navController.navigate(NavigationScreen.SignUpScreen.route)
                })
                {
                    navController.navigate(NavigationScreen.ChatRoomScreen.route)
                }
        }
        composable(NavigationScreen.ChatRoomScreen.route) {
            ChatRoomListScreen{
                navController.navigate("${NavigationScreen.ChatScreen.route}/${it.id}")
            }
        }
        composable("${NavigationScreen.ChatScreen.route}/{roomId}"){
            val roomId: String = it.arguments?.getString("roomId") ?: ""
            ChatScreen(roomId = roomId)

        }

    }
}