package com.sonasetiana.takehometest.utils

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import com.sonasetiana.takehometest.R

fun Context.toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

fun View?.showPopup(
    context: Context,
    callback : ((String) -> Unit)? = null
){
    val popupMenu = PopupMenu(context, this)
    popupMenu.inflate(R.menu.options_menu)
    popupMenu.gravity = Gravity.END
    popupMenu.setOnMenuItemClickListener { menu ->
        callback?.invoke(menu.title.toString())
        true
    }
    popupMenu.show()
}