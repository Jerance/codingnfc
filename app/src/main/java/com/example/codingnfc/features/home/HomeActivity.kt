package com.example.codingnfc.features.home

import HomeViewModel
import HomeViewState
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.codingnfc.R
import com.example.codingnfc.api.RetrofitClient
import com.example.codingnfc.api.interfaces.ItemApiFile
import com.example.codingnfc.databinding.ActivityHomeBinding
import com.example.codingnfc.features.listItems.ListItemsActivity
import com.example.codingnfc.localstorage.LocalstoragePaper
import io.paperdb.Paper
import retrofit2.Retrofit
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.util.UUID


private const val TAG = "HomeActivity"

class HomeActivity : AppCompatActivity() {

    lateinit var binding: ActivityHomeBinding
    lateinit var imageFile: File
    val api: Retrofit = RetrofitClient().retrofit
    val viewModel by viewModels<HomeViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Paper.clear(this)
        Paper.init(this)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.localstorage = LocalstoragePaper()
        val retrofitCreate = api.create(ItemApiFile::class.java)
        viewModel.apiFile = retrofitCreate

        binding.recyclerButton.setOnClickListener {
            val intent = Intent(this, ListItemsActivity::class.java)
            startActivity(intent)
        }
        viewModel.checkAndRequestPermissions(this)

        viewModel.stateLiveData.observe(this) { state ->
            handleStateChange(state)
        }

        binding.cameraButton.setOnClickListener { takePhoto() }

        binding.cameraButton.setOnLongClickListener{
            viewModel.postItem(simulateFile())
            true
        }

    }

    private fun simulateFile(): File {
        val imageFile1 =  File(filesDir, "file.jpg");

        val os : OutputStream = FileOutputStream(imageFile1);

        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.car)

        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, os);
        os.flush();
        os.close();

        return imageFile1
    }

    private fun handleStateChange(state: HomeViewState?): HomeViewState? {
        when (state) {
            is HomeViewState.Success -> {
                val intent = Intent(this, ListItemsActivity::class.java)
                startActivity(intent)
            }

            is HomeViewState.Error -> {
                // Gérer l'erreur
                Log.e("HomeActivity", "Error: ${state.message}")
                Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
            }

            is HomeViewState.Loading -> {
                Log.i(TAG, "handleStateChange: Loading")
            }

            else -> {}
        }
        return state
    }


    private fun takePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val stringId = generateStringId()

        imageFile = File(filesDir, stringId + ".jpg")

        val TAG = "pictureActivity"
        Log.i(TAG, "takePicture:$imageFile ")

        val uri = FileProvider.getUriForFile(
            this,
            "com.example.android.fileprovider",
            imageFile
        )

        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        startActivityForResult(intent, 1)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Log.i(TAG, "image enregistrée. imageFile=$imageFile")
            viewModel.postItem(imageFile)
        }
    }

    fun generateStringId(): String {
        return UUID.randomUUID().toString()
    }

}





