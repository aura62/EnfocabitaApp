package com.aura.enfocabita.presentation.inicio

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StreakCard(racha: Int, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text("🔥 Racha actual", style = MaterialTheme.typography.titleMedium)
                Text("$racha día(s)", style = MaterialTheme.typography.headlineSmall)
            }

            if (racha >= 7) {
                Text("¡Excelente!", color = MaterialTheme.colorScheme.primary)
            } else if (racha > 0) {
                Text("¡Sigue así!", color = MaterialTheme.colorScheme.secondary)
            } else {
                Text("Empieza hoy 💪", color = MaterialTheme.colorScheme.error)
            }
        }
    }
}
