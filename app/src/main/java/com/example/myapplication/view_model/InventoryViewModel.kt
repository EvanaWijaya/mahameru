package com.example.myapplication.view_model

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.models.inventory.GetInventoryData
import com.example.myapplication.data.models.inventory.GetInventoryResponse
import com.example.myapplication.data.repositories.ticket.GetInventoryRepository
import com.example.myapplication.utils.SharedPrefs
import com.example.myapplication.utils.showToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InventoryViewModel(
    private val getInventoryRepository : GetInventoryRepository ,
) : ViewModel() {

    var isLoading=mutableStateOf(false)
    private var getInventoryResponse=mutableStateOf<GetInventoryResponse?>(null)
    var errorMessage=mutableStateOf<String?>(null)
    var inventoryId=mutableStateOf(0)
    var inventoryQuantity=mutableStateOf(0)
    var totalCost=mutableStateOf(0)

    private var inventory=mutableStateOf<List<GetInventoryData>>(emptyList())


    var inventoryQuantities=mutableStateOf<Map<String , Int>>(emptyMap())

    fun calculateTotalCost() {
        var newTotalCost = 0
        inventory.value.forEach { inventory ->
            val inventoryPrice = parsePriceWithoutDecimal(inventory.price)
            val quantity = inventoryQuantities.value[inventory.name] ?: 0
            newTotalCost += inventoryPrice * quantity
            Log.d("InventoryViewModel", "Item: ${inventory.name}, Quantity: $quantity, Price: $inventoryPrice, Total: $newTotalCost")
        }
        totalCost.value = newTotalCost
        Log.d("InventoryViewModel", "Total cost updated: $newTotalCost")
    }


    private fun parsePriceWithoutDecimal(price : String?) : Int {
        return price?.split(".")?.get(0)?.toIntOrNull() ?: 0
    }


    fun updateInventoryQuantity(inventoryName : String , quantity : Int) {
        inventoryQuantities.value=inventoryQuantities.value.toMutableMap().apply {
            this[inventoryName]=quantity
        }
        calculateTotalCost()
    }



    fun formatPriceWithoutDecimal(price : String?) : String {
        return price?.split(".")?.get(0) ?: "0"
    }



    fun getInventory(context : Context) {
        isLoading.value=true
        val token=SharedPrefs.getToken(context)

        token?.let {
            viewModelScope.launch(Dispatchers.IO) {
                getInventoryRepository.getInventory(it) { response , error ->
                    isLoading.value=false
                    if (response != null) {
                        getInventoryResponse.value=response
                        errorMessage.value=null
                        Log.d("InventoryViewModel" , "getInventory: ${response.data}")
                        inventory.value=response.data

                    } else {
                        errorMessage.value=error
                        showToast(context , error ?: "Gagal mendapatkan data tiket")
                    }
                }
            }
        } ?: run {
            isLoading.value=false
            errorMessage.value="No token found"
            showToast(context , "Token tidak ditemukan")
        }
    }

    fun resetInventoryQuantities() {
        inventoryQuantities.value = emptyMap()
        inventoryQuantity.value = 0
        totalCost.value = 0
    }


    fun getInventorys()=getInventoryResponse.value


}
