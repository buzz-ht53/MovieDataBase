package com.buzz_ht.moviedatabase.UI.Retrofit

import com.buzz_ht.moviedatabase.UI.Utils.Constant
import com.google.android.gms.common.api.Api
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitClient {

    private var instance: RetrofitClient? = null


    object RetrofitClient {
        fun getRetroInstance(): Retrofit {

            return Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        }
    }


}