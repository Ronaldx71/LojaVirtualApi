package com.example.lojavirtualapi.ui.posts.material3expressive

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun MeuLoading(
    modifier: Modifier = Modifier
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        LoadingIndicator()
    }
}

@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
    strokeWidth: Float = 6f
) {
    val infiniteTransition = rememberInfiniteTransition(label = "loading")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    Canvas(
        modifier = modifier.size(56.dp)
    ) {
        drawLoadingIndicator(
            rotation = rotation,
            color = color,
            strokeWidth = strokeWidth
        )
    }
}

private fun DrawScope.drawLoadingIndicator(
    rotation: Float,
    color: Color,
    strokeWidth: Float
) {
    val center = this.center
    val radius = size.minDimension / 2 - strokeWidth / 2

    // Desenha múltiplos arcos para criar um efeito mais expressivo
    for (i in 0..3) {
        val startAngle = rotation + (i * 90f)
        val sweepAngle = 60f - (i * 10f)
        val alpha = 1f - (i * 0.2f)

        drawArc(
            color = color.copy(alpha = alpha),
            startAngle = startAngle,
            sweepAngle = sweepAngle,
            useCenter = false,
            style = Stroke(
                width = strokeWidth,
                cap = StrokeCap.Round
            )
        )
    }
}

@Preview
@Composable
private fun MeuLoadingIndicatorPreview() {
    MeuLoading()
}