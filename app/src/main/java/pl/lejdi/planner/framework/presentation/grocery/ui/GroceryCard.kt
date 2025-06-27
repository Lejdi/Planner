package pl.lejdi.planner.framework.presentation.grocery.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.Alignment
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
            .animateContentSize()
            .combinedClickable(
                onLongClick = {
                    inEditMode = true
                },
                onClick = {}
            ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = item.name,
                    style = LocalTextStyle.current.copy(
                        fontWeight = FontWeight.Bold
                    ),
                )
                Text(
                    text = item.description ?: "",
                    modifier = Modifier
                        .padding(top = 4.dp)
                )
            }
            Button(
                onClick = {
                    if (inEditMode) onEditClick(item)
                    else onCompleteClick(item)
                },
                contentPadding = PaddingValues(8.dp),
                shape = CircleShape,
                modifier = Modifier
                    .defaultMinSize(minWidth = 1.dp, minHeight = 1.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.CheckCircle,
                    contentDescription = null,
                )
            }
        }
    }
}