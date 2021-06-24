package com.sonasetiana.takehometest.presentation.main.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.android.material.tabs.TabLayout
import com.sonasetiana.takehometest.databinding.FragmentYieldRewardBinding
import com.sonasetiana.takehometest.datasource.main.MainDataSource
import com.sonasetiana.takehometest.datasource.main.MainRepository
import com.sonasetiana.takehometest.presentation.main.MainViewModel
import com.sonasetiana.takehometest.presentation.main.adapter.YieldRewardAdapter
import com.sonasetiana.takehometest.utils.*

class YieldRewardFragment: Fragment(), TabLayout.OnTabSelectedListener {

    private var binding : FragmentYieldRewardBinding? = null

    private val mainRepository by lazy {
        MainRepository(MainDataSource.create())
    }

    private val mainViewModel by lazy {
        ViewModelProvider(requireActivity())[MainViewModel::class.java]
    }

    private val productAdapter by lazy {
        YieldRewardAdapter()
    }

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
        binding?.apply { 
            tabPeriod.addOnTabSelectedListener(this@YieldRewardFragment)
            swipeRefresh.setOnRefreshListener {
                swipeRefresh.isRefreshing = false
                requestData()
            }
            rvProducts.adapter = productAdapter

        }
        setupViewModel()
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        when(binding?.tabPeriod?.selectedTabPosition){
            0 -> "1W"
            1 -> "1M"
            2 -> "1Y"
            3 -> "3Y"
            4 -> "5Y"
            5 -> "10Y"
            else -> "All"
        }.run {
            productAdapter.setPeriod(this)
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) = Unit

    override fun onTabReselected(tab: TabLayout.Tab?) = Unit

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

    @SuppressLint("SetTextI18n")
    private fun setupViewModel(){
        binding?.apply {
            with(mainViewModel){
                loadingGetChartData().observe(viewLifecycleOwner, {

                })
                successGetChartData().observe(viewLifecycleOwner, { items ->
                    val dataSet = ArrayList<ILineDataSet>()
                    (0 until items.size).forEach {
                        val item = items[it]
                        val color = when(it){
                            0 -> "#E2EBDD"
                            1 -> "#E0DBEB"
                            else -> "#E0E8EE"
                        }
                        dataSet.add(item.data.toLineDataSet(mColor = color))
                        dataSet.add(item.data.toCenterLineDataSet(mColor = color))
                    }

                    items.toDataLegend().let { legend ->
                        txtFirst.text = "${legend["first"]}%"
                        txtCenter.text = "${legend["center"]}%"
                        txtLast.text = "${legend["last"]}%"
                        txtDate.text = legend["date"].toString()
                    }

                    lineChart.legend.isEnabled = false
                    lineChart.data = LineData(dataSet)
                    lineChart.axisLeft.isEnabled = false
                    lineChart.axisRight.valueFormatter = AxisRightFormatter()
                    lineChart.axisRight.setDrawGridLines(false)
                    lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
                    lineChart.xAxis.valueFormatter = items.toAXisValueFormatter()
                    lineChart.xAxis.setDrawGridLines(false)
                    lineChart.description.isEnabled = false
                    lineChart.animateXY(100, 500)
                })
                errorGetChartData().observe(viewLifecycleOwner, {

                })

                loadingGetProductDetail().observe(viewLifecycleOwner, {

                })
                successGetProductDetail().observe(viewLifecycleOwner, {
                    productAdapter.setItems(it)
                })
                errorGetProductDetail().observe(viewLifecycleOwner, {

                })

                requestData()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}