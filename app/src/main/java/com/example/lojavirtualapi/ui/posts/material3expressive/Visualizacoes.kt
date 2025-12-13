@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.lojavirtualapi.ui.posts.material3expressive

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.danilloteles.appnetflixapi.ui.theme.BLACK

@Composable
fun Visualizacoes(
    visualizacoes: Int,
    modifier: Modifier = Modifier
) {
    TooltipBox(
        positionProvider = TooltipDefaults.rememberTooltipPositionProvider(TooltipAnchorPosition.Above),
        tooltip = {
            PlainTooltip {
                Text("$visualizacoes visualizações")
            }
        },
        state = rememberTooltipState(),
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(4.dp)
        ) {
            IconButton(onClick = { /* Ação de visualizações */ }) {
                Icon(
                    imageVector = Icons.Filled.Visibility,
                    contentDescription = "Visualizações",
                    tint = BLACK
                )
            }
            Text(
                text = visualizacoes.toString(),
                style = MaterialTheme.typography.bodyMedium,
                color = BLACK
            )
        }
    }
}