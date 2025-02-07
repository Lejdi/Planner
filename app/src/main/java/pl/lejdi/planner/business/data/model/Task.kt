package pl.lejdi.planner.business.data.model

import java.util.Date

data class Task(
    val id: Int,
    val name: String,
    val description: String?,
    val startDate: Date,
    val endDate: Date?,
    val hour: String?,
    val daysInterval: Int,
    val asap: Boolean,
)
