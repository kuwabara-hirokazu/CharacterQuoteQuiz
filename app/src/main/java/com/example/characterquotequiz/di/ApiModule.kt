package com.example.characterquotequiz.di

import com.example.characterquotequiz.data.remote.AnimeApi
import com.example.characterquotequiz.data.remote.DeepLApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    private const val ANIME_URL = "https://animechan.vercel.app/api/"
    private const val DEEPL_URL = "https://api-free.deepl.com/v2/"

    @Provides
    fun provideOkHttp(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    @Provides
    @Named("AnimeService")
    fun provideAnimeService(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ANIME_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideAnimeApi(@Named("AnimeService") retrofit: Retrofit): AnimeApi {
        return retrofit.create(AnimeApi::class.java)
    }

    @Provides
    @Named("DeepLService")
    fun provideDeepLService(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(DEEPL_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideDeepLApi(@Named("DeepLService") retrofit: Retrofit): DeepLApi {
        return retrofit.create(DeepLApi::class.java)
    }
}