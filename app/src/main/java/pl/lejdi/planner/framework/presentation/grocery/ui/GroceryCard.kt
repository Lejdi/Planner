package pl.lejdi.planner.framework.presentation.grocery.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import pl.lejdi.planner.framework.presentation.common.model.grocery.GroceryDisplayable

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GroceryCard(
    item: GroceryDisplayable,
    onEditClick: (GroceryDisplayable) -> Unit,
    onCompleteClick: (GroceryDisplayable) -> Unit,
) {
    var inEditMode by remember { mutableStateOf(false) }
    Card(
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        modifier = Modifier
            .height(200.dp)
            .combinedClickable(
                onLongClick = {
                    inEditMode = true
                },
                onClick = {}
            ),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(12.dp),
        ) {
            Text(
                text = item.name,
                style = LocalTextStyle.current.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .weight(1.0f)
            )
            Text(
                text = item.description ?: "",
                modifier = Modifier
                    .padding(top = 4.dp)
            )
            Button(
                onClick = {
                    if(inEditMode) onEditClick(item)
                    else onCompleteClick(item)
                },
            ) {
                Icon(Icons.Outlined.CheckCircle, null)
            }
        }
    }
}