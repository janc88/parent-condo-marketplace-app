package com.example.mobdeve_mco
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FloorAreaViewModel : ViewModel() {
    private val _floorArea = MutableLiveData<String>()
    val floorArea: LiveData<String> = _floorArea

    fun setFloorArea(value: String) {
        _floorArea.value = value
    }
}
