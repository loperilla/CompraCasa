package com.loperilla.compracasa.shoppinglist.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.loperilla.compracasa.data.model.ShoppingListItem
import com.loperilla.compracasa.databinding.FragmentAddShoppingListBinding
import com.loperilla.compracasa.shoppinglist.data.InputsState
import com.loperilla.compracasa.shoppinglist.data.PostState
import com.loperilla.compracasa.shoppinglist.viewmodel.AddShoppingListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddShoppingListFragment : Fragment() {
    private var _binding: FragmentAddShoppingListBinding? = null
    private lateinit var viewRoot: View
    private val binding get() = _binding!!
    private val viewModel: AddShoppingListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddShoppingListBinding.inflate(inflater, container, false)
        viewRoot = binding.root
        return viewRoot
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.isValidFormState.observe(viewLifecycleOwner) { inputState ->
            Log.e("isValid", inputState.name)
            binding.btnAdd.isEnabled = inputState == InputsState.CORRECT
        }

        viewModel.isPostSuccess.observe(viewLifecycleOwner) {
            if (it == PostState.SUCCESS) {
                Toast.makeText(viewRoot.context, "Se agregó con éxito", Toast.LENGTH_LONG).show()
                findNavController().navigateUp()
            }

            if (it == PostState.FAIL) {
                Toast.makeText(viewRoot.context, "Algo salió mal", Toast.LENGTH_LONG).show()
            }

        }

        val afterTextChangedListener = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // ignore
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // ignore
            }

            override fun afterTextChanged(s: Editable) {
                viewModel.inputsDataChange(
                    binding.etTest.text.toString(),
                    binding.etTest2.text.toString()
                )
            }
        }
        binding.etTest.addTextChangedListener(afterTextChangedListener)
        binding.etTest2.addTextChangedListener(afterTextChangedListener)
        binding.btnAdd.setOnClickListener {
            viewModel.addShoppingList(
                ShoppingListItem(
                    title = binding.etTest.text.toString(),
                    date = binding.etTest2.text.toString()
                )
            )
        }
    }
}
