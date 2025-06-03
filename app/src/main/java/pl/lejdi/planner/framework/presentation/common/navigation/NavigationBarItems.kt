package pl.lejdi.planner.framework.presentation.common.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

enum class NavigationBarItems(
    val image: ImageVector,
) {
    TASKS(Icons.AutoMirrored.Filled.List),
    GROCERIES(Icons.Default.ShoppingCart),
}