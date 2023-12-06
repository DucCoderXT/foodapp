package com.hoangduc.foodproject.ui.fragment

import android.content.Intent
import android.graphics.Color
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
import com.hoangduc.foodproject.activities.HomeActivity
import com.hoangduc.foodproject.databinding.FragmentLoginBinding
import com.hoangduc.foodproject.videomodel.LoginViewModel
import com.hoangduc.foodproject.videomodel.LoginViewModelFactory


class LoginFragment : BaseFragment() {
    private lateinit var binding:FragmentLoginBinding
    private val loginViewModel by lazy {
        ViewModelProvider(this, LoginViewModelFactory(repository))[LoginViewModel::class.java]
    }
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
       binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = binding.username
        val password = binding.password
        val login = binding.btnLogin

        loginViewModel.loginFormState.observe(viewLifecycleOwner, Observer {

            val loginState = it ?: return@Observer

            login.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                username.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                password.error = getString(loginState.passwordError)
            }

        })

        loginViewModel.loginResult.observe(viewLifecycleOwner, Observer {
            val loginResult = it ?: return@Observer
            if (loginResult.exception != null) {
//                 Xử lý khi đăng nhập thất bại
                Toast.makeText(requireContext(), "Sai tên đăng nhập hoặc mật khẩu", Toast.LENGTH_LONG).show()
            }
            if (loginResult.success != null) {
                // Xử lý khi đăng nhập thành công
                val intent = Intent(requireActivity(), HomeActivity::class.java)
                intent.putExtra(HomeActivity.EXTRA_KEY_DISPLAY_NAME,loginResult.success.displayName)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        })

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

        login.setOnClickListener {

            binding.btnLogin.setTextColor(Color.WHITE)
            loginViewModel.login(username.text.toString(), password.text.toString())
            username.text?.clear()
            password.text?.clear()

            // ẩn bàn phím ảo
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