package com.software.jgodort.tipcalculatormvvm.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.View
import android.widget.EditText
import com.software.jgodort.tipcalculatormvvm.R

class SavedDialogFragment : DialogFragment() {

  interface Callback {
    fun onSaveTip(locationName: String)
  }

  private var saveTipCallback: Callback? = null

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    saveTipCallback = context as? Callback
  }

  override fun onDetach() {
    super.onDetach()
    saveTipCallback = null
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    val savedDialog = context?.let {
      val editText = EditText(it)
      editText.id = viewId
      editText.hint = getString(R.string.location_hint)

      AlertDialog.Builder(it)
        .setView(editText)
        .setNegativeButton(getString(R.string.action_cancel), null)
        .setPositiveButton(getString(R.string.action_save)) { _, _ -> onSave(editText) }
        .create()
    }

    return savedDialog!!
  }

  private fun onSave(editText: EditText) {
    val text = editText.text.toString()
    if (text.isNotEmpty()) {
      saveTipCallback?.onSaveTip(text)
    }
  }

  companion object {
    val viewId = View.generateViewId()
  }
}