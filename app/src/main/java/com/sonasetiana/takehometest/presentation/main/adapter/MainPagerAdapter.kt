package com.sonasetiana.takehometest.presentation.main.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.sonasetiana.takehometest.presentation.main.fragments.ManagedFundsFragment
import com.sonasetiana.takehometest.presentation.main.fragments.YieldRewardFragment

class MainPagerAdapter(fm: FragmentActivity): FragmentStateAdapter(fm) {

    private var fragments = ArrayList<Fragment>()

    init {
        fragments.add(YieldRewardFragment())
        fragments.add(ManagedFundsFragment())
    }

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}