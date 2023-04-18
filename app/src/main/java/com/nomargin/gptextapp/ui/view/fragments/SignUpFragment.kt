package com.nomargin.gptextapp.ui.view.fragments

import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.nomargin.gptextapp.R
import com.nomargin.gptextapp.databinding.FragmentSignUpBinding
import com.nomargin.gptextapp.ui.viewmodel.SignUpViewModel

class SignUpFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentSignUpBinding? = null
    private val binding: FragmentSignUpBinding get() = _binding!!
    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        observers()

        binding.applicationCredits.setLinkTextColor(requireContext().getColor(R.color.low_white))
        binding.applicationCredits.movementMethod = LinkMovementMethod.getInstance()

        initClicks()
    }

    override fun onClick(view: View) {
        when(view.id){
            binding.buttonBack.id -> {
                findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToSignInFragment())
            }
            binding.buttonEmailSignup.id -> {
                when{
                    binding.inputEmail.text.isEmpty() -> {
                        makeToast(getString(R.string.insert_your_email))
                    }
                    binding.inputPassword.text.isEmpty() -> {
                        makeToast(getString(R.string.insert_your_password))
                    }
                    binding.inputConfirmPassword.text.isEmpty() -> {
                        makeToast(getString(R.string.confirm_your_password))
                    }
                    binding.inputPassword.text.toString() != binding.inputConfirmPassword.text.toString() -> {
                        makeToast(getString(R.string.verify_your_password))
                    }
                    else -> {
                        viewModel.createUser(requireContext(), binding.inputEmail.text.toString(), binding.inputPassword.text.toString())
                        binding.progressBar.isVisible = true
                        inputHandler(false)
                    }
                }
            }
        }
    }

    private fun observers(){
        viewModel.createUserStatus.observe(viewLifecycleOwner){createUserResult ->
            binding.progressBar.isVisible = false
            inputHandler(true)
            if(createUserResult.status){
                makeToast(createUserResult.message)
                binding.inputEmail.text.clear()
                binding.inputPassword.text.clear()
                binding.inputConfirmPassword.text.clear()
            }else{
                makeToast(createUserResult.message)
            }
        }
    }

    private fun inputHandler(value: Boolean){
        binding.inputEmail.isEnabled = value
        binding.inputPassword.isEnabled = value
        binding.inputConfirmPassword.isEnabled = value
    }

    private fun initClicks(){
        binding.buttonBack.setOnClickListener(this)
        binding.buttonEmailSignup.setOnClickListener(this)
    }

    private fun makeToast(message: String){
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}