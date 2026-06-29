package com.example.codingnfc.features.listItems

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.codingnfc.ItemBox
import com.example.codingnfc.ItemData
import com.example.codingnfc.ItemElement
import com.example.codingnfc.ItemValue
import io.paperdb.Paper

class ListItemsViewModel : ViewModel() {

    val itemsLiveData = MutableLiveData<List<ItemElement>>()

    private lateinit var itemInfo: List<ItemElement>
    val itemBox: ItemBox = ItemBox(163, 1225, 3192, 2908)

    fun readItemsInfo() {
        if (Paper.book().read<List<ItemElement>>("itemInfo") != null) {
            this.itemInfo = Paper.book().read("itemInfo")!!
        } else {
            this.itemInfo = listOf(ItemElement(null, listOf(ItemValue(itemBox, listOf(ItemData("", 0.99f))))))
        }
        itemsLiveData.value = this.itemInfo
    }


}