package com.example.king_bob_nae.features.home.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.king_bob_nae.R
import com.example.king_bob_nae.base.BaseFragment
import com.example.king_bob_nae.databinding.FragmentFriendsHomeBinding
import com.example.king_bob_nae.features.home.domain.friendshome.KkilogState
import com.example.king_bob_nae.features.home.presentation.adapter.AllKkilogAdapter
import com.example.king_bob_nae.features.home.presentation.adapter.UserListAdapter
import com.example.king_bob_nae.features.home.presentation.viewmodel.HomeViewModel
import com.example.king_bob_nae.features.myprofile.presentation.MyProfileActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FriendsHomeFragment :
    BaseFragment<FragmentFriendsHomeBinding>(R.layout.fragment_friends_home) {
    private val userListAdapter by lazy { UserListAdapter(homeViewModel) }
    private val allKkilogAdapter by lazy { AllKkilogAdapter(::itemClick) }
    private val homeViewModel: HomeViewModel by activityViewModels()
    var userId: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectFlows()
        initView()
    }

    private fun initView() {
        val args: FriendsHomeFragmentArgs by navArgs()
        userId = args.userId

        homeViewModel.setSelectedUserId(userId)

        binding.rvAllKkiLog.adapter = allKkilogAdapter
        binding.commonHomeLayout.rvFriends.apply {
            adapter = userListAdapter
        }

        binding.ivMy.setOnClickListener {
            startActivity(Intent(requireActivity(), MyProfileActivity::class.java))
        }
        binding.commonHomeLayout.ivAdd.setOnClickListener {
            it.findNavController().navigate(R.id.action_friendsHomeFragment_to_followingFragment)
        }
        binding.tvSpeechBubble.setOnClickListener {
            it.visibility = View.GONE
        }
    }

    private fun collectFlows() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                homeViewModel.run {
                    launch {
                        homeUserFriendList.collect {
                            userListAdapter.submitList(it)
                        }
                    }

                    launch {
                        homeFriendsStatus.collectLatest {
                            binding.home = it
                            binding.commonHomeLayout.home = it
                            binding.commonHomeLayout.tvKkilog.text = "${it.userNickName}의 끼록"
                        }
                    }

                    launch {
                        goHomeFragmentEvent.collect {
                            val action =
                                FriendsHomeFragmentDirections.actionFriendsHomeFragmentToHomeFragment(
                                    it
                                )
                            findNavController().navigate(action)
                        }
                    }

                    launch {
                        goFriendsHomeFragmentEvent.collect {
                            homeViewModel.setSelectedUserId(it)
                        }
                    }

                    launch {
                        allKkilogList.collect { allKkilog ->
                            allKkilogAdapter.submitList(allKkilog)
                            binding.friendsCount = allKkilog.count()
                        }
                    }
                }
            }
        }
    }

    private fun itemClick(kkilogState: KkilogState) {
        if (kkilogState.type == "상세 끼록") {
            val action =
                FriendsHomeFragmentDirections.actionFriendsHomeFragmentToDetailKkiLogResultFragment(
                    userId = kkilogState.id
                )
            findNavController().navigate(action)
        } else {
            val action =
                FriendsHomeFragmentDirections.actionFriendsHomeFragmentToKkilogResultFragment(
                    kkilogState.id
                )
            findNavController().navigate(action)
        }

    }
}
