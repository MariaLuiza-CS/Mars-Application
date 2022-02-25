package com.example.marsappkotlin.presenter.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.example.marsappkotlin.R
import com.example.marsappkotlin.databinding.FragmentSplashBinding


class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding: FragmentSplashBinding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_splash, container, false)

        _binding = FragmentSplashBinding.bind(view)

        Handler(Looper.myLooper()!!).postDelayed({
            val extras =
                FragmentNavigatorExtras(binding.imageViewIcMars to getString(R.string.transition_image_view_ic_mars_big))
            findNavController().navigate(
                R.id.action_splashFragment_to_welcomeFragment,
                null,
                null,
                extras
            )
        }, 5000)

        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}