package com.software.jgodort.tipcalculatormvvm.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.databinding.Observable
import android.databinding.Observable.OnPropertyChangedCallback
import android.databinding.PropertyChangeRegistry
import com.android.databinding.library.baseAdapters.BR

abstract class ObservableViewModel(application: Application) : AndroidViewModel(application),
  Observable {

  @delegate:Transient
  private val delegateCallback: PropertyChangeRegistry by lazy { PropertyChangeRegistry() }

  override fun removeOnPropertyChangedCallback(callback: OnPropertyChangedCallback?) {
    delegateCallback.remove(callback)
  }

  override fun addOnPropertyChangedCallback(callback: OnPropertyChangedCallback?) {
    delegateCallback.add(callback)
  }

  fun notifyChange() {
    delegateCallback.notifyChange(this, BR._all)
  }
}