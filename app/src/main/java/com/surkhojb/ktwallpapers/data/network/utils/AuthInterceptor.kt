package com.surkhojb.ktwallpapers.data.network.utils

import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.HttpException
import java.lang.Exception

//IMPORTANT: This is a demo app, you MUSTN'T add api keys in source code.
private const val API_KEY = "Authorization"
private const val API_AUTH_KEY = "ADD_YOUR_PEXELS_API_KEY_HERE"

class AuthInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        val headers = request.headers.newBuilder().add(API_KEY,API_AUTH_KEY).build()
        request = request.newBuilder().headers(headers).build()

        return chain.proceed(request)

    }
}

fun HttpException.errorByCode(code: Int): Throwable {
    return when(code){
        400 ->  Exception("Invalid request")
        404 ->  Exception("Resource not found")
        429 ->  Exception("API rate limit exceeded")
        500 ->  Exception("Server error")
        else -> Exception("Unknown exception")
    }
}