package com.toohome.android.reviewroom.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.toohome.android.reviewroom.data.model.LoggedUser
import com.toohome.android.reviewroom.databinding.FragmentUserDetailBinding
import com.toohome.android.reviewroom.ui.ViewModelFactory

class UserDetailFragment : Fragment() {
    private val model: UserDetailViewModel by viewModels(factoryProducer = { ViewModelFactory() })
    private val user: LoggedUser by lazy { model.getUser() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        binding.username.text = user.username
        binding.collectionsButton.setOnClickListener {
            val action = UserDetailFragmentDirections.actionUserDetailFragmentToCollectionsFragment(user.id)
            it.findNavController().navigate(action)
        }
        return binding.root
    }
}
