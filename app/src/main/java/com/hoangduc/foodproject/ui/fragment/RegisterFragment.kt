package com.hoangduc.foodproject.ui.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hoangduc.foodproject.BaseFragment
import com.hoangduc.foodproject.databinding.FragmentRegisterBinding
import com.hoangduc.foodproject.videomodel.LoginViewModel
import com.hoangduc.foodproject.videomodel.LoginViewModelFactory


class RegisterFragment : BaseFragment() {

    private val loginViewModel by lazy {
        ViewModelProvider(this, LoginViewModelFactory(repository))[LoginViewModel::class.java]
    }

    private lateinit var binding: FragmentRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = binding.txtUserNameRegister
        val password = binding.txtPasswordRegister
        val displayName = binding.txtFullNameRegister
        val register = binding.btnRegister

        loginViewModel.registeredResult.observe(viewLifecycleOwner, Observer{
            val registerResult = it ?: return@Observer
            if (registerResult.exception != null) {
                Toast.makeText(requireContext(), "Đăng ký thất bại", Toast.LENGTH_LONG).show()
            }
            if (registerResult.success != null) {

                Toast.makeText(requireContext(), "Đăng ký thành công", Toast.LENGTH_LONG).show()
            }
        })

        register.setOnClickListener {
            loginViewModel.signup(
                username.text.toString(), password.text.toString(), displayName?.text.toString()
            )
            username.text?.clear()
            password.text?.clear()
            displayName?.text?.clear()
        }

        username.afterTextChanged {
            loginViewModel.loginDataChanged(
                username.text.toString(), password.text.toString()
            )
        }


        password.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(), password.text.toString()
                )
            }

        }
    }


    fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
                afterTextChanged.invoke(editable.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

    }
}
