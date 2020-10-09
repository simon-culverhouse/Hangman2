package com.fragmentise.hangman2.util

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.databinding.BindingAdapter
import com.fragmentise.hangman2.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking


@BindingAdapter("hangmanIcon")
    fun hangman(view: ImageView, hangNum: Int) {
    view.setImageDrawable(getDrawableHangman(hangNum, view.context))
    }


private fun getDrawableHangman(hangNum: Int, context: Context): Drawable? {
    return when (hangNum) {
        0 -> {
            ContextCompat.getDrawable(context, R.drawable.ic_hang_0)
        }
        1 -> {
            ContextCompat.getDrawable(context, R.drawable.ic_hang_1)
        }
        2 -> {
            ContextCompat.getDrawable(context, R.drawable.ic_hang_2)
        }
        3 -> {
            ContextCompat.getDrawable(context, R.drawable.ic_hang_3)
        }
        4 -> {
            ContextCompat.getDrawable(context, R.drawable.ic_hang_4)
        }
        5 -> {
            ContextCompat.getDrawable(context, R.drawable.ic_hang_5)
        }
        6 -> {
            ContextCompat.getDrawable(context, R.drawable.ic_hang_6)
        }
        7 -> {
            ContextCompat.getDrawable(context, R.drawable.ic_hang_7)
        }
        8 -> {
            ContextCompat.getDrawable(context, R.drawable.ic_hang_8)
        }
        9 -> {
            ContextCompat.getDrawable(context, R.drawable.ic_hang_9)
        }
        10 -> {
            ContextCompat.getDrawable(context, R.drawable.ic_hang_10)
        }
        11 -> {
            ContextCompat.getDrawable(context, R.drawable.ic_hang_11)
        }
        else -> {
            ContextCompat.getDrawable(context, R.drawable.ic_hang_0)
        }
    }
}

@BindingAdapter("app:changeTextColor")
    fun newTextColor(answerTextView: TextView, correctWord: Boolean) {

    if (correctWord) answerTextView.setTextColor(Color.parseColor("#ad0a78"))

}




