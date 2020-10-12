package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.graphics.Rect
import android.util.DisplayMetrics
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyboard() {
    val imm: InputMethodManager =
        this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view: View? = this.currentFocus
    if (view == null)
        view = View(this)
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Activity.isKeyboardOpen() : Boolean {
    val SOFT_KEYBOARD_HEIGHT_DP_THRESHOLD = 128

    val rootView = this.findViewById<View>(android.R.id.content)
    val r = Rect()
    rootView.getWindowVisibleDisplayFrame(r)
    val dm: DisplayMetrics = rootView.resources.displayMetrics
    val heightDiff = rootView.bottom - r.bottom
    return heightDiff > dm.density * SOFT_KEYBOARD_HEIGHT_DP_THRESHOLD
}

fun Activity.isKeyboardClosed() : Boolean {
    return !isKeyboardOpen()
}