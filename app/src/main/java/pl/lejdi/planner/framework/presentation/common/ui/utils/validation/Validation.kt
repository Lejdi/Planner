package pl.lejdi.planner.framework.presentation.common.ui.utils.validation

abstract class Validation {
    private var isValid: Boolean = true
    private lateinit var onValidationChangeListener: (Boolean) -> Unit
    protected var fieldToValidate : Any? = null

    abstract fun fieldToValidate(fieldValue: Any)
    abstract fun validation() : Boolean

    fun validate() : Boolean {
        isValid = validation()
        onValidationChangeListener.invoke(isValid)
        return isValid
    }

    fun onValidationChange(onChange: (Boolean) -> Unit) {
        onValidationChangeListener = onChange
    }
}