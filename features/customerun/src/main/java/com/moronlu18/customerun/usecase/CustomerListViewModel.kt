package com.moronlu18.customerun.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.signup.utils.Locator
import com.moronlu18.customer.entity.Customer
import com.moronlu18.customer.repository.CustomerRepository
import com.moronlu18.customerun.ui.CustomerListState
import kotlinx.coroutines.launch

class CustomerListViewModel : ViewModel() {
   // private val customerRepository = InvoiceRepository()
    var allcustomers = initallCustomer()

    private var state = MutableLiveData<CustomerListState>()
    fun getState(): LiveData<CustomerListState> {
        return state
    }

    fun initallCustomer(): LiveData<List<Customer>> {
        if (Locator.invoicePreferencesRepository.getCustomerOr() == "Id") {
            return CustomerRepository.getCustomerList().asLiveData()
        } else {
            return CustomerRepository.getCustomerListOrderByName().asLiveData()
        }
    }
    fun validate() {
        viewModelScope.launch {
        when  {
             allcustomers.value?.isEmpty() == true -> CustomerListState.noDataError
            else -> {
                state.value = CustomerListState.Success
            }
        }
            }
    }
}