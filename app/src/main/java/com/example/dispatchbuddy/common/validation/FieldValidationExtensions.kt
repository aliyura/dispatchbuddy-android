package com.example.dispatchbuddy.common.validation

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.LifecycleOwner
import com.example.dispatchbuddy.R
import com.example.dispatchbuddy.common.validation.FieldValidationTracker.fieldTypeMap
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout

inline fun TextInputLayout.validateField(
    errorMessage: String,
    fieldType: FieldValidationTracker.FieldType,
    crossinline action: (String) -> Boolean
) {
    this.editText?.doAfterTextChanged {
        if (!action.invoke(it.toString().trim())) {
            this.error = errorMessage
            fieldTypeMap[fieldType] = false
            FieldValidationTracker.isFieldsValidated.value = fieldTypeMap
        } else {
            this.error = null
            this.isErrorEnabled = false
            fieldTypeMap[fieldType] = true
            FieldValidationTracker.isFieldsValidated.value = fieldTypeMap
        }
    }
}

fun TextInputLayout.validateConfirmPassword(
    passwordInputLayout: TextInputLayout,
    fieldType: FieldValidationTracker.FieldType,
    errorMessage: String,
) {
    this.editText?.doAfterTextChanged {
        if (!FieldValidations.validateConfirmPassword(
                it.toString().trim(),
                passwordInputLayout.editText?.text.toString().trim()
            )
        ) {
            this.error = errorMessage
            fieldTypeMap[fieldType] = false
            FieldValidationTracker.isFieldsValidated.value = fieldTypeMap
        } else {
            this.error = null
            fieldTypeMap[fieldType] = true
            FieldValidationTracker.isFieldsValidated.value = fieldTypeMap
        }
    }
}

fun MaterialButton.observeFieldsValidationToEnableButton(
    context: Context,
    lifecycleOwner: LifecycleOwner,
    fieldValidationTracker: FieldValidationTracker = FieldValidationTracker
) {

    fieldValidationTracker.isFieldsValidated.observe(lifecycleOwner) {
        this.apply {
            this.isEnabled = !it.values.contains(false)
            backgroundTintList = if (!it.values.contains(false))
                ContextCompat.getColorStateList(context, R.color.secondary_color) else
                ContextCompat.getColorStateList(context, R.color.grey)
        }
    }
}
