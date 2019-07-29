package com.software.jgodort.tipcalculatormvvm

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.software.jgodort.tipcalculatormvvm.model.TipCalculation
import com.software.jgodort.tipcalculatormvvm.model.TipCalculationRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class TipCalculationRepositoryTest {

  @get:Rule
  var rule: TestRule = InstantTaskExecutorRule()

  private lateinit var repository: TipCalculationRepository

  @Before
  fun setup() {
    repository = TipCalculationRepository()
  }

  @Test
  fun testSaveTip() {
    val tip = TipCalculation(
      "Pancake Paradise",
      100.0,
      25.0,
      25.0,
      125.0
    )
    repository.saveTipCalculation(tip)

    assertEquals(tip, repository.loadTipCalculationByName(tip.locationName))
  }

  @Test
  fun testLoadSavedTipCalculations() {
    val tip1 = TipCalculation(
      "Pancake Paradise",
      100.0,
      25.0,
      25.0,
      125.0
    )

    val tip2 = TipCalculation(
      "Burger Paradise",
      40.0,
      13.0,
      13.0,
      53.0
    )
    repository.saveTipCalculation(tip1)
    repository.saveTipCalculation(tip2)

    repository.loadTipCalculations().observeForever { tipsCalculations ->
      assertEquals(2, tipsCalculations?.size)

    }
  }
}