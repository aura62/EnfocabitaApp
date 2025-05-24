package com.aura.enfocabita.presentation.stats

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.tehras.charts.bar.BarChart
import com.github.tehras.charts.bar.BarChartData
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import com.github.tehras.charts.bar.renderer.bar.SimpleBarDrawer
import com.github.tehras.charts.bar.renderer.label.SimpleValueDrawer
import com.github.tehras.charts.bar.renderer.xaxis.SimpleXAxisDrawer
import com.github.tehras.charts.piechart.animation.simpleChartAnimation

@Composable
fun BarChartProgresoSemanal(data: Map<String, Int>) {
    val barColor = MaterialTheme.colorScheme.primary
    val labelColor = MaterialTheme.colorScheme.inverseSurface

    val barData = BarChartData(
        bars = data.entries.map {
            BarChartData.Bar(
                label = it.key,
                value = it.value.toFloat(),
                color = barColor
            )
        }
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(top = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BarChart(
            barChartData = barData,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp),
            animation = simpleChartAnimation(),
            barDrawer = SimpleBarDrawer(),
            xAxisDrawer = SimpleXAxisDrawer(),
            labelDrawer = SimpleValueDrawer(
                drawLocation = SimpleValueDrawer.DrawLocation.Inside,
                labelTextColor = labelColor
            )
        )
    }
}