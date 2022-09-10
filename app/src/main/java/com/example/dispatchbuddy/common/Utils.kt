package com.example.dispatchbuddy.common

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.ContentResolver
import android.content.Context
import android.content.res.Resources
import android.net.Uri
import android.provider.OpenableColumns
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.dispatchbuddy.R
import com.example.dispatchbuddy.common.ViewExtensions.showShortSnackBar
import com.example.dispatchbuddy.databinding.LogoutDialogLayoutBinding
import com.example.dispatchbuddy.databinding.SuccessDialogLayoutBinding
import com.google.android.material.button.MaterialButton
import com.google.firebase.storage.FirebaseStorage
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

fun showSuccessDialog(
    context: Context,
    resources: Resources,
    updateUI:() -> Unit
): Dialog{
    val dialog = Dialog(context)
    val dialogSuccessView = View.inflate(context, R.layout.success_dialog_layout, null)
    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
    val width = (resources.displayMetrics.widthPixels * 0.80).toInt()
    val height = (resources.displayMetrics.heightPixels * 0.35).toInt()
    dialog.window?.setLayout(width, height)
    dialog.setCancelable(false)
    dialog.setCanceledOnTouchOutside(false)
    dialog.setContentView(dialogSuccessView)
    val closeButton: View = dialogSuccessView.findViewById(R.id.close_icon_btn)
    closeButton.setOnClickListener {
        updateUI.invoke()
        dialog.dismiss()
    }
    return dialog
}

fun uploadImageToFirebase(fileUri: Uri?, fragment: Fragment, context: Context, getUrl: (string: String)-> Unit){

    if (fileUri != null) {
        val fileName = UUID.randomUUID().toString()
        val refStorage = FirebaseStorage.getInstance().reference.child("images/$fileName")
        val dialog = fragment.setProgressDialog(context, "Uploading..")
        dialog.show()
        refStorage.putFile(fileUri)
            .addOnSuccessListener { taskSnapshot ->
                val result = taskSnapshot.metadata!!.reference!!.downloadUrl
                result.addOnSuccessListener { imageUrl->
                    getUrl(imageUrl.toString())
                    dialog.dismiss()
                    fragment.showShortSnackBar("Image uploaded successfully")
                }
            }
            .addOnFailureListener { e ->
                fragment.showShortSnackBar("${e.message}")
                dialog.dismiss()
            }
    }
}