package com.loperilla.compracasa.register.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.loperilla.compracasa.data.model.DataRegistration
import com.loperilla.compracasa.databinding.FragmentRegisterBinding
import com.loperilla.compracasa.register.model.RegisterResult
import com.loperilla.compracasa.register.viewModel.RegisterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RegisterViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val emailEditText = binding.etRegisterEmail
        val passwordEditText = binding.etRegisterPassword
        val displayNameEditText = binding.etRegisterDisplayName
        val registerButton = binding.btnRegister
        val loadingProgressBar = binding.loading

        viewModel.registerFormState.observe(viewLifecycleOwner,
            Observer { registerFormState ->
                if (registerFormState == null) {
                    return@Observer
                }
                registerButton.isEnabled = registerFormState.isDataValid
                registerFormState.usernameError?.let {
                    emailEditText.error = getString(it)
                }
                registerFormState.passwordError?.let {
                    passwordEditText.error = getString(it)
                }
                registerFormState.displayNameError?.let {
                    displayNameEditText.error = getString(it)
                }
            })

        viewModel.registerResult.observe(viewLifecycleOwner) { registerResult ->
            registerResult ?: return@observe
            loadingProgressBar.visibility = View.GONE
            if (registerResult == RegisterResult.SUCCESSFULLY) {
                findNavController().navigate(
                    RegisterFragmentDirections.actionRegisterFragmentToHomeFragment()
                )
                return@observe
            }
            Toast.makeText(binding.root.context, "Algo falló", Toast.LENGTH_LONG).show()
        }


        val afterTextChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // ignore
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // ignore
            }

            override fun afterTextChanged(s: Editable) {
                viewModel.registerFromDataChanged(
                    emailEditText.text.toString(),
                    passwordEditText.text.toString(),
                    displayNameEditText.text.toString()
                )
            }
        }
        emailEditText.addTextChangedListener(afterTextChangedListener)
        passwordEditText.addTextChangedListener(afterTextChangedListener)
        displayNameEditText.addTextChangedListener(afterTextChangedListener)
        displayNameEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.doRegister(
                    DataRegistration(
                        emailEditText.text.toString(),
                        passwordEditText.text.toString(),
                        displayNameEditText.text.toString()
                    )
                )
            }
            false
        }

        registerButton.setOnClickListener {
            loadingProgressBar.visibility = View.VISIBLE
            viewModel.doRegister(
                DataRegistration(
                    emailEditText.text.toString(),
                    passwordEditText.text.toString(),
                    displayNameEditText.text.toString()
                )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}