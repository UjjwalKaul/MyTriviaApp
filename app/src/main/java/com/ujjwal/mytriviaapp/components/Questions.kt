package com.ujjwal.mytriviaapp.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ujjwal.mytriviaapp.screens.QuestionsViewModel
import com.ujjwal.mytriviaapp.util.AppColors


@Composable
fun Questions(viewModel: QuestionsViewModel = hiltViewModel()) {
    val questions = viewModel.data.value.data?.toMutableList()

    if (viewModel.data.value.loading == true) {
        CircularProgressIndicator()
    } else {
        questions?.forEach { question ->
            Column {
                Text(question.question)
            }
        }
    }
    Log.d("Questions", "Questions: $questions")
}

@Preview
@Composable
fun QuestionDisplay() {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp), color = AppColors.mDarkGray
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            QuestionTracker()
        }

    }
}


@Composable
fun QuestionTracker(counter: Int = 10, outOff: Int = 100) {
    Text(
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = AppColors.mLightGreen,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 30.sp
                )
            ) {
                append("Question $counter/")
            }
            withStyle(
                style = SpanStyle(
                    color = AppColors.mLightGreen,
                    fontWeight = FontWeight.Light,
                    fontSize = 20.sp
                )
            ) {
                append("$outOff")
            }
        },
        modifier = Modifier.padding(20.dp)
    )
}