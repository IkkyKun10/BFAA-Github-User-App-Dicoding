package com.dicoding.latihan.githubuserdicoding.detail.follow

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.latihan.githubuserdicoding.R
import com.dicoding.latihan.githubuserdicoding.databinding.FragmentFollowBinding
import com.dicoding.latihan.githubuserdicoding.detail.UserDetailActivity
import com.dicoding.latihan.githubuserdicoding.raw.UserSearch
import com.dicoding.latihan.githubuserdicoding.viewmodel.FollowingViewModel

class FollowingFragment : Fragment(R.layout.fragment_follow) {

    private var _binding : FragmentFollowBinding? = null
    private val binding get() = _binding
    private val viewModel by viewModels<FollowingViewModel>()
    private lateinit var mAdapter: FollowAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFollowBinding.bind(view)

        val username = arguments?.getString(ARGS_NAME)

        mAdapter = FollowAdapter()
        showRecycler()

        showLoading(true)
        username?.let { viewModel.setFollowingList(it) }
        viewModel.getFollowingList().observe(viewLifecycleOwner, {items ->
            if (items != null) {
                mAdapter.setList(items)
                showLoading(false)
            }
            val item = items.isEmpty()
            if (item) {
                binding?.frameError?.visibility = View.VISIBLE
            }
        })

        mAdapter.setOnItemClickCallback(object : FollowAdapter.OnItemClickCallback {
            override fun onItemClick(data: UserSearch) {
                Intent(requireActivity(), UserDetailActivity::class.java).also {
                    it.putExtra(UserDetailActivity.EXTRA_INTENT, data.username)
                    it.putExtra(UserDetailActivity.EXTRA_USERS, data)
                    startActivity(it)
                }
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


    companion object {
        private const val ARGS_NAME = "username"

        fun newInstanse(username: String?) : FollowingFragment {
            val fragment = FollowingFragment()
            val mBundle = Bundle()
            mBundle.putString(ARGS_NAME, username)
            fragment.arguments = mBundle

            return fragment
        }
    }
}