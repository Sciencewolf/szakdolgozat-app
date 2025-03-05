package com.sciencewolf.szakdolgozat.pages.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun LineChartView(entries: List<Entry>, label: String, color: Color) {
    AndroidView(
        factory = { context ->
            LineChart(context).apply {
                description.text = "$label Data"
                setNoDataText("No data available")
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                xAxis.granularity = 1f
                axisRight.isEnabled = false
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp),
        update = { chart ->
            val dataSet = LineDataSet(entries, label).apply {
                this.color = color.hashCode()
                valueTextColor = Color.White.hashCode()
                lineWidth = 2f
                circleRadius = 4f
                setDrawCircles(true)
                setDrawValues(false)
                enableDashedLine(10f, 5f, 0f)
            }

            chart.apply {
                xAxis.textSize = 10f
                axisLeft.textSize = 14f
                legend.textSize = 14f
                legend.textColor = Color.White.hashCode()
                description.textColor = Color.White.hashCode()
                axisLeft.textColor = Color.White.hashCode()
                xAxis.textColor = Color.White.hashCode()

                xAxis.valueFormatter = object : ValueFormatter() {
                    override fun getFormattedValue(value: Float): String {
                        return formatTimestamp(value.toLong())
                    }
                }
            }

            chart.data = LineData(dataSet)
            chart.invalidate()
        }
    )
}

fun formatTimestamp(timestamp: Long): String {
    return SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(timestamp * 1000))
}
