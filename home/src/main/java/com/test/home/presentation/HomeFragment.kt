package com.test.home.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.home.databinding.FragmentHomeBinding
import com.test.home.presentation.adapter.OfferAdapter
import com.test.model.TypeSuggest
import com.test.utils.getFromSuggest
import com.test.utils.getToSuggest
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var binding_: FragmentHomeBinding? = null
    private val binding get() = binding_!!

    private var modelView_: HomeModelView? = null
    private val modelView get() = modelView_!!
    private lateinit var adapter: OfferAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding_ = FragmentHomeBinding.inflate(inflater, container, false)
        modelView_ = ViewModelProvider(requireActivity())[HomeModelView::class]

        init()
        initObserver()
        return binding.root
    }

    private fun init(){
        binding.suggestMusicTravel.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        adapter = OfferAdapter()
        binding.suggestMusicTravel.adapter = adapter

        binding.formTv.setOnClickListener {
            InputTownBottomSheet().showNow(childFragmentManager, "formTv")
        }

        binding.toTv.setOnClickListener {
            InputTownBottomSheet().showNow(childFragmentManager, "toTv")
        }

        binding.toTv.getSaveValue(TypeSuggest.TO_TOWN)
        binding.formTv.getSaveValue(TypeSuggest.FROM_TOWN)
    }

    private fun TextView.getSaveValue(type: TypeSuggest){
        when(type){
            TypeSuggest.TO_TOWN -> lifecycleScope.launch {
                getToSuggest(requireContext())?.let {
                    hint = hint.toString().plus(" - ").plus(it)
                }
            }
            TypeSuggest.FROM_TOWN -> lifecycleScope.launch {
                getFromSuggest(requireContext())?.let {
                    hint = hint.toString().plus(" - ").plus(it)
                }
            }
        }
    }

    private fun initObserver(){
        modelView.offers.observe(viewLifecycleOwner){
            it?.let {
                binding.waitLoadMusic.visibility = View.GONE
                binding.suggestMusicTravel.visibility = View.VISIBLE
                adapter.offers = it.offers
                adapter.notifyDataSetChanged()
            }
        }

        modelView.townTo.observe(requireActivity()){
            it?.let {
                binding.toTv.text = it
            }
        }

        modelView.townFrom.observe(requireActivity()){
            it?.let {
                binding.formTv.text = it
            }
        }
    }
}
