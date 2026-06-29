package com.example.codingnfc.api.interfaces

import com.example.codingnfc.ItemResponseWrapper
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ItemApiFile {

    @Headers(
        "Ocp-Apim-Subscription-Key: 950f757730c54f6e8feae4b9967d4780",
        "Content-Type: application/octet-stream"
    )
    @POST("computervision/imageanalysis:analyze?api-version=2023-02-01-preview&language=en&gender-neutral-caption=False&features=objects")
    fun createItem( @Body body: RequestBody): Call<ItemResponseWrapper>

}