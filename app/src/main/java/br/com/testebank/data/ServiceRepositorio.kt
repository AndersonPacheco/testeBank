package br.com.testebank.data

import android.text.TextUtils
import br.com.testebank.util.Constantes
import okhttp3.Credentials
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceRepositorio {
    private val httpClient = OkHttpClient.Builder()

    private val builder = Retrofit.Builder()
        .addConverterFactory(getGson())
        .baseUrl(Constantes.URL)
        .client(getClient())

    private var retrofit = builder.build()

    fun <S> createService(serviceClass: Class<S>): S {
        return createService(serviceClass, null, null)
    }

    fun <S> createService(serviceClass: Class<S>, username: String?, password: String?): S {
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            val authToken = Credentials.basic(username, password)
            return createService(serviceClass, authToken)
        }

        return createService(serviceClass, null)
    }

    fun <S> createService(serviceClass: Class<S>, authToken: String?): S {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(httpLoggingInterceptor)

        builder.client(httpClient.build())
        retrofit = builder.build()

        return retrofit.create(serviceClass)
    }

    private fun getClient(): OkHttpClient {
        return OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)
            .build()
    }

    private fun getGson(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }
}