package com.software.jgodort.tipcalculatormvvm.view

import android.app.AlertDialog
import android.app.Dialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.widget.DividerItemDecoration
import android.view.LayoutInflater
import android.view.View
import com.software.jgodort.tipcalculatormvvm.R
import com.software.jgodort.tipcalculatormvvm.viewmodel.CalculatorViewModel
import kotlinx.android.synthetic.main.saved_tip_calculations_list.view.recycler_saved_calculations

class LoadDialogFragment : DialogFragment() {

  interface Callback {
    fun onTipSelected(name: String)
  }

  private var loadTipCallback: Callback? = null

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    loadTipCallback = context as? Callback
  }

  override fun onDetach() {
    super.onDetach()
    loadTipCallback = null
  }

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    val dialog = context?.let {
      AlertDialog.Builder(it)
        .setView(createView(it))
        .setNegativeButton(R.string.action_cancel, null)
        .create()
    }
    return dialog!!
  }

  private fun createView(context: Context): View {
    val recycler = LayoutInflater.from(context)
      .inflate(R.layout.saved_tip_calculations_list, null)
      .recycler_saved_calculations
    recycler.setHasFixedSize(true)
    recycler.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

    val adapter = TipSummaryAdapter {
      loadTipCallback?.onTipSelected(it.locationName)
      dismiss()
    }
    recycler.adapter = adapter
    val vm = ViewModelProviders.of(activity!!).get(CalculatorViewModel::class.java)

    vm.loadSavedTipCalculationSummaries().observe(this, Observer {
      if (it != null) {
        adapter.updateList(it)
      }
    })
    return recycler
  }

}