package com.example.marsappkotlin.presenter.fragment

import android.app.Application
import android.content.Context
import android.util.Patterns
import android.widget.EditText
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marsappkotlin.R
import com.example.marsappkotlin.data.local.model.User
import com.example.marsappkotlin.data.remoto.firebase.AuthenticationDataSource
import com.example.marsappkotlin.domain.LocalRepositoty
import com.example.marsappkotlin.domain.RemoteRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class RegisterFragmentViewModel(
    private val localRepositoty: LocalRepositoty,
    private val remoteRepositoty: RemoteRepository,
    private val application: Application,
    private val context: Context
) :
    ViewModel() {

    private var userEditTextEmail: MutableLiveData<CharSequence?> = MutableLiveData<CharSequence?>()
    private var userEditTextName: MutableLiveData<CharSequence?> = MutableLiveData<CharSequence?>()
    private var userEditTextPassword: MutableLiveData<CharSequence?> =
        MutableLiveData<CharSequence?>()
    private var userEditTextConfirmationPassword: MutableLiveData<CharSequence?> =
        MutableLiveData<CharSequence?>()
    private var userMutableLiveData: MutableLiveData<User?> = MutableLiveData<User?>()
    private var isUserEmailVerifiedLiveData: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    private var firebaseUserLiveData: MutableLiveData<User?> = MutableLiveData<User?>()
    private val authenticationDataSource = AuthenticationDataSource(context)

    init {
        isUserEmailVerifiedLiveData = authenticationDataSource.getIsUserEmailVerified()
        userMutableLiveData = authenticationDataSource.getUserMutableLiveData()
    }

    fun createUserFirebase(name: String, email: String, password: String) {
        viewModelScope.launch {
            authenticationDataSource.createUserWithEmailAndPassword(name, email, password)
        }
    }

    fun addLocalUser(id: Int, name: String, email: String, password: String) {
        viewModelScope.launch {
            localRepositoty.insertUser(User(id, name, email, password))
        }
    }

    fun checkEditTextName(editTextName: EditText) {
        viewModelScope.launch {
            if (editTextName.text.isEmpty()) {
                userEditTextName.postValue(context.getString(R.string.invalid_name))
            } else {
                userEditTextName.postValue(null)
            }
        }

    }

    fun checkEditTextEmail(editTextEmail: EditText) {
        viewModelScope.launch {
            if (editTextEmail.text.isEmpty()) {
                userEditTextEmail.postValue(context.getString(R.string.invalid_name))
            } else if (!Patterns.EMAIL_ADDRESS.matcher(editTextEmail.text.toString()).matches()) {
                userEditTextEmail.postValue(context.getString(R.string.invalid_email))
            } else {
                userEditTextEmail.postValue(null)
            }
        }
    }

    fun checkEditTextPassword(editTextPassword: EditText) {
        viewModelScope.launch {
            if (editTextPassword.text.length < 8) {
                userEditTextPassword.postValue(context.getString(R.string.invalid_password_length))
            } else if (!editTextPassword.text.matches(".*[A-Z].*".toRegex())) {
                userEditTextPassword.postValue(context.getString(R.string.invalid_password_upper_case))
            } else if (!editTextPassword.text.matches(".*[a-z].*".toRegex())) {
                userEditTextPassword.postValue(context.getString(R.string.invalid_password_lower_case))
            } else if (!editTextPassword.text.matches(".*[@#\$%^&+=*].*".toRegex())) {
                userEditTextPassword.postValue(context.getString(R.string.invalid_password_special_character))
            } else {
                userEditTextPassword.postValue(null)
            }
        }
    }

    fun checkEditTextConfirmationPassword(
        editTextPassword: EditText,
        editTextConfirmationPassword: EditText
    ) {
        viewModelScope.launch {
            if (editTextConfirmationPassword.text.toString() != editTextPassword.text.toString()) {
                userEditTextConfirmationPassword.postValue(context.getString(R.string.invalid_confirm_password))
            } else {
                userEditTextConfirmationPassword.postValue(null)
            }
        }
    }

    fun getUserEditTextNameLiveData(): MutableLiveData<CharSequence?> {
        return userEditTextName
    }

    fun getUserEditTextEmailLiveData(): MutableLiveData<CharSequence?> {
        return userEditTextEmail
    }

    fun getUserEditTextPasswordLiveData(): MutableLiveData<CharSequence?> {
        return userEditTextPassword
    }

    fun getUserEditTextConfirmationPasswordLiveData(): MutableLiveData<CharSequence?> {
        return userEditTextConfirmationPassword
    }

    fun getFirebaseUserLiveData(): MutableLiveData<User?> {
        return userMutableLiveData
    }

}