package com.neobis.fms.network


import com.neobis.fms.MyApplication
import com.neobis.fms.utilits.BASE_URL
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request

import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitInstance {


    companion object {
        lateinit var request: Request

        private val retrofit by lazy {
            val pref = MyApplication.getContext()?.let { SessionManager(it) }

            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client =
                OkHttpClient.Builder().addInterceptor(Interceptor { chain ->
                    request = chain.request()
                    if (pref?.fetchUserToken() != null) {
                        request = request.newBuilder()
                            .addHeader("Authorization", "Bearer ${pref.fetchUserToken()}")
                            .build()

                    }


                    chain.proceed(request)
                }).addInterceptor(logging).build()

            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }
        val api by lazy {
            retrofit.create(Api::class.java)
        }
    }

}