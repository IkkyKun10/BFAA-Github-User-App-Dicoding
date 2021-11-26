package com.dicoding.latihan.githubuserdicoding.detail.follow

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.latihan.githubuserdicoding.R
import com.dicoding.latihan.githubuserdicoding.databinding.FragmentFollowBinding

class FollowersFragment : Fragment(R.layout.fragment_follow) {

    companion object {
        private val ARGS_NAME = "username"

        fun newInstanse(username: String?) : FollowersFragment {
            val fragment = FollowersFragment()
            val mBundle = Bundle()
            mBundle.putString(ARGS_NAME, username)
            fragment.arguments = mBundle

            return fragment
        }
    }

    private var _binding : FragmentFollowBinding? = null
    private val binding get() = _binding
    private val viewModel by viewModels<FollowersViewModel>()
    private lateinit var mAdapter: FollowAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFollowBinding.bind(view)

        val username = arguments?.getString(ARGS_NAME)

        mAdapter = FollowAdapter()
        showRecycler()

        showLoading(true)
        username?.let { viewModel.setFollowersList(it) }
        viewModel.getFollowersList().observe(viewLifecycleOwner, {items ->
            if (items != null) {
                mAdapter.setList(items)
                showLoading(false)
            }
        })
    }

    private fun showRecycler() {
        binding?.rvFollow?.layoutManager = LinearLayoutManager(context)
        binding?.rvFollow?.setHasFixedSize(true)
        binding?.rvFollow?.adapter = mAdapter
    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading){
            binding?.progressBar?.visibility = View.VISIBLE
        } else {
            binding?.progressBar?.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}