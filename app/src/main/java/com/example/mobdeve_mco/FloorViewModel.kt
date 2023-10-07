package com.example.mobdeve_mco

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FloorViewModel : ViewModel() {
    private val _floor = MutableLiveData<String>()
    val floor: LiveData<String> = _floor

    fun setFloor(value: String) {
        _floor.value = value
    }
}
