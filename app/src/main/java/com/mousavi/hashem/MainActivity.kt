package com.mousavi.hashem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mousavi.hashem.ui.theme.ComposeIndicatorTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Content()
        }
    }
}

@Composable
private fun Content() {
    ComposeIndicatorTheme {
        var value by remember {
            mutableStateOf("")
        }

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Indicator(
                    value = if (value.isNotBlank()) value.toInt() else 0
                )
                Spacer(modifier = Modifier.padding(vertical = 20.dp))
                TextField(
                    value = value,
                    onValueChange = {
                        value = it
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    )
                )
            }
        }
    }
}

@Composable
fun Indicator(
    backgroundColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.1f),
    indicatorColor: Color = MaterialTheme.colors.primary,
    max: Int = 100,
    value: Int
) {
    val v = if (value >= max) max else value

    val anim = animateFloatAsState(
        targetValue = 2.4f * v,
        animationSpec = tween(1000)
    )

    Column(
        modifier = Modifier
            .padding(25.dp)
            .size(300.dp)
            .drawBehind {
                drawBackgroundIndicator(backgroundColor)
                drawForegroundIndicator(indicatorColor, anim.value)
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "weight")
        Text(text = "${(anim.value/2.4f).roundToInt()} KG", fontSize = 20.sp)
    }
}

private fun DrawScope.drawBackgroundIndicator(backgroundColor: Color) {
    drawArc(
        color = backgroundColor,
        startAngle = 30f,
        sweepAngle = -240f,
        useCenter = false,
        style = Stroke(
            width = 25.dp.toPx(),
            cap = StrokeCap.Round
        )
    )
}


private fun DrawScope.drawForegroundIndicator(indicatorColor: Color, value: Float) {
    drawArc(
        color = indicatorColor,
        startAngle = 150f,
        sweepAngle = value,
        useCenter = false,
        style = Stroke(
            width = 40.dp.toPx(),
            cap = StrokeCap.Round
        )
    )
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Content()
}