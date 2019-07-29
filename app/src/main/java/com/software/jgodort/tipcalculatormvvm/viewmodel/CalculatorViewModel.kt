package com.software.jgodort.tipcalculatormvvm.viewmodel

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import com.software.jgodort.tipcalculatormvvm.R
import com.software.jgodort.tipcalculatormvvm.model.RestaurantCalculator
import com.software.jgodort.tipcalculatormvvm.model.TipCalculation
import com.software.jgodort.tipcalculatormvvm.model.TipCalculationRepository

class CalculatorViewModel @JvmOverloads constructor(
  application: Application,
  private val calculator: RestaurantCalculator = RestaurantCalculator(TipCalculationRepository())
) : ObservableViewModel(application) {

  private var lastTipCalculated = TipCalculation()
  var inputCheckAmount = ""
  var inputTipPercentage = ""

  val outputCheckAmount: String
    get() = getApplication<Application>().getString(
      R.string.dollar_format,
      lastTipCalculated.checkAmount
    )

  val outputTipAmount: String
    get() = getApplication<Application>().getString(
      R.string.dollar_format,
      lastTipCalculated.tipAmount
    )
  val outputTotalDollarAmount: String
    get() = getApplication<Application>().getString(
      R.string.dollar_format,
      lastTipCalculated.grandTotal
    )
  val locationName: String get() = lastTipCalculated.locationName

  init {
    updateOutputs(TipCalculation())
  }

  private fun updateOutputs(tipCalculation: TipCalculation) {
    lastTipCalculated = tipCalculation
    notifyChange()
  }

  fun calculateTip() {

    val checkAmount = inputCheckAmount.toDoubleOrNull()
    val tipPercentage = inputTipPercentage.toIntOrNull()

    if (checkAmount != null && tipPercentage != null) {
      updateOutputs(calculator.calculateTip(checkAmount, tipPercentage))
    }
  }

  fun saveCurrentTip(name: String) {

    val tipToSave = lastTipCalculated.copy(locationName = name)
    calculator.saveTipCalculation(tipToSave)
    updateOutputs(tipToSave)
  }

  fun loadTipCalculation(name: String) {

    val tc = calculator.loadTipCalculationByLocationName(name)

    if (tc != null) {
      inputCheckAmount = tc.checkAmount.toString()
      inputTipPercentage = tc.tipPercentage.toString()

      updateOutputs(tc)
      notifyChange()
    }
  }

  fun loadSavedTipCalculationSummaries() : LiveData<List<TipCalculationSummaryItem>> {
    return Transformations.map(calculator.loadSavedCalculations()) { tipCalculationObjects ->
      tipCalculationObjects.map {
        TipCalculationSummaryItem(it.locationName,
          getApplication<Application>().getString(R.string.dollar_format, it.grandTotal))
      }
    }
  }

}