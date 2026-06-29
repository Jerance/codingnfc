package com.example.codingnfc.localstorage

import com.example.codingnfc.ItemElement
import io.paperdb.Paper

interface Localstorage {

    fun readItemElements() : MutableList<ItemElement>
    fun writeItemElements(list: List<ItemElement>)
}

class LocalstoragePaper : Localstorage {
    override fun readItemElements(): MutableList<ItemElement> {
        val itemsStorage : MutableList<ItemElement>? = Paper.book().read<MutableList<ItemElement>?>("itemInfo")?.toMutableList()
        return itemsStorage ?: mutableListOf()
    }

    override fun writeItemElements(list: List<ItemElement>) {
        Paper.book().write("itemInfo", list)

    }

}