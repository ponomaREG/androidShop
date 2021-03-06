package com.ponomar.shoper.ui.menu

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.ponomar.shoper.base.LiveCoroutinesViewModel
import com.ponomar.shoper.model.sqlOutput.EmbeddedProduct
import com.ponomar.shoper.repository.ProductRepository

class MenuViewModel @ViewModelInject constructor(
        private val repository: ProductRepository,
        @Assisted private val savedStateHandle: SavedStateHandle
) : LiveCoroutinesViewModel() {

    private val _mutableToast:MutableLiveData<String> = MutableLiveData()
    private var _mutableFetchingPageOfProducts:MutableLiveData<Int> = MutableLiveData()


    val isLoading:ObservableBoolean = ObservableBoolean(false)
    val listOfProducts:LiveData<List<EmbeddedProduct>>
    val toastLiveData:LiveData<String> = _mutableToast

    init{
        listOfProducts = _mutableFetchingPageOfProducts.switchMap {
            launchOnViewModelScope {
                isLoading.set(true)
                repository.fetchListOfProducts(
                        onSuccess = {isLoading.set(false) },
                        onError = {_mutableToast.value=it
                        Log.e("ERROR",it)}
                ).asLiveData()
            }
        }
    }


    fun updateListOfProducts(page:Int){
        _mutableFetchingPageOfProducts.value = page
    }

}