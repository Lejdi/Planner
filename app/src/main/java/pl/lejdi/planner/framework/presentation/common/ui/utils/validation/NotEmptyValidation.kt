package pl.lejdi.planner.framework.presentation.common.ui.utils.validation

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
}