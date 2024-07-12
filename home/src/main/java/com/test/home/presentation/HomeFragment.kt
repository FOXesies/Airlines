package com.test.home.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.test.home.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var binding_: FragmentHomeBinding? = null
    private val binding get() = binding_!!

    private var modelView_: HomeModelView? = null
    private val modelView get() = modelView_!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding_ = FragmentHomeBinding.inflate(inflater, container, false)

        modelView_ = ViewModelProvider(this)[HomeModelView::class]

        return binding.root
    }

}