package com.nomargin.gptextapp.ui.view.fragments

import android.app.Activity
import android.content.Intent
import android.graphics.Color.blue
import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GoogleAuthProvider
import com.nomargin.gptextapp.R
import com.nomargin.gptextapp.databinding.FragmentSignInBinding
import com.nomargin.gptextapp.ui.model.FirebaseInstance
import com.nomargin.gptextapp.ui.view.activities.MainActivity
import com.nomargin.gptextapp.ui.viewmodel.SignInViewModel
import com.nomargin.gptextapp.util.constants.ApplicationConstants

class SignInFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentSignInBinding? = null
    private val binding: FragmentSignInBinding get() = _binding!!
    private lateinit var viewModel: SignInViewModel
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var inputEmail: EditText
    private lateinit var inputPassword: EditText
    private var googleSignInLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleData(task)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(SignInViewModel::class.java)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        inputEmail = binding.inputEmail
        inputPassword = binding.inputPassword
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        observers()

        initClicks()

        binding.applicationCredits.setLinkTextColor(requireContext().getColor(R.color.low_white))
        binding.applicationCredits.movementMethod = LinkMovementMethod.getInstance()
    }

    override fun onClick(view: View) {
        when (view.id) {
            binding.buttonGoogleSignin.id -> {
                signInGoogle()
            }
            binding.buttonSignupAccount.id -> {
                findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToSignUpFragment())
            }
            binding.buttonEmailSignin.id -> {
                when {
                    inputEmail.text.isEmpty() -> {
                        makeToast(getString(R.string.insert_your_email))
                    }
                    inputPassword.text.isEmpty() -> {
                        makeToast(getString(R.string.insert_your_password))
                    }
                    else -> {
                        viewModel.emailSignIn(requireContext(), inputEmail.text.toString(), inputPassword.text.toString())
                        binding.progressBar.isVisible = true
                        inputHandler(false)
                    }
                }
            }
        }
    }

    private fun initClicks(){
        binding.buttonSignupAccount.setOnClickListener(this)
        binding.buttonGoogleSignin.setOnClickListener(this)
        binding.buttonEmailSignin.setOnClickListener(this)
    }

    private fun signInGoogle() {
        val googleSignInIntent = googleSignInClient.signInIntent
        googleSignInLauncher.launch(googleSignInIntent)
    }

    private fun handleData(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {
            val account = task.result
            if (account != null) {
                val googleSignInCredential = GoogleAuthProvider.getCredential(account.idToken, null)
                viewModel.googleSignInCredential(googleSignInCredential)
            }
        } else {
            makeToast(task.exception?.message.toString())
        }
    }

    private fun observers() {
        viewModel.googleSignInStatus.observe(viewLifecycleOwner) { googleSignInStatus ->
            if (googleSignInStatus.status) {
                startMainActivity()
            } else {
                makeToast(googleSignInStatus.message)
            }
        }
        viewModel.emailSignInStatus.observe(viewLifecycleOwner){emailSignInResult ->
            binding.progressBar.isVisible = false
            inputHandler(true)
            if(emailSignInResult.status){
                startMainActivity()
            }else{
                makeToast(emailSignInResult.message)
            }
        }
    }

    private fun inputHandler(value: Boolean) {
        inputEmail.isEnabled = value
        inputPassword.isEnabled = value
    }

    private fun makeToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun startMainActivity(){
        val intent = Intent(requireContext(), MainActivity::class.java)
        intent.putExtra(
            ApplicationConstants.Companion.UserData.USER_NAME,
            FirebaseInstance.getFirebaseAuthentication().currentUser?.displayName
        )
        intent.putExtra(
            ApplicationConstants.Companion.UserData.USER_EMAIL,
            FirebaseInstance.getFirebaseAuthentication().currentUser?.email
        )
        startActivity(intent)
        requireActivity().finish()
    }

}