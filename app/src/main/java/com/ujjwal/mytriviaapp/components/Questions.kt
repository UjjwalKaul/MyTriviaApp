package com.ujjwal.mytriviaapp.components

import android.text.Layout
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ujjwal.mytriviaapp.model.QuestionItem
import com.ujjwal.mytriviaapp.screens.QuestionsViewModel
import com.ujjwal.mytriviaapp.util.AppColors


@Composable
fun Questions(viewModel: QuestionsViewModel = hiltViewModel()) {
    val questions = viewModel.data.value.data?.toMutableList()
    val questionIndex=remember { mutableIntStateOf(0) }

    if (viewModel.data.value.loading == true) {
        CircularProgressIndicator()
    } else {
        val question = try {
            questions?.get(questionIndex.intValue)
        } catch (e: Exception) {
            null
        }
        if(question!=null){
            QuestionDisplay(
                question = question,
                questionIndex = questionIndex,
                viewModel = viewModel,
                onNext = {
                    questionIndex.intValue=it+1
                }
            )
        }
    }
    Log.d("Questions", "Questions: $questions")
}

//@Preview
@Composable
fun QuestionDisplay(
    question: QuestionItem,
    questionIndex: MutableState<Int>,
    viewModel: QuestionsViewModel,
    onNext: (Int) -> Unit
) {
    val choicesState = remember(question) { question.choices.toMutableList() }
    val answerState = remember(question) { mutableStateOf<Int?>(null) }
    val correctAnswerState = remember(question) { mutableStateOf<Boolean?>(null) }
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)

    val updateAnswer: (Int) -> Unit = remember(question) {
        { it: Int ->
            answerState.value = it
            correctAnswerState.value = choicesState[it] == question.answer
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = AppColors.mDarkGray
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            if(questionIndex.value>=1) ShowProgressMeter(score = questionIndex.value+1)

            QuestionTracker(counter = questionIndex.value+1)

            DottedLine(pathEffect)

            Column {
                Text(
                    modifier = Modifier.fillMaxHeight(0.3f),
                    text = question.question,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = AppColors.mTrueWhite,
                    lineHeight = 22.sp
                )

                choicesState.forEachIndexed { index, answerText ->
                    Row(
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth()
                            .height(50.dp)
                            .border(
                                width = 5.dp, brush = Brush.linearGradient(
                                    colors = listOf(AppColors.mLightGreen, AppColors.mLightBlue)
                                ), shape = RoundedCornerShape(15.dp)
                            )
                            .clip(RoundedCornerShape(15.dp))
                            .background(Color.Transparent),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (answerState.value == index),
                            onClick = {
                                updateAnswer(index)
                            },
                            modifier = Modifier.padding(start = 15.dp),
                            colors = RadioButtonDefaults.colors(
                                selectedColor = if (correctAnswerState.value == true && index == answerState.value) Color.Green.copy(
                                    alpha = 0.3f
                                ) else Color.Red.copy(alpha = 0.3f)
                            )
                        )
                        val annotatedString=buildAnnotatedString {
                            withStyle(style = SpanStyle(
                                fontWeight = FontWeight.Light,
                                color = if(correctAnswerState.value == true && index == answerState.value){
                                    Color.Green
                                }else if(correctAnswerState.value == false && index == answerState.value){
                                    Color.Red
                                }else{
                                    AppColors.mOffWhite
                                },
                                fontSize = 20.sp
                            )
                            ){
                                append(answerText)
                            }
                        }
                        Text(annotatedString)
                    }
                }
                Button(
                    onClick = {
                        onNext(questionIndex.value)
                    },
                    modifier = Modifier
                        .padding(5.dp)
                        .align(Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(15.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppColors.mIndigo
                    )
                ) {
                    Text("Next", fontWeight = FontWeight.Bold, color = AppColors.mTrueWhite, fontSize = 20.sp)
                }
            }
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
                    color = AppColors.mLightGreen, fontWeight = FontWeight.Light, fontSize = 20.sp
                )
            ) {
                append("$outOff")
            }
        }, modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    )
}

//@Preview
@Composable
fun DottedLine(pathEffect: PathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(2.dp)
    ) {
        drawLine(
            color = AppColors.mTrueWhite,
            start = Offset(0f, 0f),
            end = Offset(size.width, 0f),
            pathEffect = pathEffect
        )
    }
}


@Preview()
@Composable
fun ShowProgressMeter(score: Int = 3) {
    val gradient = Brush.linearGradient(listOf(Color(0xFFF95075), Color(0xFFBE6BE5)))
    val progressFactor = remember(score) { mutableFloatStateOf(score * 0.015f) }

    Box(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
            .height(50.dp)
            .clip(RoundedCornerShape(30.dp))
            .border(
                width = 5.dp,
                brush = SolidColor(AppColors.mCharcoal),
                shape = RoundedCornerShape(30.dp)
            )
            .background(Color.Transparent),
        contentAlignment = Alignment.CenterStart
    ) {
        // Progress bar
        Box(
            modifier = Modifier
                .fillMaxWidth(progressFactor.floatValue)
                .fillMaxHeight()
                .background(brush = gradient)
        )

        // Score text
        Text(
            text = (score * 10).toString(),
            color = AppColors.mOffWhite,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 20.dp)
        )
    }
}





















