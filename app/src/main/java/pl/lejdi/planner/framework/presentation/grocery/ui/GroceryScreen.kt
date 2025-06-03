package pl.lejdi.planner.framework.presentation.grocery.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.serialization.Serializable
import pl.lejdi.planner.framework.presentation.common.ui.BaseScreen
import pl.lejdi.planner.framework.presentation.grocery.GroceryViewModel

@Serializable
data object GroceryScreenRoute

@Composable
fun GroceryScreen(
    viewModel: GroceryViewModel = hiltViewModel(),
) = BaseScreen(
    displayProgressBar = viewModel.viewState.value.isLoading,
    errorsQueue = viewModel.errorsQueue
) {
    Scaffold{ contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colorScheme.background
                )
                .padding(top = contentPadding.calculateTopPadding())
        ) {
            Text("This is grocery screen")
        }
    }
}