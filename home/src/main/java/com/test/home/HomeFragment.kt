package com.test.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.test.home.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var binding_: FragmentHomeBinding? = null
    private val binding get() = binding_!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding_ = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

}