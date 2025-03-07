package pl.lejdi.planner.framework.presentation.common.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import pl.lejdi.planner.framework.presentation.common.ui.utils.validation.Validation

@Composable
fun FormTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    singleLine: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    validation: Validation? = null
) {
    var isError by remember { mutableStateOf(!true) }
    validation?.fieldToValidate(value)
    validation?.onValidationChange { isValid ->
        isError = !isValid
    }

    OutlinedTextField(
        value = value,
        onValueChange = {
            isError = false
            onValueChange.invoke(it)
        },
        label = { Text(label) },
        maxLines = maxLines,
        minLines = minLines,
        keyboardOptions = keyboardOptions,
        isError = isError,
        singleLine = singleLine,
        modifier = Modifier
            .fillMaxWidth()
    )

}