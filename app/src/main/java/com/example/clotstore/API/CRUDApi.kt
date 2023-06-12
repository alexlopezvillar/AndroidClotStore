package com.example.clotstore.API

import android.net.Uri
import android.os.Build
import android.text.TextUtils
import android.util.Log
import androidx.viewbinding.BuildConfig
import com.google.gson.GsonBuilder
import kotlinx.coroutines.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import kotlin.coroutines.CoroutineContext


class CRUDApi() : CoroutineScope {
    val urlapi = "http://172.16.24.115:45455/"


    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private fun getRetrofit(): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder().baseUrl(urlapi).client(getClient())
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
    }

    fun OkHttpClient.Builder.ignoreAllSSLErrors(): OkHttpClient.Builder {
        val naiveTrustManager = object : X509TrustManager {
            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            override fun checkClientTrusted(certs: Array<X509Certificate>, authType: String) = Unit
            override fun checkServerTrusted(certs: Array<X509Certificate>, authType: String) = Unit
        }

        val insecureSocketFactory = SSLContext.getInstance("TLSv1.2").apply {
            val trustAllCerts = arrayOf<TrustManager>(naiveTrustManager)
            init(null, trustAllCerts, SecureRandom())
        }.socketFactory

        sslSocketFactory(insecureSocketFactory, naiveTrustManager)
        hostnameVerifier(HostnameVerifier { _, _ -> true })
        return this
    }

    private fun getClient(): OkHttpClient {
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder().apply {
            addInterceptor(ErrorInterceptor())
            addInterceptor(logging)
            if (BuildConfig.DEBUG) //if it is a debug build ignore ssl errors
                ignoreAllSSLErrors()
        }.build()
    }

    //Productes
    suspend fun getOutfits(idPreferences: Int): Productes {
        val response =
            getRetrofit().create(APIService::class.java).getOutfits(idPreferences).body()
        return response!!
    }

    suspend fun getAllProductes(): Productes {
        val response = getRetrofit().create(APIService::class.java).getProductes().body()
        return response!!
    }

    suspend fun getAllProductesCars(): Productes {
        val response = getRetrofit().create(APIService::class.java).getProductesCars().body()
        return response!!
    }

    suspend fun getAllProductesBarats(): Productes {
        val response = getRetrofit().create(APIService::class.java).getProductesBarats().body()
        return response!!
    }

    suspend fun getProductesMarca(idMarca: Int, idPrenda: Int): Productes {
        val response =
            getRetrofit().create(APIService::class.java).getProducteMarca(idMarca, idPrenda).body()
        return response!!
    }

    suspend fun getProductesTemporada(idTemporada: Int, idPrenda: Int): Productes {
        val response =
            getRetrofit().create(APIService::class.java).getProducteTemporada(idTemporada, idPrenda)
                .body()
        return response!!
    }

    suspend fun getProductesCategoria(idCategoria: Int, idPrenda: Int): Productes {
        val response =
            getRetrofit().create(APIService::class.java).getProducteCategoria(idCategoria, idPrenda)
                .body()
        return response!!
    }

    suspend fun getProductesPrenda(idPrenda: Int): Productes {
        val response =
            getRetrofit().create(APIService::class.java).getProductePrenda(idPrenda).body()
        return response!!
    }

    suspend fun getProductesEstil(idEstil: Int, idPrenda: Int): Productes {
        val response =
            getRetrofit().create(APIService::class.java).getProducteEstil(idEstil, idPrenda).body()
        return response!!
    }

    fun postProducte(
        nom: String,
        preu: Double,
        categoria: Int,
        prenda: Int,
        marca: Int,
        temporada: Int,
        estil: Int,
        rutaArxiu: String,
        nomArxiu: String
    ): Producte? {
        Log.i("nomArxiu", rutaArxiu)

        var novarutaArxiu = ""

        if (rutaArxiu?.contains("storage") == true) {
            novarutaArxiu =
                TextUtils.substring(
                    rutaArxiu,
                    TextUtils.indexOf(rutaArxiu, "/storage"),
                    rutaArxiu!!.length
                )
        } else {
            novarutaArxiu =
                rutaArxiu?.replace(iniciUrlDocument, iniciUrl).toString()
        }

        //urlImatge = Uri.parse(novarutaArxiu)
        Log.i("urlImatge", novarutaArxiu)


        val file = File(novarutaArxiu)
        Log.i("nomArxiu", novarutaArxiu)
        if (!file.exists()) {
            Log.i("Error", "La ruta " + novarutaArxiu + " no existeix")
            return null
        } else {
            val photo: RequestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("Archivo", file.getName(), photo)

            var response: Producte? = null
            runBlocking {
                val corrutina = launch {
                    response =
                        getRetrofit().create(APIService::class.java).postProducte(
                            nom,
                            preu,
                            categoria,
                            prenda,
                            marca,
                            temporada,
                            estil,
                            body,
                            nomArxiu
                        )!!
                }
                corrutina.join()
            }
            return response
        }
    }

    suspend fun putProducte(idProducte: Int, nom: String, preu: Double): Boolean {
        val call =
            getRetrofit().create(APIService::class.java).putProducte(idProducte, nom, preu)
        return call
    }

    suspend fun deleteProducte(idProducte: Int): Boolean {
        val call = getRetrofit().create(APIService::class.java).borrarProducte(idProducte)
        return call.isSuccessful
    }

    //Marques
    suspend fun getMarcaNom(nom: String): Marca {
        val response = getRetrofit().create(APIService::class.java).getMarcaNom(nom).body()
        return response!!
    }

    suspend fun getAllMarques(): Marques {
        val response = getRetrofit().create(APIService::class.java).getMarques().body()
        return response!!
    }

    suspend fun getMarcaId(codi: Int): Marca {
        val response = getRetrofit().create(APIService::class.java).getMarca(codi).body()
        return response!!
    }

    fun postMarca(
        nom: String,
        rutaArxiu: String,
        nomArxiu: String
    ): Marca? {
        Log.i("nomArxiu", rutaArxiu)

        var novarutaArxiu = ""

        if (rutaArxiu?.contains("storage") == true) {
            novarutaArxiu =
                TextUtils.substring(
                    rutaArxiu,
                    TextUtils.indexOf(rutaArxiu, "/storage"),
                    rutaArxiu!!.length
                )
        } else {
            novarutaArxiu =
                rutaArxiu?.replace(iniciUrlDocument, iniciUrl).toString()
        }

        //urlImatge = Uri.parse(novarutaArxiu)
        Log.i("urlImatge", novarutaArxiu)
        val file = File(novarutaArxiu)
        Log.i("nomArxiu", novarutaArxiu)
        if (!file.exists()) {
            Log.i("Error", "La ruta " + novarutaArxiu + " no existeix")
            return null
        } else {
            val photo: RequestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("Archivo", file.getName(), photo)

            var response: Marca? = null
            runBlocking {
                val corrutina = launch {
                    response =
                        getRetrofit().create(APIService::class.java).postMarca(
                            nom,
                            body,
                            nomArxiu
                        )!!
                }
                corrutina.join()
            }
            return response
        }
    }

    suspend fun deleteMarca(idMarca: Int): Boolean {
        val call = getRetrofit().create(APIService::class.java).borrarMarca(idMarca)
        return call.isSuccessful
    }

    //Categories
    suspend fun getCategoriaNom(nom: String): Categoria {
        val response = getRetrofit().create(APIService::class.java).getCategoriaNom(nom).body()
        return response!!
    }

    suspend fun getAllCategories(): Categories {
        val response = getRetrofit().create(APIService::class.java).getCategorias().body()
        return response!!
    }

    suspend fun getCategoriaId(codi: Int): Categoria {
        val response = getRetrofit().create(APIService::class.java).getCategoria(codi).body()
        return response!!
    }

    //Temporades
    suspend fun getTemporadaNom(nom: String): Temporada {
        val response = getRetrofit().create(APIService::class.java).getTemporadaNom(nom).body()
        return response!!
    }

    suspend fun getAllTeporades(): Temporades {
        val response = getRetrofit().create(APIService::class.java).getTemporadas().body()
        return response!!
    }

    suspend fun getTemporadaId(codi: Int): Temporada {
        val response = getRetrofit().create(APIService::class.java).getTemporada(codi).body()
        return response!!
    }

    //Estils
    suspend fun getEstilNom(nom: String): Estil {
        val response = getRetrofit().create(APIService::class.java).getEstilNom(nom).body()
        return response!!
    }

    suspend fun getAllEstils(): Estils {
        val response = getRetrofit().create(APIService::class.java).getEstils().body()
        return response!!
    }

    suspend fun getEstilId(codi: Int): Estil {
        val response = getRetrofit().create(APIService::class.java).getEstil(codi).body()
        return response!!
    }

    //Prendes
    suspend fun getPrendaNom(nom: String): Prenda {
        val response = getRetrofit().create(APIService::class.java).getPrendaNom(nom).body()
        return response!!
    }

    suspend fun getAllPrendes(): Prendes {
        val response = getRetrofit().create(APIService::class.java).getPrendes().body()
        return response!!
    }

    suspend fun getPrendaId(codi: Int): Prenda {
        val response = getRetrofit().create(APIService::class.java).getPrenda(codi).body()
        return response!!
    }

    //Carret
    suspend fun getProductesCarret(idCarret: Int): ProductesCarret {
        val response =
            getRetrofit().create(APIService::class.java).getProducteCarret(idCarret).body()
        return response!!
    }

    suspend fun creaCarret(idUsuari: Int?): Boolean {
        val call = getRetrofit().create(APIService::class.java).creaCarret(idUsuari)
        return call.isSuccessful
    }

    suspend fun getIdCarret(idUsuari: Int?): Int {
        val call = getRetrofit().create(APIService::class.java).getIdCarret(idUsuari)
        return call
    }

    suspend fun getCarret(idCarret: Int?): Carret {
        val call = getRetrofit().create(APIService::class.java).getCarret(idCarret)
        return call
    }

    suspend fun getCarretPreu(idCarret: Int?): Double? {
        val call = getRetrofit().create(APIService::class.java).getCarretPreu(idCarret)
        return call
    }

    suspend fun afegeixProducteCarret(
        idCarret: Int,
        idTallaProducte: Int,
        quantitat: Int
    ): Boolean {
        val call = getRetrofit().create(APIService::class.java)
            .afegeixProducteCarret(idCarret, idTallaProducte, quantitat)
        return call.isSuccessful
    }

    suspend fun getQuantitatCarret(idCarret: Int, idProducte: Int, nomTalla: String): Int {
        val response =
            getRetrofit().create(APIService::class.java)
                .getQuantitatCarret(idCarret, idProducte, nomTalla).body()
        return response!!
    }

    suspend fun putQuantitat(idCarret: Int, idTallaProducte: Int, subir: Boolean): Boolean {
        val call =
            getRetrofit().create(APIService::class.java)
                .putQuantitat(idCarret, idTallaProducte, subir)
        return call
    }

    suspend fun deleteCarret(idCarret: Int): Boolean {
        val call =
            getRetrofit().create(APIService::class.java).deleteCarret(idCarret)
        return call
    }

    //Usuari
    suspend fun afegeixUsuari(usuari: Usuari): Boolean {
        val call = getRetrofit().create(APIService::class.java).afegeixUsuari(usuari)
        return call.isSuccessful
    }

    suspend fun existeixUsuari(nom: String): Boolean {
        val call = getRetrofit().create(APIService::class.java).existeixUsuari(nom)
        return call
    }

    suspend fun autenticaUsuari(nom: String, contra: String): Usuari? {
        val call = getRetrofit().create(APIService::class.java).loguejaUsuari(nom, contra)
        return call.body()
    }

    suspend fun getIdUsuari(nom: String): Int {
        val call = getRetrofit().create(APIService::class.java).getIdUsuari(nom)
        return call
    }

    //Talles
    suspend fun getTallaProducte(idProducte: Int, nomTalla: String): Int {
        val response =
            getRetrofit().create(APIService::class.java).getTallaProducte(idProducte, nomTalla)
                .body()
        return response!!
    }

    suspend fun getIdTallaProducte(idProducte: Int, nomTalla: String): Int {
        val response =
            getRetrofit().create(APIService::class.java).getIdTallaProducte(idProducte, nomTalla)
                .body()
        return response!!
    }

    suspend fun getExistenciesTallaProducte(idTallaProducte: Int): Int {
        val response =
            getRetrofit().create(APIService::class.java)
                .getExistenciesTallaProducte(idTallaProducte).body()
        return response!!
    }

    suspend fun getPreferences(idUsuari: Int?): Preference? {
        var response =
            getRetrofit().create(APIService::class.java).getPreferences(idUsuari)!!.body()
        Log.i("resposta preferencies", response.toString())
        if (response == null) {
            response = Preference(0, 0, 0, 0, 0, 0)
            return response!!
        } else {
            return response!!
        }
    }

    suspend fun postPreferences(preference: Preference): Preference {
        val call =
            getRetrofit().create(APIService::class.java).postPreferencia(preference)
        return call
    }

    suspend fun postTallaPorducte(idProducte: Int, nomTalla: String, existencias: Int): Boolean {
        val call =
            getRetrofit().create(APIService::class.java)
                .postTallaProducte(idProducte, nomTalla, existencias)
        return call
    }

    suspend fun getTallas(isNumber: Boolean): Talles {
        val response = getRetrofit().create(APIService::class.java).getTallas(isNumber).body()
        return response!!
    }
}
