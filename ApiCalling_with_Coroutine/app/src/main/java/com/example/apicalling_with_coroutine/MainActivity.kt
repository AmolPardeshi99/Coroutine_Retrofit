package com.example.apicalling_with_coroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var UserList : List<ResponseModelItem>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CoroutineScope(Dispatchers.IO).launch {

            val res: String  = async {


                val apiService = Network.getInstance().create(ApiService::class.java)
                apiService.getUserData().enqueue(object: Callback<ResponseModel>{
                    override fun onResponse(
                        call: Call<ResponseModel>,
                        response: Response<ResponseModel>
                    ) {
                        response.body()?.run {
                            UserList = response.body()!!
                            tvText1.text = response.body()?.get(0)?.fullName.toString()
                            tvText2.text = response.body()?.get(0)?.gitUrl.toString()
                        }
                    }

                    override fun onFailure(call: Call<ResponseModel>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                })
                return@async "apiservice1"
            }.await()

            CoroutineScope(Dispatchers.Main).launch {
                tvText2.text = res.toString()
            }
        }
    }
}