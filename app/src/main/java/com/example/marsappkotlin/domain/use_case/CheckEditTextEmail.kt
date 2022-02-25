package com.example.marsappkotlin.domain.use_case

import android.util.Patterns
import android.widget.EditText
import androidx.lifecycle.MutableLiveData

class CheckEditTextEmail {

    private var userEditTextEmail: MutableLiveData<CharSequence?> = MutableLiveData<CharSequence?>()

    operator fun invoke(editTextEmail: EditText) {
        if (editTextEmail.text == null) {
            userEditTextEmail.postValue("You need to put something")
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(editTextEmail.text.toString()).matches()) {
            userEditTextEmail.postValue("Invalid email Address")
        }
    }

    fun getUserEditTextEmail(): MutableLiveData<CharSequence?> {
        return userEditTextEmail
    }
}