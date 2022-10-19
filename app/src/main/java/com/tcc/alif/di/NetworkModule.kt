package com.tcc.alif.di

import android.app.Application
import android.content.Context
import com.tcc.alif.data.api.AlifService
import com.tcc.alif.data.api.CepService
import com.tcc.alif.data.util.Constants.API_BASE_URL
import com.tcc.alif.data.util.Constants.API_CEP_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Singleton

private const val READ_TIMEOUT = 30L
private const val CONNECT_TIMEOUT = 30L

@Qualifier annotation class ApiRetrofit
@Qualifier annotation class CepRetrofit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    fun providesHttpInterceptor(): Interceptor {
        return Interceptor {
            val request = it.request().newBuilder()
            val actualRequest = request.build()
            it.proceed(actualRequest)
        }
    }

    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(providesHttpInterceptor())
            .addInterceptor(providesLoggingInterceptor())
            .build()
    }

    @Singleton
    @Provides
    @ApiRetrofit
    fun provideRetrofitInstance(
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    @CepRetrofit
    fun provideCepInstanceApi(
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(API_CEP_URL)
            .addConverterFactory(gsonConverterFactory)
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideMovieApiService(@ApiRetrofit retrofit: Retrofit): AlifService {
        return retrofit.create(AlifService::class.java)
    }

    @Singleton
    @Provides
    fun provideCepService(@CepRetrofit retrofit: Retrofit): CepService {
        return retrofit.create(CepService::class.java)
    }

    @Singleton
    @Provides
    @Named("AppContext")
    fun provideContext(application: Application): Context? {
        return application.applicationContext
    }
}