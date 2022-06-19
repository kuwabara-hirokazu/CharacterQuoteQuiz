package com.example.characterquotequiz.data.remote

import com.example.characterquotequiz.BuildConfig
import com.example.characterquotequiz.data.entity.DeepLResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface DeepLApi {

    @FormUrlEncoded
    @POST("translate")
    suspend fun translate(
        @Field("auth_key") authKey: String = BuildConfig.DEEPL_API_KEY,
        @Field("text") text: String,
        @Field("target_lang") targetLang: String
    ): DeepLResponse
}