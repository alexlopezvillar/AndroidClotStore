package com.example.clotstore.API


import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.clotstore.RegistreLogin.IniciarSessio
import com.example.clotstore.databinding.IniciarSessioBinding
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.lang.reflect.InvocationTargetException
import java.net.SocketTimeoutException

class ErrorInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()

        try{

            val response = chain.proceed(request)
            val bodyString = response.body!!.string()

            return response.newBuilder()
                .body(bodyString.toResponseBody(response.body?.contentType()))
                .build()

        } catch(e : java.lang.Exception){
            var interceptorCode = 0
            when(e){

                is SocketTimeoutException -> {
                    Log.e("Error", "SocketTimeoutException")
                    interceptorCode = 408
                }
                is InvocationTargetException -> Log.e("Error", "InvocationTargetException")
            }

            return Response.Builder()
                .request(request)
                .protocol(Protocol.HTTP_1_1)
                .code(interceptorCode)
                .message("Error webService")
                .body("{${e}}".toResponseBody(null)).build()

        }


    }


}