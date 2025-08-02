package com.ujjwal.mytriviaapp.screens

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ujjwal.mytriviaapp.data.DataOrException
import com.ujjwal.mytriviaapp.model.Question
import com.ujjwal.mytriviaapp.repository.QuestionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionsViewModel @Inject constructor(
    private val repository: QuestionRepository
) : ViewModel() {
    private val data = mutableStateOf(DataOrException(Question(), true, Exception("")))

    init {
        getAllQuestions()
    }

    private fun getAllQuestions() {
        viewModelScope.launch {
            data.value.loading = true
            data.value = repository.getAllQuestion()
            if (data.value.data.toString().isNotEmpty()) data.value.loading = false
        }
    }
}