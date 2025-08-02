package com.ujjwal.mytriviaapp.network

import com.ujjwal.mytriviaapp.model.Question
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface QuestionAPI {
    @GET("world.json")
    suspend fun getAllQuestions():Question
}