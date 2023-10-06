package com.example.mobdeve_mco

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


class AddListingFormAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AddListingStep1Fragment()
            1 -> AddListingStep2Fragment()
            2 -> AddListingStep3Fragment()
            3 -> AddListingStep4Fragment()
            4 -> AddListingStep5Fragment()
            5 -> AddListingStep6Fragment()
            else -> AddListingStep1Fragment()
        }
    }

    override fun getItemCount(): Int {
        return 6
    }
}
