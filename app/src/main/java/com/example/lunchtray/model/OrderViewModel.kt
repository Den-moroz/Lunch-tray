package com.example.lunchtray.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.lunchtray.data.DataSource
import java.text.NumberFormat

class OrderViewModel : ViewModel() {

    val menuItems = DataSource.menuItems

    private var previousEntreePrice = 0.0
    private var previousSidePrice = 0.0
    private var previousAccompanimentPrice = 0.0

    private val taxRate = 0.08

    private val _entree = MutableLiveData<MenuItem?>()
    val entree: LiveData<MenuItem?> = _entree

    private val _side = MutableLiveData<MenuItem?>()
    val side: LiveData<MenuItem?> = _side

    private val _accompaniment = MutableLiveData<MenuItem?>()
    val accompaniment: LiveData<MenuItem?> = _accompaniment

    private val _subtotal = MutableLiveData(0.0)
    val subtotal: LiveData<String> = _subtotal.map {
        NumberFormat.getCurrencyInstance().format(it)
    }

    private val _total = MutableLiveData(0.0)
    val total: LiveData<String> = _total.map {
        NumberFormat.getCurrencyInstance().format(it)
    }

    private val _tax = MutableLiveData(0.0)
    val tax: LiveData<String> = _tax.map {
        NumberFormat.getCurrencyInstance().format(it)
    }

    init {
        resetOrder()
    }

    fun setEntree(entreeName: String) {
        if (_entree.value != null) {
            previousEntreePrice = _entree.value!!.price
        }
        if (_subtotal.value!! > 0) {
            _subtotal.value = _subtotal.value?.minus(previousEntreePrice)
        }

        val selectedEntree = menuItems[entreeName]
        if (selectedEntree != null) {
            _entree.value = selectedEntree
            updateSubtotal(_entree.value!!.price)
        } else {
            _entree.value = null
        }
    }

    fun setSide(side: String) {
        if (_side.value != null) {
            previousSidePrice = _side.value!!.price
        }
        if (_subtotal.value!! > 0.0) {
            _subtotal.value = _subtotal.value?.minus(previousSidePrice)
        }
        val selectedSide = menuItems[side]
        if (selectedSide != null) {
            _side.value = selectedSide
            updateSubtotal(_side.value!!.price)
        } else {
            _entree.value = null
        }
    }

    fun setAccompaniment(accompaniment: String) {
        if (_accompaniment.value != null) {
            previousAccompanimentPrice = _accompaniment.value!!.price
        }
        if (_subtotal.value!! > 0.0) {
            _subtotal.value = _subtotal.value?.minus(previousAccompanimentPrice)
        }
        val selectedAccompaniment = menuItems[accompaniment]
        if (selectedAccompaniment != null) {
            _accompaniment.value = selectedAccompaniment
            updateSubtotal(_accompaniment.value!!.price)
        } else {
            _accompaniment.value = null
        }
    }

    private fun updateSubtotal(itemPrice: Double) {
        if (_subtotal.value != null) {
            _subtotal.value = _subtotal.value?.plus(itemPrice)
        } else {
            _subtotal.value = itemPrice
        }
    }

    fun calculateTaxAndTotal() {
        _tax.value = _subtotal.value?.times(taxRate)
        _total.value = _tax.value?.let { _subtotal.value?.plus(it) }
    }

    fun resetOrder() {
        _entree.value = null
        _side.value = null
        _accompaniment.value = null
        _subtotal.value = 0.0
        _total.value = 0.0
        _tax.value = 0.0
    }
}
