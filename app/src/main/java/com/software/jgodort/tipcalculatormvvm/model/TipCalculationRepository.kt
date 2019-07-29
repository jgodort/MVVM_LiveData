package com.software.jgodort.tipcalculatormvvm.model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData

class TipCalculationRepository {

  private val savedTips = mutableMapOf<String, TipCalculation>()

  fun saveTipCalculation(tip: TipCalculation) {
    savedTips[tip.locationName] = tip
  }

  fun loadTipCalculationByName(locationName: String): TipCalculation? {
    return savedTips[locationName]
  }

  fun loadTipCalculations(): LiveData<List<TipCalculation>> {
    var liveData = MutableLiveData<List<TipCalculation>>()
    liveData.value = savedTips.values.toList()
    return liveData
  }
}