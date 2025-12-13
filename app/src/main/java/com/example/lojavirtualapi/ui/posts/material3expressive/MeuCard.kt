package com.example.lojavirtualapi.ui.posts.material3expressive

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.danilloteles.appnetflixapi.ui.theme.BLACK
import com.danilloteles.appnetflixapi.ui.theme.WHITE

@Composable
fun MeuCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    postId: Int,
    title: String,
    body: String
){
    OutlinedCard(
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = modifier.padding(16.dp)) {
            Surface(
                color = BLACK,
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "ID: $postId",
                    style = MaterialTheme.typography.titleMedium,
                    color = WHITE,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }

            Spacer(modifier = modifier.height(8.dp))

            Text(
                text = title.replace("\n", "\n\n"),
                style = MaterialTheme.typography.titleLarge,
                color = BLACK,
                textAlign = TextAlign.Justify
            )

            Spacer(modifier = modifier.height(8.dp))

            Text(
                text = body.replace("\n", "\n\n"),
                style = MaterialTheme.typography.bodyMedium,
                color = BLACK,
                textAlign = TextAlign.Justify
            )
        }
    }
}

@Preview
@Composable
private fun MeuCardPreview(){
    MeuCard(
        postId = 1,
        title = "Título do Post",
        body = "Este é o corpo do post de exemplo para visualização no preview.",
        onClick = {}
    )
}