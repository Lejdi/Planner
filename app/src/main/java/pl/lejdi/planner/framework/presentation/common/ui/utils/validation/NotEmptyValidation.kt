package pl.lejdi.planner.framework.presentation.common.ui.utils.validation

import pl.lejdi.planner.R

object NotEmptyValidation : Validation() {

    override fun validation(): Boolean {
        return if(fieldToValidate is String){
            (fieldToValidate as String).isNotEmpty()
        }
        else{
            true
        }
    }

    override fun fieldToValidate(fieldValue: Any) {
        fieldToValidate = fieldValue
    }

    override fun getErrorMessage() = R.string.validation_non_empty_error_message
}