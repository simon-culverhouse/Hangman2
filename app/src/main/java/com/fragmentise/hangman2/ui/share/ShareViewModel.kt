package com.fragmentise.hangman2.ui.share

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fragmentise.hangman2.R
import kotlinx.android.synthetic.main.fragment_game.view.*

class ShareViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "CORRECT"   }
    val text: LiveData<String> = _text
}