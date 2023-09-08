package com.example.test.network.interfaces

import com.example.test.network.ApiMember
import com.example.test.network.ApiMemberContainers
import com.example.test.network.ApiVirtualMachine
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory.*
import retrofit2.http.GET
import retrofit2.http.Path
import java.security.SecureRandom
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


private var API_BASE_URL = "http://10.0.2.2:5000/api/"

// Create Moshi object
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

// An OkHttp interceptor which logs request and response information
private val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

private fun createInsecureOkHttpClient(): OkHttpClient {
    val trustAllCertificates = arrayOf<TrustManager>(
        object : X509TrustManager {
            override fun checkClientTrusted(
                chain: Array<out java.security.cert.X509Certificate>?,
                authType: String?
            ) {
            }

            override fun checkServerTrusted(
                chain: Array<out java.security.cert.X509Certificate>?,
                authType: String?
            ) {
            }

            override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> = arrayOf()
        }
    )

    val sslContext = SSLContext.getInstance("SSL")
    sslContext.init(null, trustAllCertificates, SecureRandom())

    val sslSocketFactory = sslContext.socketFactory

    return OkHttpClient.Builder()
        .sslSocketFactory(sslSocketFactory, trustAllCertificates[0] as X509TrustManager)
        .hostnameVerifier { hostname, session -> true }
        .addInterceptor(logger)
        .build()
}


interface MemberApiService {


    @GET("member")
    fun getAllMembers(): Deferred<ApiMemberContainers>

    @GET("member/{id}")
    fun getMemberById(@Path("id") role: String): Deferred<List<ApiMember>>

    @GET("member/{id}/vms")
    fun getMemberVmsById(@Path("id") role: String): Deferred<List<ApiVirtualMachine>>
}


private val retrofit = Retrofit.Builder()
    .addConverterFactory(create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(API_BASE_URL)
    .client(createInsecureOkHttpClient())
    .build()

object ApiMemberObj {
    // Lazy properties with delegate: thread safe, can only be initialized once at a time. Adds extra safety to our 1 instance we need
    val retrofitService: MemberApiService by lazy {
        retrofit.create(MemberApiService::class.java)
    }
}