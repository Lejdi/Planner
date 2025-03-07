package pl.lejdi.planner.framework.presentation.edittask.util

import pl.lejdi.planner.R

enum class EditTaskRadioButton(val description: Int) {
    ASAP(R.string.edit_task_radio_button_asap),
    SPECIFIC_DAY(R.string.edit_task_radio_button_specific_day),
    PERIODIC(R.string.edit_task_radio_button_periodic),
}