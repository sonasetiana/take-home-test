package com.sonasetiana.takehometest.presentation.main

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.TypefaceSpan
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.tabs.TabLayoutMediator
import com.sonasetiana.takehometest.R
import com.sonasetiana.takehometest.databinding.ActivityMainBinding
import com.sonasetiana.takehometest.presentation.main.adapter.MainPagerAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding){
            with(toolbar){
                title = getTitleToolbar("Perbandingan")
                setNavigationOnClickListener { onBackPressed() }
            }
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

    fun getTitleToolbar(title: String): String{
        return SpannableString(title).apply {
            val font = Typeface.create(ResourcesCompat.getFont(this@MainActivity, R.font.montserrat_medium), Typeface.BOLD)
            setSpan(TypefaceSpan(font), 0, title.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        }.toString()
    }
}