package com.amr_medhat_r.runningapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.amr_medhat_r.runningapp.databinding.ItemRunBinding
import com.amr_medhat_r.runningapp.db.Run
import com.amr_medhat_r.runningapp.other.TrackingUtility
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*

class RunAdapter : RecyclerView.Adapter<RunAdapter.RunViewHolder>() {

    inner class RunViewHolder(var itemRunBinding: ItemRunBinding) :
        RecyclerView.ViewHolder(itemRunBinding.root)

    val diffCallback = object : DiffUtil.ItemCallback<Run>() {
        override fun areItemsTheSame(oldItem: Run, newItem: Run): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Run, newItem: Run): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<Run>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RunViewHolder {
        val binding =
            ItemRunBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RunViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RunViewHolder, position: Int) {
        // val run = differ.currentList[position]
        with(holder) {
            differ.currentList[position].let { run ->
                Glide.with(itemRunBinding.root).load(run.img).into(itemRunBinding.ivRunImage)
                val calender = Calendar.getInstance().apply {
                    timeInMillis = run.timestamp
                }
                val dateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
                itemRunBinding.tvDate.text = dateFormat.format(calender.time)

                val avgSpeed = "${run.avgSpeedInKMH}km/h"
                itemRunBinding.tvAvgSpeed.text = avgSpeed

                val distanceInKm = "${run.distanceInMeters / 1000f}Km"
                itemRunBinding.tvDistance.text = distanceInKm

                itemRunBinding.tvTime.text =
                    TrackingUtility.getFormattedStopWatchTime(run.timeInMillis)

                val caloriesBurned = "${run.caloriesBurned}Kcal"
                itemRunBinding.tvCalories.text = caloriesBurned
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}