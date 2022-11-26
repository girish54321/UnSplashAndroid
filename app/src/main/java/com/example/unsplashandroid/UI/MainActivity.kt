package com.example.unsplashandroid.UI
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.myquizapp.helper.BasicAlertDialog
import com.example.myquizapp.helper.LoadingScreen
import com.example.unsplashandroid.Api.RetrofitInstance
import com.example.unsplashandroid.R
import com.example.unsplashandroid.adpter.PhotoRVAdapter
import com.example.unsplashandroid.const.Constants
import com.example.unsplashandroid.databinding.ActivityMainBinding
import com.example.unsplashandroid.modal.UnPlashResponse
import retrofit2.HttpException
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private var binding: ActivityMainBinding? = null

    private var playerNames = arrayOf("Cristiano Ronaldo", "Joao Felix", "Bernado Silva", "Andre Silve",
        "Bruno Fernandez", "William Carvalho", "Nelson Semedo", "Pepe", "Rui Patricio")
    private var playerImages = intArrayOf(R.drawable.image, R.drawable.image, R.drawable.image,
        R.drawable.image, R.drawable.image, R.drawable.image, R.drawable.image, R.drawable.image, R.drawable.image)
//    lateinit var photoList: ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

//        photoList = ArrayList()

        // on below line we are initializing our adapter
//        var photoRVAdapter = PhotoRVAdapter(photoList)

        // on below line we are setting layout manager for our recycler view
//        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
//        binding?.gridView?.layoutManager = staggeredGridLayoutManager

        // on below line we are setting
        // adapter to our recycler view.
//        binding?.gridView?.adapter = photoRVAdapter

        // on below line we are adding data to our list
//        photoList.add("https://videocdn.geeksforgeeks.org/geeksforgeeks/2DTranslationinComputerGraphics/2DTranslationinComputerGraphics20220628122713-small.png")
//        photoList.add("https://videocdn.geeksforgeeks.org/geeksforgeeks/PythonProgramforFibonacciSeries/FibonacciseriesinPython20220627183541-small.png")
//        photoList.add("https://pbs.twimg.com/media/FV6-TWhUsAY92R_.jpg")
//        photoList.add("https://videocdn.geeksforgeeks.org/geeksforgeeks/PerformCRUDOperationusingFirebaseinFlutter/PerformCRUDOperationusingFirebaseinFlutter20220627152121-small.png")
//        photoList.add("https://videocdn.geeksforgeeks.org/geeksforgeeks/CProgramtoConvertLowercasetoUppercaseviceversa/CProgramtoConvertLowercasetoUppercase20220627145001-small.png")
//        photoList.add("https://videocdn.geeksforgeeks.org/geeksforgeeks/OptimalPageReplacementAlgorithminOS/OptimalPageReplacement20220627124822-small.png")
//        photoList.add("https://videocdn.geeksforgeeks.org/geeksforgeeks/JavaProgramtoFindQuotientRemainder/JavaProgramtoFindQuotientandRemainder20220626125601-small.png")
//        photoList.add("https://videocdn.geeksforgeeks.org/geeksforgeeks/FirstandFollowinCompilerDesign/FirstFollowinCompilerDesign20220624172015-small.png")
//        photoList.add("https://media.geeksforgeeks.org/wp-content/uploads/geeksforgeeks-25.png")
//        photoList.add("https://media.geeksforgeeks.org/wp-content/uploads/20220531202857/photo6102488593462309569.jpg")
//        photoList.add("https://practice.geeksforgeeks.org/_next/image?url=https%3A%2F%2Fmedia.geeksforgeeks.org%2Fimg-practice%2Fbanner%2Fdsa-self-paced-thumbnail.png%3Fv%3D19171&w=1920&q=75")
//        photoList.add("https://practice.geeksforgeeks.org/_next/image?url=https%3A%2F%2Fmedia.geeksforgeeks.org%2Fimg-practice%2Fbanner%2Fcompetitive-programming-live-thumbnail.png%3Fv%3D19171&w=1920&q=75")

        // on below line we are notifying adapter
        // that data has been updated.
//        photoRVAdapter.notifyDataSetChanged()
        getRandomQuestion()
    }

    private fun setUpImageList(photoList: List<UnPlashResponse>){
        var photoRVAdapter = PhotoRVAdapter(photoList)
        binding?.gridView?.adapter = photoRVAdapter
        val staggeredGridLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding?.gridView?.layoutManager = staggeredGridLayoutManager
        photoRVAdapter.notifyDataSetChanged()
    }

    private fun getRandomQuestion(){
        val context: Context = this
        LoadingScreen.displayLoadingWithText(this,"Please wait...",false)
        lifecycleScope.launchWhenCreated {
            val response = try {
                RetrofitInstance.api.getPhotos(Constants.APK_KEY,Constants.ORDER_BY,"30")
            } catch(e: IOException) {
                LoadingScreen.hideLoading()
                BasicAlertDialog.displayBasicAlertDialog(context,"Error","IOException, you might not have internet connection",false)
                Log.e(TAG, "IOException, you might not have internet connection")
                return@launchWhenCreated
            } catch (e: HttpException) {
                LoadingScreen.hideLoading()
                BasicAlertDialog.displayBasicAlertDialog(context,"Error","HttpException, unexpected response",false)
                Log.e(TAG, "HttpException, unexpected response")
                return@launchWhenCreated
            }
            if(response.isSuccessful && response.body() != null) {
                val data: List<UnPlashResponse>  = response.body()!!
                LoadingScreen.hideLoading()
                data[0]?.let { it.blurHash?.let { it1 -> Log.e(TAG, it1) } }
                if(data.isEmpty()){
                    return@launchWhenCreated
                }
                setUpImageList(data)
            } else {
                LoadingScreen.hideLoading()
                BasicAlertDialog.displayBasicAlertDialog(context,"Error","Response not successful",false)
                Log.e(TAG, "Response not successful")
            }
        }
    }
}