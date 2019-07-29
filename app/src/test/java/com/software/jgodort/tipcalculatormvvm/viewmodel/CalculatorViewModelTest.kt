package com.software.jgodort.tipcalculatormvvm.viewmodel

import android.app.Application
import com.software.jgodort.tipcalculatormvvm.R
import com.software.jgodort.tipcalculatormvvm.model.RestaurantCalculator
import com.software.jgodort.tipcalculatormvvm.model.TipCalculation
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyDouble
import org.mockito.Mockito.anyInt
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class CalculatorViewModelTest {

  private lateinit var calculatorViewModel: CalculatorViewModel

  @Mock
  lateinit var mockCalculator: RestaurantCalculator

  @Mock
  lateinit var mockApplication: Application

  @Before
  fun setup() {
    MockitoAnnotations.initMocks(this)
    stubResources(0.0, "$0.00")
    calculatorViewModel = CalculatorViewModel(mockApplication, mockCalculator)
  }

  private fun stubResources(
    given: Double,
    returnStub: String
  ) {
    `when`(
      mockApplication.getString(R.string.dollar_format, given)
    ).thenReturn(returnStub)
  }

  @Test
  fun testCalculateTip() {

    val stub = TipCalculation(
      checkAmount = 10.00,
      tipPercentage = 15.0,
      tipAmount = 1.5,
      grandTotal = 11.50
    )
    stubResources(10.00, "$10.00")
    stubResources(1.5, "$1.50")
    stubResources(11.50, "$11.50")

    `when`(
      mockCalculator.calculateTip(10.00, 15)
    ).thenReturn(stub)

    calculatorViewModel.inputCheckAmount = "10.00"
    calculatorViewModel.inputTipPercentage = "15"

    calculatorViewModel.calculateTip()

    assertEquals("$10.00", calculatorViewModel.outputCheckAmount)
    assertEquals("$1.50", calculatorViewModel.outputTipAmount)
    assertEquals("$11.50", calculatorViewModel.outputTotalDollarAmount)

  }

  @Test
  fun testCalculateBadTipPercent() {

    calculatorViewModel.inputCheckAmount = "10.00"
    calculatorViewModel.inputTipPercentage = ""

    calculatorViewModel.calculateTip()
    verify(mockCalculator, never()).calculateTip(anyDouble(), anyInt())

  }

  @Test
  fun testCalculateBadCheckAmount() {

    calculatorViewModel.inputCheckAmount = ""
    calculatorViewModel.inputTipPercentage = "15"

    calculatorViewModel.calculateTip()
    verify(mockCalculator, never()).calculateTip(anyDouble(), anyInt())

  }

  @Test
  fun testSaveCurrentTip() {
    val stub = TipCalculation(checkAmount = 10.0, tipPercentage = 1.5, grandTotal = 11.5)
    val stubLocationName = "Green Eggs and Bacon"

    calculatorViewModel.inputCheckAmount = "10.00"
    calculatorViewModel.inputTipPercentage = "15"
    `when`(mockCalculator.calculateTip(10.0, 15)).thenReturn(stub)

    calculatorViewModel.calculateTip()
    calculatorViewModel.saveCurrentTip(stubLocationName)

    verify(mockCalculator).saveTipCalculation(stub.copy(stubLocationName))
    assertEquals(stubLocationName, calculatorViewModel.locationName)
  }
}