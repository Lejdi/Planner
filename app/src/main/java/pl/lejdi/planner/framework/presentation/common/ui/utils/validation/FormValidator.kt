package pl.lejdi.planner.framework.presentation.common.ui.utils.validation

class FormValidator {
    private val validations = mutableListOf<Validation>()

    fun addValidation(validation: Validation) : Validation {
        validations.add(validation)
        return validation
    }

    fun validate() : Boolean {
        return validations.all { it.validate() }
    }
}