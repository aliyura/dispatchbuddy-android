package com.example.dispatchbuddy.common

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.Context
import android.content.res.Resources
import android.net.Uri
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.dispatchbuddy.databinding.LogoutDialogLayoutBinding
import java.util.*

fun getDaysAgo(daysAgo: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, daysAgo)
    return calendar.time
}

@SuppressLint("Range")
fun ContentResolver.getFileName(uri: Uri): String {
    var name = ""
    val cursor =query(uri,null, null, null, null)
    cursor?.use {
        it?.moveToFirst()
        name = cursor.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
    }
    return name
}

fun showLogOutDialog(context: Context, binding: LogoutDialogLayoutBinding, resources: Resources, logOutFunction: () -> Unit): AlertDialog {
    val builder = AlertDialog.Builder(context)
    builder.setView(binding.root)
    builder.setCancelable(false)
    val dialog = builder.create()
    val width = (resources.displayMetrics.widthPixels * 0.80).toInt()
    val height = (resources.displayMetrics.heightPixels * 0.35).toInt()
    dialog!!.window?.setLayout(width, height)

    binding.logoutDialogYesTextView.setOnClickListener {
        logOutFunction.invoke()
        Toast.makeText(context,"Logout successful", Toast.LENGTH_SHORT).show()
        dialog.dismiss()
    }
    binding.logoutDialogNoTextView.setOnClickListener {
        dialog.dismiss()
    }
    return dialog
}