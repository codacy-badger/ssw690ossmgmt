package com.ccorrads.ossp.loginregistration.views

import android.content.Context
import android.util.AttributeSet

class ValidatablePw : ValidatableText {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun textIsValid(): Boolean {
        return ValidatorUtil.isValidPassword(text?.toString())
    }
}
