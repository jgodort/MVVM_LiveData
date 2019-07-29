package com.software.jgodort.tipcalculatormvvm.view

import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.software.jgodort.tipcalculatormvvm.R
import com.software.jgodort.tipcalculatormvvm.databinding.ActivityTipCalculatorBinding
import com.software.jgodort.tipcalculatormvvm.viewmodel.CalculatorViewModel

class TipCalculatorActivity : AppCompatActivity(), SavedDialogFragment.Callback,
  LoadDialogFragment.Callback {

  private lateinit var binding: ActivityTipCalculatorBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_tip_calculator)
    binding.vm = ViewModelProviders.of(this).get(CalculatorViewModel::class.java)
    setSupportActionBar(binding.toolbar)
  }

  override fun onTipSelected(name: String) {
    binding.vm?.loadTipCalculation(name)
  }

  override fun onSaveTip(locationName: String) {
    binding.vm?.saveCurrentTip(locationName)

    Snackbar.make(binding.root, locationName, Snackbar.LENGTH_LONG).show()
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_tip_calculator, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.action_save -> {
        showSavedDialog()
        true
      }
      R.id.action_load -> {
        showLoadDialog()
        true
      }
      else -> {
        super.onOptionsItemSelected(item)
      }
    }
  }

  private fun showLoadDialog() {
    val loadFragment = LoadDialogFragment()
    loadFragment.show(supportFragmentManager, "LoadDialog")
  }

  private fun showSavedDialog() {
    val dialogFragment = SavedDialogFragment()
    dialogFragment.show(supportFragmentManager, "SaveDialog")
  }
}
