package com.example.dispatchbuddy.common

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.dispatchbuddy.R
import com.example.dispatchbuddy.common.Constants.FROM_LOGOUT
import com.example.dispatchbuddy.common.preferences.Preferences
import com.example.dispatchbuddy.presentation.ui.authentication.AuthenticationActivity
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


fun Fragment.userLogOut(preferences: Preferences){
    preferences.deleteTokenInfo()
    preferences.deleteUserId()
    val intent = Intent(requireContext(), AuthenticationActivity::class.java)
    intent.putExtra(FROM_LOGOUT, true)
    startActivity(intent)
    activity?.finish()
}

fun Fragment.setProgressDialog(context:Context, message:String): AlertDialog {
    val llPadding = 30
    val ll = LinearLayout(context)
    ll.orientation = LinearLayout.HORIZONTAL
    ll.setPadding(llPadding, llPadding, llPadding, llPadding)
    ll.gravity = Gravity.CENTER
    var llParam = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT)
    llParam.gravity = Gravity.CENTER
    ll.layoutParams = llParam

    val progressBar = ProgressBar(context)
    progressBar.isIndeterminate = true
    progressBar.setPadding(0, 0, llPadding, 0)
    progressBar.layoutParams = llParam

    llParam = LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT)
    llParam.gravity = Gravity.CENTER
    val tvText = TextView(context)
    tvText.text = message
    tvText.setTextColor(Color.parseColor("#000000"))
    tvText.textSize = 20.toFloat()
    tvText.layoutParams = llParam

    ll.addView(progressBar)
    ll.addView(tvText)

    val builder = AlertDialog.Builder(context)
    builder.setCancelable(true)
    builder.setView(ll)

    val dialog = builder.create()
    val window = dialog.window
    if (window != null) {
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window?.attributes)
        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
        dialog.window?.attributes = layoutParams
    }
    return dialog
}