package com.loperilla.compracasa.main.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.loperilla.compracasa.R
import com.loperilla.compracasa.data.model.ShoppingListItem
import com.loperilla.compracasa.databinding.FragmentHomeBinding
import com.loperilla.compracasa.main.adapter.ShoppingListVH
import com.loperilla.compracasa.main.data.ShoppingState

class HomeFragment : Fragment() {
    private lateinit var viewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var mFirebaseAdapter: FirebaseRecyclerAdapter<ShoppingListItem, ShoppingListVH>

    private val args by navArgs<HomeFragmentArgs>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
//        binding.tvName.text = args.displayName
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

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
        Log.e("Loading", "loading")
        with(binding) {
            progressbar.isVisible = true
            rvList.isVisible = false
        }
    }

    private fun showRecyclerView() {
        Log.e("showRecyclerView", "showRecyclerView")
        with(binding) {
            progressbar.isVisible = false
            rvList.isVisible = true
        }
    }

    private fun showErrorView() {
        with(binding) {
            progressbar.isVisible = false
            rvList.isVisible = true
        }
    }

    private fun setUpRecyclerView(options: FirebaseRecyclerOptions<ShoppingListItem>) {
        mFirebaseAdapter =
            object : FirebaseRecyclerAdapter<ShoppingListItem, ShoppingListVH>(options) {
                private lateinit var mContext: Context
                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListVH {
                    with(
                        LayoutInflater.from(parent.context)
                            .inflate(R.layout.shopping_item, parent, false)
                    ) {
                        mContext = context
                        return ShoppingListVH(this)
                    }
                }

                override fun onBindViewHolder(
                    holder: ShoppingListVH,
                    position: Int,
                    model: ShoppingListItem
                ) {
                    val shoppingListItem = getItem(position)
                    Log.e("shopping", shoppingListItem.toString())
                    with(holder.binding) {
                        Toast.makeText(mContext, shoppingListItem.title, Toast.LENGTH_LONG).show()
                        tvDate.text = shoppingListItem.date
                    }
                }

                override fun onDataChanged() {
                    super.onDataChanged()
                    binding.progressbar.isVisible = false
                }

            }
        val mLayoutManager = LinearLayoutManager(binding.root.context)
        binding.rvList.apply {
            setHasFixedSize(true)
            layoutManager = mLayoutManager
            adapter = mFirebaseAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        if (this::mFirebaseAdapter.isInitialized) {
            mFirebaseAdapter.startListening()
        }
    }

    override fun onStop() {
        super.onStop()
        if (this::mFirebaseAdapter.isInitialized) {
            mFirebaseAdapter.stopListening()
        }
    }
}
