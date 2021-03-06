package com.ponomar.shoper.ui.order_history

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.ponomar.shoper.base.LiveCoroutinesViewModel
import com.ponomar.shoper.model.entities.Order
import com.ponomar.shoper.repository.OrderRepository

class OrderHistoryViewModel @ViewModelInject constructor(
        private val repository: OrderRepository,
        @Assisted savedStateHandle: SavedStateHandle
) : LiveCoroutinesViewModel() {
    private val _toastMutableLiveData:MutableLiveData<String> = MutableLiveData()
    private val _fetchingOrderHistoryByToken:MutableLiveData<String> = MutableLiveData()

    val toastLiveData:LiveData<String> = _toastMutableLiveData
    val isLoading:ObservableBoolean = ObservableBoolean(false)
    val orderLiveData: LiveData<List<Order>>

    init {
        orderLiveData = _fetchingOrderHistoryByToken.switchMap {
            launchOnViewModelScope {
                isLoading.set(true)
                repository.fetchHistoryOfOrder(
                        token = it,
                        onComplete = { isLoading.set(false)},
                        onError = { _toastMutableLiveData.value = it}
                ).asLiveData()
            }
        }
    }


    fun fetchHistoryOfOrder(token:String){
        _fetchingOrderHistoryByToken.value = token
    }

    override fun onCleared() {
        super.onCleared()
    }
}