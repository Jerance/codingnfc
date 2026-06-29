package com.example.codingnfc

import HomeViewModel
import HomeViewState
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.codingnfc.api.interfaces.ItemApiFile
import com.example.codingnfc.localstorage.Localstorage
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import okhttp3.RequestBody
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import retrofit2.Call
import retrofit2.mock.Calls
import java.io.File
import java.io.IOException


@Mock
lateinit var mockItem: ItemResponseWrapper
private lateinit var mockFile: File
val element = ItemValue(ItemBox(268, 461, 318, 341), listOf(ItemData("television", 0.599f)))

// Mock de l'objet ItemResponseWrapper
val mockItemResponse = ItemResponseWrapper(
    ItemResponse(listOf(element))
)


class FakeApi : ItemApiFile {
    override fun createItem(body: RequestBody): Call<ItemResponseWrapper> {
        return Calls.response(mockItemResponse)
    }
}
class FakeLocalstorage: Localstorage {
    override fun readItemElements(): MutableList<ItemElement> {
        return mutableListOf(ItemElement("file.jpg", listOf(element)))
    }

    override fun writeItemElements(list: List<ItemElement>) {
    }

}
class HomeViewModelTest {


    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()



    @Test
    fun postItemSuccess() {

        //ARRANGE
        val model = HomeViewModel()
        model.localstorage = FakeLocalstorage()
        model.apiFile = FakeApi()
        val mockFile: File = Mockito.mock(File::class.java)

        //ACT
        model.postItem(mockFile)

        //ASSERT
        Assert.assertEquals( HomeViewState.Success(mockItemResponse),
            model.stateLiveData.value
        )
    }


    @Test
    fun postItemFailure() {
        // Arrange
        val viewModel = HomeViewModel()
        val mockApi = mock<ItemApiFile>()
        val mockFile: File = Mockito.mock(File::class.java)

        whenever(mockApi.createItem(any()))
            .thenReturn(Calls.failure(IOException()))
        viewModel.apiFile = mockApi

        // Act
        viewModel.postItem(mockFile)

        // Assert
        Assert.assertEquals(
            HomeViewState.Error("request failed"),
            viewModel.stateLiveData.value
        )
    }
}

