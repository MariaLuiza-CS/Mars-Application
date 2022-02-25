package com.example.marsappkotlin.presenter.fragment

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.marsappkotlin.R
import com.example.marsappkotlin.databinding.FragmentWelcomeBinding


class WelcomeFragment : Fragment() {

    private var _binding: FragmentWelcomeBinding? = null
    private val binding: FragmentWelcomeBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val animation =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_welcome, container, false)
        _binding = FragmentWelcomeBinding.bind(view)

        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    activity?.finish()
                }
            }

        initComponents(callback)
        return binding.root
    }

    private fun initComponents(callback: OnBackPressedCallback) {

        requireActivity().onBackPressedDispatcher.addCallback(this, callback)

        binding.buttonSignIn.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_loginFragment)
        }

        binding.buttonSignUp.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_registerFragment)
        }
    }

}