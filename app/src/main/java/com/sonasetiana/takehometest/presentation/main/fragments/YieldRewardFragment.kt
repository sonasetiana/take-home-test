package com.sonasetiana.takehometest.presentation.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sonasetiana.takehometest.databinding.FragmentYieldRewardBinding
import com.sonasetiana.takehometest.datasource.main.MainDataSource
import com.sonasetiana.takehometest.datasource.main.MainRepository
import com.sonasetiana.takehometest.presentation.main.MainViewModel

class YieldRewardFragment: Fragment() {

    private var binding : FragmentYieldRewardBinding? = null

    private val mainRepository by lazy { MainRepository(MainDataSource.create()) }

    private val mainViewModel by lazy { ViewModelProvider(requireActivity())[MainViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentYieldRewardBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.initRepository(mainRepository)
        with(mainViewModel){
            loadingGetChartData().observe(viewLifecycleOwner, {

            })
            successGetChartData().observe(viewLifecycleOwner, {

            })
            errorGetChartData().observe(viewLifecycleOwner, {

            })

            loadingGetProductDetail().observe(viewLifecycleOwner, {

            })
            successGetProductDetail().observe(viewLifecycleOwner, {

            })
            errorGetProductDetail().observe(viewLifecycleOwner, {

            })

            requestData()
        }
    }

    private fun requestData(){
        with(mainViewModel){
            val params = mapOf(
                    "productCodes" to "KI002MMCDANCAS00",
                    "productCodes" to "TP002EQCEQTCRS00",
                    "productCodes" to "NI002EQCDANSIE00"
            )
            getChartData(params)
            getProductDetail(params)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}