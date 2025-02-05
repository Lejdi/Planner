package pl.lejdi.planner.framework.datasource.cache.model.task

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "start_date") val startDate: String,
    @ColumnInfo(name = "end_date") val endDate: String?,
    @ColumnInfo(name = "hour") val hour: String?,
    @ColumnInfo(name = "days_interval") val daysInterval: Int,
    @ColumnInfo(name = "asap") val asap: Boolean,
)