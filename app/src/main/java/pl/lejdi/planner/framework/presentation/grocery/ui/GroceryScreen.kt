package pl.lejdi.planner.framework.presentation.grocery.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.serialization.Serializable
import pl.lejdi.planner.framework.presentation.common.ui.BaseScreen
import pl.lejdi.planner.framework.presentation.grocery.GroceryContract
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
                items(viewModel.viewState.value.groceriesList){
                    GroceryCard(
                        item = it,
                        onEditClick = { groceryItem ->
                            viewModel.sendEvent(GroceryContract.Event.GroceryEdited(groceryItem))
                        },
                        onCompleteClick = { groceryItem ->
                            viewModel.sendEvent(GroceryContract.Event.GroceryCompleted(groceryItem))
                        }
                    )
                }
                item{
                    AddGroceryView(
                        expanded = viewModel.viewState.value.newTaskExpanded,
                        onFabClicked = {
                            viewModel.sendEvent(
                                GroceryContract.Event.AddButtonClicked
                            )
                        },
                        onSaveClicked = { name, description ->
                            viewModel.sendEvent(
                                GroceryContract.Event.SaveNewTaskClicked(
                                    name = name,
                                    description = description
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}