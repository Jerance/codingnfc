package com.example.codingnfc

import com.google.gson.annotations.SerializedName
import java.io.File
import java.io.Serializable

data class ItemResponseWrapper (
    @SerializedName("objectsResult")
    val itemResponse: ItemResponse
)

data class ItemResponse(
    @SerializedName("values")
    val itemValues: List<ItemValue>
)
data class ItemElement(
    val image: String?,
    val itemValue: List<ItemValue>
)
data class ItemValue (
    @SerializedName("boundingBox")
    val itemBoundingBox: ItemBox,
    @SerializedName("tags")
    val itemTags: List<ItemData>
)

data class ItemBox (
    val x: Int,
    val y: Int,
    val w: Int,
    val h: Int
)

data class ItemData(
    val name: String,
    val confidence: Float
)