package com.loperilla.compracasa.main.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.loperilla.compracasa.data.model.ShoppingListItem
import com.loperilla.compracasa.databinding.FragmentHomeBinding
import com.loperilla.compracasa.main.adapter.ShoppingListAdapter
import com.loperilla.compracasa.main.data.ShoppingState
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModel()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.shoppingListState.observe(viewLifecycleOwner) { state ->
            if (state == ShoppingState.LOADING) {
                showLoadingView()
                return@observe
            }
            if (state == ShoppingState.SUCCESSFUL) {
                showRecyclerView()
                return@observe
            }

            showErrorView()
        }

        viewModel.shoppingListItemLiveData.observe(viewLifecycleOwner) { optionList ->
            setUpRecyclerView(optionList)
        }

        binding.fabAdd.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToAddShoppingListFragment()
            )
        }
        viewModel.getUserShoppingList()
    }

    private fun showLoadingView() {
        Log.e("Loading", "loading")
        with(binding) {
            progressbar.isVisible = true
            progressbar.animate()
            rvList.isVisible = false
            ivEmpty.isVisible = false
        }
    }

    private fun showRecyclerView() {
        Log.e("showRecyclerView", "showRecyclerView")
        with(binding) {
            progressbar.isVisible = false
            rvList.isVisible = true
            ivEmpty.isVisible = false
        }
    }

    private fun showErrorView() {
        with(binding) {
            progressbar.isVisible = false
            ivEmpty.isVisible = true
            rvList.isVisible = false
        }
    }

    private fun setUpRecyclerView(shoppingList: List<ShoppingListItem>) {
        val shoppingListAdapter = ShoppingListAdapter(shoppingList)
        binding.rvList.apply {
            setHasFixedSize(true)
            adapter = shoppingListAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
