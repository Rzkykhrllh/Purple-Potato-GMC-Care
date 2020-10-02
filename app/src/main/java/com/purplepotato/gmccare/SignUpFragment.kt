package com.purplepotato.gmccare

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_sign_up.*

class SignUpFragment : Fragment(), View.OnClickListener {

    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fbAuthentication = FirebaseAuthentication()
        val viewModelFactory = SignUpViewModelFactory(fbAuthentication)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(SignUpViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSignUp.setOnClickListener(this)
        btnBackToLoginFragment.setOnClickListener(this)
        tvBackToLoginFragment.setOnClickListener(this)
        getSignUpState()
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnSignUp -> signUp()
            R.id.btnBackToLoginFragment -> requireActivity().onBackPressed()
            R.id.tvBackToLoginFragment -> requireActivity().onBackPressed()
        }
    }

    private fun signUp() {
        val name = et_name.text.toString().trim()
        val birthDate = et_birth_date.text.toString().trim()
        val nik = et_nik.text.toString().trim()
        val email = et_email.text.toString().trim()
        val password = et_password.text.toString().trim()

        if (name.isEmpty()) {
            et_name.error = "Field ini tidak boleh kosong"
            return
        }

        if (birthDate.isEmpty()) {
            et_birth_date.error = "Field ini tidak boleh kosong"
            return
        }

        if (nik.isEmpty()) {
            et_nik.error = "Field ini tidak boleh kosong"
            return
        }

        if (email.isEmpty()) {
            et_email.error = "Field ini tidak boleh kosong"
            return
        }

        if (password.isEmpty()) {
            et_email.error = "Field ini tidak boleh kosong"
            return
        }

        if (password.length < 8) {
            et_password.error = "Password kurang dari 8 karakter"
            return
        }

        viewModel.createUserWithEmailAndPassword(email, password)

    }

    private fun getSignUpState() {
        viewModel.getSignUpState().observe(viewLifecycleOwner, { response ->
            when (response) {
                is State.OnSuccess -> {
                    showLoading(false)
                    findNavController().navigate(R.id.toVerificationFragment)
                }
                is State.OnLoading -> {
                    showLoading(true)
                }
                is State.OnError -> {
                    showLoading(false)
                    response.message?.let {
                        Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            signUpLoadingFrame.visibility = View.VISIBLE
            signUpProgressBar.visibility = View.VISIBLE
        } else {
            signUpProgressBar.visibility = View.GONE
            signUpLoadingFrame.visibility = View.GONE
        }

    }
}