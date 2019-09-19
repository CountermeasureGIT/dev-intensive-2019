package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyboard() {
    val imm: InputMethodManager =
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
}

private fun Activity.isSoftKeyboardShown(): Boolean {
    val permissibleError =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50F, resources.displayMetrics)
            .toInt()
    val rootView = findViewById<View>(android.R.id.content)
    val rect = Rect()
    rootView.getWindowVisibleDisplayFrame(rect)
    val difference = rootView.height - rect.height()
    return (difference > permissibleError)
}

fun Activity.isKeyboardOpen(): Boolean {
    return isSoftKeyboardShown()
}

fun Activity.isKeyboardClosed(): Boolean {
    return !isSoftKeyboardShown()
}
