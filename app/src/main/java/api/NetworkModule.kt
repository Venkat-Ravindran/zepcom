package api

import android.annotation.SuppressLint
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSession
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager


class NetworkModule() {
    var gson = GsonBuilder()
        .setLenient()
        .create()
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .client(getUnsafeOkHttpClient())
            //.client(clientBuilder.build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("https://www.jsonkeeper.com/b/5BEJ/")
            .build()
    }

    fun provideApi(): Api {
        return provideRetrofit().create(Api::class.java)
    }

    fun getUnsafeOkHttpClient(): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
        clientBuilder.connectTimeout(20, TimeUnit.SECONDS)
        clientBuilder.readTimeout(120, TimeUnit.SECONDS)
        clientBuilder.writeTimeout(20, TimeUnit.SECONDS)

        val interceptors = provideInterceptors()
        interceptors.forEach { _ ->
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            clientBuilder.addInterceptor(interceptor)
        }

        var okHttpClient: OkHttpClient = clientBuilder.build()
        okHttpClient = getSelfSignedSocket(okHttpClient)
        return okHttpClient
    }

    private fun provideInterceptors(): ArrayList<Interceptor> {
        val interceptors = arrayListOf<Interceptor>()
        val keyInterceptor = Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Content-Type", "application/json")
                //.addHeader("Host", "sync-service")
                .build()
            return@Interceptor chain.proceed(request)
        }
        interceptors.add(keyInterceptor)
        return interceptors
    }

    private fun getSelfSignedSocket(okHttpClient: OkHttpClient): OkHttpClient {

        var hc = okHttpClient
        val clientBuilder: OkHttpClient.Builder = hc.newBuilder()
        clientBuilder.connectTimeout(20, TimeUnit.SECONDS)
        clientBuilder.readTimeout(120, TimeUnit.SECONDS)
        clientBuilder.writeTimeout(20, TimeUnit.SECONDS)

        try {
            val trustAllCerts = arrayOf<TrustManager>(
                @SuppressLint("CustomX509TrustManager")
                object : X509TrustManager {
                    @SuppressLint("TrustAllX509TrustManager")
                    override fun checkClientTrusted(
                        chain: Array<X509Certificate?>?,
                        authType: String?
                    ) { }

                    @SuppressLint("TrustAllX509TrustManager")
                    override fun checkServerTrusted(
                        chain: Array<X509Certificate?>?,
                        authType: String?
                    ) { }

                    override fun getAcceptedIssuers(): Array<X509Certificate?> {
                        return arrayOf()
                    }
                }
            )

            // Install the all-trusting trust manager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())

            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory = sslContext.socketFactory
            clientBuilder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            val hostnameVerifier =
                HostnameVerifier { _: String?, _: SSLSession? -> true }
            clientBuilder.hostnameVerifier(hostnameVerifier)
            hc = clientBuilder.build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
        return hc
    }


}
