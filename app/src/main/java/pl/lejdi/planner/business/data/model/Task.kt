package pl.lejdi.planner.business.data.model

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import java.util.Date

@Parcelize
@Serializable
data class Task(
    val id: Int,
    val name: String,
    val description: String?,
    @Serializable(with = DateSerializer::class)
    val startDate: Date,
    @Serializable(with = DateSerializer::class)
    val endDate: Date?,
    val hour: String?,
    val daysInterval: Int,
    val asap: Boolean,
) : Parcelable


@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = Date::class)
class DateSerializer : KSerializer<Date> {
    override fun deserialize(decoder: Decoder): Date {
        return Date(decoder.decodeLong())
    }

    override fun serialize(encoder: Encoder, value: Date) {
        encoder.encodeLong(value.time)
    }
}


val TaskType = object : NavType<Task>(
    isNullableAllowed = true
) {
    override fun get(bundle: Bundle, key: String): Task? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, Task::class.java)
        } else {
            @Suppress("DEPRECATION")
            bundle.getParcelable(key)
        }
    }

    override fun parseValue(value: String): Task {
        return Json.decodeFromString<Task>(value)
    }

    override fun serializeAsValue(value: Task): String {
        return Json.encodeToString(value)
    }

    override fun put(bundle: Bundle, key: String, value: Task) {
        bundle.putParcelable(key, value)
    }
}