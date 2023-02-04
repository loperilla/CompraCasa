package com.loperilla.compracasa.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.DatabaseReference
import com.loperilla.compracasa.R
import com.loperilla.compracasa.databinding.FragmentFirstBinding
import com.loperilla.compracasa.firebase.Auth.checkIfIsUserLogged
import com.loperilla.compracasa.firebase.Database.setValueInReference

class MainFragment : Fragment() {
    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private lateinit var testReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            setValueInReference(testReference, "test")
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
//        testReference.addValueEventListener(
//            object : ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    val value: String = snapshot.value.toString()
//                    binding.textviewFirst.text = value
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    // Failed to read value
//                    binding.textviewFirst.text = error.toException().toString()
//                    Log.w("onCancelled", "Failed to read value.", error.toException())
//                }
//            }
//        )

    }

    override fun onStart() {
        super.onStart()
        if (!checkIfIsUserLogged()) {
            findNavController().navigate(
                MainFragmentDirections.actionFirstFragmentToLoginFragment()
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
