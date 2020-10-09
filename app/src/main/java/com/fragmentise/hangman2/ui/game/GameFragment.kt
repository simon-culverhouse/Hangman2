package com.fragmentise.hangman2.ui.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.fragmentise.hangman2.R
import com.fragmentise.hangman2.databinding.FragmentGameBinding

class GameFragment : Fragment() {


    private val navController by lazy { findNavController(this) }
    private lateinit var binding: FragmentGameBinding
    private lateinit var gameViewModel: GameViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_game,
            container,
            false
        )
        // Get the viewModel
        gameViewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)

        // Set the viewmodel for databinding - this allows the bound layout access
        // to all the data in the VieWModel
        binding.gameViewModel = gameViewModel

        // Specify the current activity as the lifecycle owner of the binding.
        // This is used so that the binding can observe LiveData updates
        binding.lifecycleOwner = this

        gameViewModel.correctText.observe(this, Observer { isFinished ->
            if (isFinished) {
                println("is finished")
                val currentScore = gameViewModel.hangStage.value ?: 0
                val action = (R.id.action_nav_gallery_to_nav_share)
                navController.navigate(action)

            }
        })

        //NavHostFragment.findNavController(this).navigate(R.id.action_nav_gallery_to_correctDialog)

        return binding.root
    }
}