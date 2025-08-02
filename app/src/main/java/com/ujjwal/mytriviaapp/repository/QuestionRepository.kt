package com.ujjwal.mytriviaapp.repository

import android.util.Log
import com.ujjwal.mytriviaapp.data.DataOrException
import com.ujjwal.mytriviaapp.model.Question
import com.ujjwal.mytriviaapp.network.QuestionAPI
import javax.inject.Inject

class QuestionRepository @Inject constructor(
    private val api: QuestionAPI
) {
    private val TAG = "QuestionRepository"
    private val dataOrException = DataOrException<Question, Boolean, Exception>()

    suspend fun getAllQuestion(): DataOrException<Question, Boolean, Exception> {
        try {
            dataOrException.loading = true
            dataOrException.data = api.getAllQuestions()
            if (dataOrException.data.toString().isNotEmpty()) dataOrException.loading = false

        } catch (e: Exception) {
            dataOrException.e = e
            Log.d(TAG, "Exception Occurred: ${e.localizedMessage} ")
        }
        return dataOrException
    }
}