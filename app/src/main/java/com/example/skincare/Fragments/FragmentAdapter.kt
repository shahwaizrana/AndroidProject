package com.example.skincare.Fragments

import android.content.Context
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class FragmentAdapter(
    private val myContext: Context,
    fragmentManager: FragmentManager,
    internal var totalTabs: Int
) : FragmentPagerAdapter(
    fragmentManager,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {

    var titles = arrayOf("Description","Causes", "Syptoms","Prevention","Treatment")
    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                //  val homeFragment: HomeFragment = HomeFragment()
                return DescriptionFragment();
            }
            1 -> {
                return CausesFragment()
            }
            2 -> {
                return SyptomsFragment();
            }

            3 -> {
                return PreventionFragment()
            }

            4 -> {
                return TreatmentFragment()
            }
            else -> return DescriptionFragment();
        }
    }

    override fun getCount(): Int {
        return totalTabs;
    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return titles.get(position)
    }

}