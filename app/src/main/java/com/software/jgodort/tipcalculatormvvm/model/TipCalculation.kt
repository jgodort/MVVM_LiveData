package com.software.jgodort.tipcalculatormvvm.model

data class TipCalculation(
  val locationName: String = "",
  val checkAmount: Double = 0.0,
  val tipPercentage: Double = 0.0,
  val tipAmount: Double = 0.0,
  val grandTotal: Double = 0.0
)