package pl.lejdi.planner.business.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class GroceryItem(
    val id: Int,
    val name: String,
    val description: String?,
) : Parcelable
