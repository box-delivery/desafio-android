package com.geanbrandao.desafioandroid

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import kotlinx.android.synthetic.main.custom_error_dialog.view.*
import timber.log.Timber
import java.net.ConnectException
import java.net.UnknownHostException

fun Activity.goToActivity(activityClass: Class<*>) {
    startActivity(Intent(this, activityClass))
}


fun Activity.globalExceptionHandle(error: Throwable): AlertDialog {
    val tag = this::class.java.simpleName
    Timber.tag(tag).e(error)
    if (error is HttpException) {
        if (error.code() > 499) {
            return showDialogError(getString(R.string.errors_server_error))
        } else {
            if (error.code() in 400..499) {

                return showDialogError(getString(R.string.errors_request_error))

            }
        }
    }

    if (error is UnknownHostException || error is ConnectException) {
        return showDialogError(getString(R.string.errors_no_connection))
    }

    return showDialogError(getString(R.string.errors_generic))
}

// dialog de erro comum
fun Activity.showDialogError(message: String): AlertDialog {
    val dialogView =
        LayoutInflater.from(this).inflate(R.layout.custom_error_dialog, null)
    val builder = AlertDialog.Builder(this)
    builder.setView(dialogView)
    val alertDialog = builder.create()

    if (message.isNotEmpty()) {
        dialogView.text_message.text = message
    }

    dialogView.button_ok.setOnClickListener {
        alertDialog.dismiss()
    }

    alertDialog.setCanceledOnTouchOutside(false)
    alertDialog.setCancelable(false)

    alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

    alertDialog.show()

    return alertDialog
}


fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    return true
                }
            }
        }
    } else {
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
            return true
        }
    }
    return false
}
