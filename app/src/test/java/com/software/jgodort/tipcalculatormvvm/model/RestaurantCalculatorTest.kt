package com.software.jgodort.tipcalculatormvvm.model

import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class RestaurantCalculatorTest {

  private lateinit var calculator: RestaurantCalculator

  @Mock
  private lateinit var mockRepository: TipCalculationRepository

  @Before
  fun setup() {
    MockitoAnnotations.initMocks(this)
    calculator = RestaurantCalculator(mockRepository)
  }

  @Test
  fun testCalculateTip() {

    val baseTipCalculation = TipCalculation(checkAmount = 10.00)
    val testVals = listOf(
      baseTipCalculation.copy(tipPercentage = 20.0, tipAmount = 2.0, grandTotal = 12.0),
      baseTipCalculation.copy(tipPercentage = 15.0, tipAmount = 1.5, grandTotal = 11.50),
      baseTipCalculation.copy(tipPercentage = 18.0, tipAmount = 1.8, grandTotal = 11.80)
    )

    testVals.forEach {
      assertEquals(it, calculator.calculateTip(it.checkAmount, it.tipPercentage.toInt()))
    }
    val checkInput = 10.0
    val tipPercentage = 25

    val expectedTipResult = TipCalculation(
      checkAmount = checkInput,
      tipPercentage = 25.0,
      tipAmount = 2.50,
      grandTotal = 12.50
    )
    assertEquals(
      expectedTipResult,
      calculator.calculateTip(checkInput, tipPercentage)
    )
  }
}