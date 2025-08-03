package com.ujjwal.mytriviaapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ujjwal.mytriviaapp.components.Questions

@Composable
fun TriviaHome(innerPadding: PaddingValues, viewModel: QuestionsViewModel = hiltViewModel()) {
    Column(modifier = Modifier.padding(innerPadding)) {
        Questions(viewModel)
    }
}
