
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.codingnfc.ItemElement
import com.example.codingnfc.ItemResponseWrapper
import com.example.codingnfc.api.interfaces.ItemApiFile
import com.example.codingnfc.features.home.HomeActivity
import com.example.codingnfc.localstorage.Localstorage
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


sealed class HomeViewState {
    object Loading : HomeViewState()
    data class Success(val itemResponse: ItemResponseWrapper) : HomeViewState()
    data class Error(val message: String) : HomeViewState()

}
class HomeViewModel : ViewModel() {

    lateinit var apiFile: ItemApiFile
    lateinit var localstorage: Localstorage
    private val ALL_PERMISSIONS_REQUEST_CODE = 200

    val stateLiveData = MutableLiveData<HomeViewState>()

    fun checkAndRequestPermissions(activity: HomeActivity) {
        val permissions = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permissionsToRequest = permissions.filter {
                ContextCompat.checkSelfPermission(activity, it) != PackageManager.PERMISSION_GRANTED
            }.toTypedArray()

            if (permissionsToRequest.isNotEmpty()) {
                ActivityCompat.requestPermissions(activity, permissionsToRequest, ALL_PERMISSIONS_REQUEST_CODE)
            }
        }
    }


    fun postItem(file: File) {
        val requestBody = RequestBody.create("image/jpeg".toMediaTypeOrNull(),file)

        val call = apiFile.createItem(requestBody)

        call.enqueue(object : Callback<ItemResponseWrapper> {
            override fun onResponse(
                call: Call<ItemResponseWrapper>,
                response: Response<ItemResponseWrapper>
            ) {
                if (response.isSuccessful) {
                    val item = response.body()
                    // Traitement de la réponse


                    val itemValues = item?.itemResponse?.itemValues
                    if (!itemValues.isNullOrEmpty()) {
                        val itemInfo = ItemElement(file.toString(), itemValues)
                        val itemList : MutableList<ItemElement> = localstorage.readItemElements()
                        itemList.add(itemInfo)
                        localstorage.writeItemElements(itemList)
                        println(itemList)
                        stateLiveData.value = HomeViewState.Success(item)
                    } else {
                        Log.e("Paper", "ItemValues is null")
                        stateLiveData.value = HomeViewState.Error("object not recognized")
                    }

                } else {
                    Log.e("homeview", "Unsuccessful response: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ItemResponseWrapper>, t: Throwable) {
                println(t)
                stateLiveData.value = HomeViewState.Error("request failed")
            }
        })
    }
}
