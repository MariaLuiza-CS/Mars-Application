package com.example.marsappkotlin.presenter.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.marsappkotlin.R
import com.example.marsappkotlin.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        _binding = FragmentLoginBinding.bind(view)

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.action_loginFragment_to_welcomeFragment)
                }
            }
        initComponents(callback)
        return binding.root

    }

    private fun initComponents(callback: OnBackPressedCallback) {
        requireActivity().onBackPressedDispatcher.addCallback(this, callback);
    }


}