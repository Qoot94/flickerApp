package com.example.flickerapp
//	flicker browser App
//Key:
//ec5c71082d2d5ce29fd3fc6dcda7f3d6
//
//Secret:
//022c1d361d62ea0d

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var apiInterface: APIInterface
    lateinit var myRV: RecyclerView
    lateinit var rvAdapter: RVAdapter
    lateinit var searchText: EditText
    private var photoList = ArrayList<Photo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val searchButton = findViewById<Button>(R.id.btSearch)
        val dismissButton = findViewById<ImageButton>(R.id.ibCancel)
        val card=findViewById<CardView>(R.id.cvDetails)
        myRV = findViewById(R.id.rvMain)
        searchText = findViewById(R.id.etSearch)

        apiInterface = APIClient().getClient()?.create(APIInterface::class.java)!!

        searchButton.setOnClickListener {
            photoList.clear()
            if (searchText.text.isNotEmpty()) {
                card.visibility=View.GONE
                getPhotos()
            } else {
                Toast.makeText(applicationContext, "please enter a value", LENGTH_SHORT).show()
            }
        }

        dismissButton.setOnClickListener {
            card.visibility=View.GONE
        }
    }

    fun getPhotos() {
        val baseUrl = "https://www.flickr.com/services/rest/"
        val apiKey = "ec5c71082d2d5ce29fd3fc6dcda7f3d6"
        var url =
            "$baseUrl?method=flickr.photos.search&api_key=$apiKey&format=json&nojsoncallback=1&extras=url_h,tags&text=cat"

        apiInterface.getApi(
            "?method=flickr.photos.search&api_key=$apiKey" +
                    "&format=json&nojsoncallback=1&extras=url_h,tags&text=${searchText.text}"
        )
            .enqueue(object : Callback<PhotoObject> {
                override fun onResponse(call: Call<PhotoObject>, response: Response<PhotoObject>) {

                    //bring result of requested url
                    val response = response.body()!!
                    for (photo in response.photos.photo) {
                        photoList.add(photo)
                    }

                    //set inside recyclerview
                    rvAdapter = RVAdapter(this@MainActivity, photoList)
                    myRV.adapter = rvAdapter
                    myRV.layoutManager = LinearLayoutManager(applicationContext)

                    //check if there is result
                    if (photoList.size == 0) {
                        Toast.makeText(
                            applicationContext,
                            "no result, wait or try something else",
                            LENGTH_SHORT
                        ).show()

                    } else if (photoList.size > 0) {
                        Toast.makeText(
                            applicationContext,
                            "Found ${photoList.size} result(s)",
                            LENGTH_SHORT
                        ).show()
                    }
                    Log.d("mainAct", "able to bring data ${photoList.size}")
                }

                override fun onFailure(call: Call<PhotoObject>, t: Throwable) {
                    Log.d("mainAct", "unable to bring data $t")
                }
            })
    }
}