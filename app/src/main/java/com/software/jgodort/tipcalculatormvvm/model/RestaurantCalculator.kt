package com.software.jgodort.tipcalculatormvvm.model

import android.arch.lifecycle.LiveData
import java.math.RoundingMode

class RestaurantCalculator(val repository: TipCalculationRepository) {

  fun calculateTip(
    checkAmount: Double,
    tipPercentage: Int
  ): TipCalculation {

    val tipAmount = (checkAmount * (tipPercentage.toDouble() / 100.0))
      .toBigDecimal()
      .setScale(2, RoundingMode.HALF_UP)
      .toDouble()
    val grandTotal = checkAmount + tipAmount


    return TipCalculation(
      checkAmount = checkAmount,
      tipPercentage = tipPercentage.toDouble(),
      tipAmount = tipAmount,
      grandTotal = grandTotal
    )
  }

  fun saveTipCalculation(tipCalculation: TipCalculation) {
    repository.saveTipCalculation(tipCalculation)
  }

  fun loadTipCalculationByLocationName(locationName: String): TipCalculation? {
    return repository.loadTipCalculationByName(locationName)
  }

  fun loadSavedCalculations(): LiveData<List<TipCalculation>> {
    return repository.loadTipCalculations()
  }
}