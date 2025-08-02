package com.ujjwal.mytriviaapp.repository

import com.ujjwal.mytriviaapp.data.DataOrException
import com.ujjwal.mytriviaapp.model.Question
import com.ujjwal.mytriviaapp.network.QuestionAPI
import javax.inject.Inject

class QuestionRepository @Inject constructor(
    private val api:QuestionAPI
) {
    private val listOfQuestions=DataOrException<ArrayList<Question>,Boolean,Exception>()

}