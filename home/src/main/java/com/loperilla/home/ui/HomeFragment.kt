package com.loperilla.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.loperilla.compracasa.databinding.FragmentHomeBinding
import com.loperilla.compracasa.main.ui.HomeFragmentDirections
import com.loperilla.home.adapter.ShoppingListAdapter
import com.loperilla.home.data.ShoppingState

class HomeFragment : Fragment() {
    private lateinit var viewModel: HomeViewModel
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
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

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
    }

    private fun showLoadingView() {
        with(binding) {
            progressbar.isVisible = true
            progressbar.animate()
            rvList.isVisible = false
            ivEmpty.isVisible = false
        }
    }

    private fun showRecyclerView() {
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

    private fun setUpRecyclerView(shoppingList: List<com.loperilla.data.model.ShoppingListItem>) {
        val shoppingListAdapter = ShoppingListAdapter(shoppingList)
        binding.rvList.apply {
            setHasFixedSize(true)
            adapter = shoppingListAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getUserShoppingList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
