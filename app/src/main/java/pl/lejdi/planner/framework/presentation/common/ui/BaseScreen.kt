package pl.lejdi.planner.framework.presentation.common.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import pl.lejdi.planner.R
import pl.lejdi.planner.framework.presentation.common.ui.utils.clickableWithoutRipple
import pl.lejdi.planner.framework.presentation.util.ErrorsQueue

@Composable
fun BaseScreen(
    displayProgressBar: Boolean,
    errorsQueue: ErrorsQueue,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        content()
        if(displayProgressBar){
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Gray.copy(alpha = 0.3f))
                    .clickableWithoutRipple{}
            ) {
                CircularProgressIndicator()
            }
        }
        errorsQueue.getError()?.let { error ->
            AlertDialog(
                title = {
                    Text(text = stringResource(R.string.error_dialog_title))
                },
                text = {
                    Text(text = stringResource(error.details))
                },
                onDismissRequest = {
                    //do nothing
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            errorsQueue.removeError()
                        }
                    ) {
                        Text(stringResource(R.string.ok_button))
                    }
                }
            )
        }
    }
}