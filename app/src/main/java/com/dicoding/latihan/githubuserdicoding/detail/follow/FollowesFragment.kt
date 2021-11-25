package com.dicoding.latihan.githubuserdicoding.detail.follow

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.dicoding.latihan.githubuserdicoding.R
import com.dicoding.latihan.githubuserdicoding.databinding.FragmentFollowBinding

class FollowesFragment : Fragment(R.layout.fragment_follow) {

    private var _binding : FragmentFollowBinding? = null
    private val binding get() = _binding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFollowBinding.bind(view)
    }
}