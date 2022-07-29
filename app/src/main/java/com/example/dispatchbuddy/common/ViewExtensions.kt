package com.example.dispatchbuddy.common

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar

object ViewExtensions {

    fun View.showView() {
        this.visibility = View.VISIBLE
    }

    fun View.hideView() {
        this.visibility = View.GONE
    }

    fun Activity.showShortToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun Fragment.showShortToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    fun hideKeyBoard(context: Context, view: View) {
        val imm = context.getSystemService(
            Context.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun Fragment.showShortSnackBar(message: String) {
        val snackBar = Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT)
        val snackBarView: View = snackBar.view
        val snackTextView: TextView =
            snackBarView.findViewById(com.google.android.material.R.id.snackbar_text)
        snackTextView.maxLines = 3
        snackBar.show()
    }

    fun Activity.showShortSnackBar(message: String, view: View) {
        val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
        val snackBarView: View = snackBar.view
        val snackTextView: TextView =
            snackBarView.findViewById(com.google.android.material.R.id.snackbar_text)
        snackTextView.maxLines = 3
        snackBar.show()
    }
}

fun Fragment.popBackStack(){
    findNavController().popBackStack()
}

fun Fragment.handleBackPress(){
    activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            popBackStack()
        }
    })
}