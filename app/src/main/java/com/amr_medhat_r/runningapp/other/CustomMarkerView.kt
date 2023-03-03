package com.amr_medhat_r.runningapp.other

import android.content.Context
import android.widget.TextView
import com.amr_medhat_r.runningapp.R
import com.amr_medhat_r.runningapp.db.Run
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import java.text.SimpleDateFormat
import java.util.*

class CustomMarkerView(
    context: Context,
    val run: List<Run>,
    layoutId: Int
) : MarkerView(context, layoutId) {

    private var tv_date: TextView? = null
    private var tv_duration: TextView? = null
    private var tv_avgSpeed: TextView? = null
    private var tv_distance: TextView? = null
    private var tv_caloriesBurned: TextView? = null

    init {
        tv_date = findViewById(R.id.tvDate)
        tv_duration = findViewById(R.id.tvDuration)
        tv_avgSpeed = findViewById(R.id.tvAvgSpeed)
        tv_distance = findViewById(R.id.tvDistance)
        tv_caloriesBurned = findViewById(R.id.tvCaloriesBurned)
    }

    override fun getOffset(): MPPointF {
        return MPPointF(-width / 2f, -height.toFloat())
    }

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        super.refreshContent(e, highlight)

        if (e == null) {
            return
        }
        val curRunId = e.x.toInt()
        val run = run[curRunId]

        val calender = Calendar.getInstance().apply {
            timeInMillis = run.timestamp
        }
        val dateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
        tv_date?.text = dateFormat.format(calender.time)

        val avgSpeed = "${run.avgSpeedInKMH}km/h"
        tv_avgSpeed?.text = avgSpeed

        val distanceInKm = "${run.distanceInMeters / 1000f}Km"
        tv_distance?.text = distanceInKm

        tv_duration?.text = TrackingUtility.getFormattedStopWatchTime(run.timeInMillis)

        val caloriesBurned = "${run.caloriesBurned}Kcal"
        tv_caloriesBurned?.text = caloriesBurned
    }
}