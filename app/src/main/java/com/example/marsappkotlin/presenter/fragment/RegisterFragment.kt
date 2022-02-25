package com.example.marsappkotlin.presenter.fragment

import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.marsappkotlin.R
import com.example.marsappkotlin.data.local.database.MarsDatabaseBuild
import com.example.marsappkotlin.data.local.model.User
import com.example.marsappkotlin.data.repository.LocalRepositoryImpl
import com.example.marsappkotlin.data.repository.RemoteRepositoryImpl
import com.example.marsappkotlin.databinding.FragmentRegisterBinding
import com.example.marsappkotlin.presenter.ViewModelFactory

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding: FragmentRegisterBinding get() = _binding!!
    private lateinit var viewModel: RegisterFragmentViewModel
    private lateinit var callback: OnBackPressedCallback
    private var userFirebase: User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        _binding = FragmentRegisterBinding.bind(view)
        callback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    findNavController().navigate(R.id.action_registerFragment_to_welcomeFragment)
                }
            }

        initViewModel()
        initComponents(callback)
        initObservers()

        return binding.root
    }

    private fun initObservers() {
        viewModel.getUserEditTextNameLiveData().observe(viewLifecycleOwner, {
            binding.textInputName.helperText = it
        })

        viewModel.getUserEditTextEmailLiveData().observe(viewLifecycleOwner, {
            binding.textInputEmail.helperText = it
        })

        viewModel.getUserEditTextPasswordLiveData().observe(viewLifecycleOwner, {
            binding.textInputPassword.helperText = it
        })

        viewModel.getUserEditTextConfirmationPasswordLiveData().observe(viewLifecycleOwner, {
            binding.textInputConfirmationPassword.helperText = it
        })

        viewModel.getFirebaseUserLiveData().observe(viewLifecycleOwner, {
            if (it?.id != null) {
                userFirebase = it
            }
        })
    }

    private fun initViewModel() {
        val localRepository = LocalRepositoryImpl(MarsDatabaseBuild.getInstance(requireContext()))
        val remoteRepository = RemoteRepositoryImpl()
        val viewModelProvider =
            ViewModelFactory(localRepository, remoteRepository, Application(), requireContext())
        viewModel = ViewModelProvider(
            requireActivity(),
            viewModelProvider
        )[RegisterFragmentViewModel::class.java]
    }

    private fun initComponents(callback: OnBackPressedCallback) {
        requireActivity().onBackPressedDispatcher.addCallback(this, callback);

        binding.textInputName.editText?.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                viewModel.checkEditTextName(binding.textInputName.editText!!)
            }
        }

        binding.textInputEmail.editText?.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                viewModel.checkEditTextEmail(binding.textInputEmail.editText!!)
            }
        }

        binding.textInputPassword.editText?.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                viewModel.checkEditTextPassword(binding.textInputPassword.editText!!)
            }
        }

        binding.textInputConfirmationPassword.editText?.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                viewModel.checkEditTextConfirmationPassword(
                    binding.textInputPassword.editText!!,
                    binding.textInputConfirmationPassword.editText!!
                )
            }
        }

        binding.buttonSignIn.setOnClickListener {

            if (checkInputs()) {
                viewModel.createUserFirebase(
                    binding.textInputName.editText?.text.toString(),
                    binding.textInputEmail.editText?.text.toString(),
                    binding.textInputPassword.editText?.text.toString()
                )
                if (userFirebase != null) {
                    viewModel.addLocalUser(
                        userFirebase!!.id!!,
                        userFirebase!!.name!!,
                        userFirebase!!.email!!,
                        userFirebase!!.password!!
                    )
                }
            }
        }
    }

    private fun checkInputs(): Boolean {
        if (binding.textInputName.helperText == null &&
            binding.textInputEmail.helperText == null &&
            binding.textInputPassword.helperText == null &&
            binding.textInputConfirmationPassword.helperText == null
        ) {
            return true
        } else {
            viewModel.checkEditTextName(binding.textInputName.editText!!)
            viewModel.checkEditTextEmail(binding.textInputEmail.editText!!)
            viewModel.checkEditTextPassword(binding.textInputPassword.editText!!)
            viewModel.checkEditTextConfirmationPassword(
                binding.textInputPassword.editText!!,
                binding.textInputConfirmationPassword.editText!!
            )
            return false
        }
    }

}