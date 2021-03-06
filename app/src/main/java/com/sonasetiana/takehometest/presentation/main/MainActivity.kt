package com.sonasetiana.takehometest.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.sonasetiana.takehometest.databinding.ActivityMainBinding
import com.sonasetiana.takehometest.presentation.main.adapter.MainPagerAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding){
            toolbar.setNavigationOnClickListener { onBackPressed() }
            with(viewPager2){
                adapter = MainPagerAdapter(this@MainActivity)
                TabLayoutMediator(tabLayout, this){ tab, index ->
                    tab.text = when(index){
                        0 -> "Imbal Hasil"
                        else -> "Dana Kelolaan"
                    }
                }.attach()
            }
        }
    }
}