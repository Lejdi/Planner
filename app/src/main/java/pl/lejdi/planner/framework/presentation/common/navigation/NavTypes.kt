package pl.lejdi.planner.framework.presentation.common.navigation

import android.net.Uri
import android.os.Bundle
import androidx.navigation.NavType
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import pl.lejdi.planner.business.data.model.Task

object NavTypes {
    val TaskType = object : NavType<Task?>(
        isNullableAllowed = true
    ) {
        override fun get(bundle: Bundle, key: String): Task? {
            return parseValue(bundle.getString(key) ?: "")
        }

        override fun parseValue(value: String): Task? {
            if(value.isEmpty()) return null
            return Json.decodeFromString<Task>(Uri.decode(value))
        }

        override fun serializeAsValue(value: Task?): String {
            if (value == null) return ""
            return Uri.encode(Json.encodeToString(value))
        }

        override fun put(bundle: Bundle, key: String, value: Task?) {
            bundle.putString(key, serializeAsValue(value))
        }
    }
}