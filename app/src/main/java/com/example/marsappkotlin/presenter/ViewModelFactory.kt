package com.example.marsappkotlin.presenter

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.marsappkotlin.domain.LocalRepositoty
import com.example.marsappkotlin.domain.RemoteRepository
import com.example.marsappkotlin.presenter.fragment.RegisterFragmentViewModel

class ViewModelFactory(
    private val localRepositoty: LocalRepositoty,
    private val remoteRepositoty: RemoteRepository,
    private val application: Application,
    private val context: Context
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(RegisterFragmentViewModel::class.java)) {
            return RegisterFragmentViewModel(localRepositoty, remoteRepositoty, application,context) as T
        }

        throw IllegalArgumentException("Unknown class name")

    }
}