package com.yunuscagliyan.cinemaapp.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.yunuscagliyan.cinemaapp.presentation.state.NetworkState

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state = viewModel.upComingState.value
    val upComingMovies = viewModel.upComingMovies.value

    Scaffold(
        topBar = {

        }
    ) {
        when(state){
            is NetworkState.Error -> {
                Text(
                    text = state.message?:"",

                )
            }
            NetworkState.Loading ->{
                CircularProgressIndicator()
            }
            NetworkState.Success ->{
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ){
                    items(upComingMovies.size){index->
                        val upComing=upComingMovies[index]
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .background(Color.Blue)
                        )
                        Spacer(modifier = Modifier.padding(top = 16.dp))
                    }
                }
            }
        }
    }

}