package com.dicoding.latihan.githubuserdicoding

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.latihan.githubuserdicoding.detail.follow.FollowersFragment
import com.dicoding.latihan.githubuserdicoding.detail.follow.FollowingFragment

class SectionPagerAdapter(private val mContext: Context, fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    var username: String? = null

    companion object{
        @StringRes
        private val tabTitle = intArrayOf(R.string.followers, R.string.following)
    }

    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowersFragment.newInstanse(username)
            1 -> fragment = FollowingFragment.newInstanse(username)
        }
        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(tabTitle[position])
    }

}