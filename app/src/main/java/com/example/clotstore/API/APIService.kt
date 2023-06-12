package com.example.clotstore.API

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*

interface APIService {

    //USUARI

    @GET("/api/existeUsuari/")

    suspend fun existeixUsuari(@Query("nom") nom: String): Boolean
    @GET("/api/idUsuari/")
    suspend fun getIdUsuari(@Query("nom") nom: String): Int

    @POST("/api/usuari/")
    suspend fun afegeixUsuari(@Body usuari: Usuari): Response<Resposta>

    @GET("/api/loginUsuari/")
    suspend fun loguejaUsuari(
        @Query("nom") nom: String,
        @Query("contra") contra: String
    ): Response<Usuari>

    //PREFERENCES

    @GET("/api/preferenceUsuari/")
    suspend fun getPreferences(@Query("idUsuari") idUsuari: Int?): Response<Preference>?

    @POST("/api/preference/")
    suspend fun postPreferencia(
        @Body preference: Preference,
    ): Preference

   //FACTURA

    @GET("/api/factura/")
    suspend fun getProducteCarret(
        @Query("idCarret") idCarret: Int,
    ): Response<ProductesCarret>



    //CARRET

    @GET("/api/quantProCarret/")
    suspend fun getQuantitatCarret(
        @Query("idCarret") idCarret: Int,
        @Query("idProducte") idProducte: Int,
        @Query("nomTalla") nomTalla: String,
    ): Response<Int>

    @GET("/api/carretCrear/")
    suspend fun creaCarret(@Query("idUsuari") idUsuari: Int?): Response<Resposta>

    @GET("/api/idCarret/")
    suspend fun getIdCarret(@Query("idUsuari") idUsuari: Int?): Int

    @GET("/api/carret/")
    suspend fun getCarret(@Query("idCarret") idCarret: Int?): Carret

    @GET("/api/carretPreu/")
    suspend fun getCarretPreu(@Query("idCarret") idCarret: Int?): Double?

    @POST("/api/productecarret/")
    suspend fun afegeixProducteCarret(
        @Query("idCarret") idCarret: Int,
        @Query("idTallaProducte") idTallaProducte: Int,
        @Query("quantitat") quantitat: Int
    ): Response<Resposta>

    @PUT("/api/productecarret/")
    suspend fun putQuantitat(
        @Query("idCarret") idCarret: Int,
        @Query("idTallaProducte") idTallaProducte: Int,
        @Query("subir") subir: Boolean
    ): Boolean

    @DELETE("/api/productecarretAll/")
    suspend fun deleteCarret(@Query("idCarret") idCarret: Int?): Boolean

    //PRODUCTE

    @GET("/api/productes/")
    suspend fun getProductes(): Response<Productes>

    @GET("/api/mascaro/")
    suspend fun getProductesCars(): Response<Productes>

    @GET("/api/masbarato/")
    suspend fun getProductesBarats(): Response<Productes>

    @GET("/api/producteMarca/")
    suspend fun getProducteMarca(
        @Query("idMarca") idMarca: Int,
        @Query("idPrenda") idPrenda: Int
    ): Response<Productes>

    @GET("/api/producteTemporada/")
    suspend fun getProducteTemporada(
        @Query("idTemporada") idTemporada: Int,
        @Query("idPrenda") idPrenda: Int
    ): Response<Productes>

    @GET("/api/producteCategoria/")
    suspend fun getProducteCategoria(
        @Query("idCategoria") idCategoria: Int,
        @Query("idPrenda") idPrenda: Int
    ): Response<Productes>

    @GET("/api/producteEstil/")
    suspend fun getProducteEstil(
        @Query("idEstil") idEstil: Int,
        @Query("idPrenda") idPrenda: Int
    ): Response<Productes>

    @GET("/api/productePrenda/")
    suspend fun getProductePrenda(
        @Query("idPrenda") idPrenda: Int
    ): Response<Productes>

    @GET("/api/outfitFet/")
    suspend fun getOutfits(
        @Query("idPreferences") idPreferences: Int,
    ): Response<Productes?>

    @Multipart
    @POST("/api/producte/")
    suspend fun postProducte(
        @Query("nom") nom: String,
        @Query("preu") preu: Double,
        @Query("categoria") categoria: Int,
        @Query("prenda") prenda: Int,
        @Query("marca") marca: Int,
        @Query("temporada") temporada: Int,
        @Query("estil") estil: Int,
        @Part Archivo: MultipartBody.Part,
        @Query("nomArxiu") nomArxiu: String
    ): Producte

    @PUT("/api/producte/")
    suspend fun putProducte(
        @Query("idProducte") idProducte: Int,
        @Query("nom") nom: String,
        @Query("preu") preu: Double,
    ): Boolean

    @DELETE("/api/producte/")
    suspend fun borrarProducte(@Query("idProducte") idProducte: Int): Response<Boolean>

    //MARCA

    @GET("/api/marcas/")
    suspend fun getMarques(): Response<Marques>

    @GET("/api/marcaNom/")
    suspend fun getMarcaNom(@Query("nom") nom: String): Response<Marca>

    @GET("/api/marca/")
    suspend fun getMarca(@Query("id") codi: Int): Response<Marca>

    @Multipart
    @POST("/api/marca/")
    suspend fun postMarca(
        @Query("nom") nom: String,
        @Part Archivo: MultipartBody.Part,
        @Query("nomArxiu") nomArxiu: String
    ): Marca

    @DELETE("/api/marca/")
    suspend fun borrarMarca(@Query("idMarca") idMarca: Int): Response<Boolean>


    //TALLA

    @GET("/api/filtreTallas/")
    suspend fun getTallas(@Query("isNumber") isNumber: Boolean): Response<Talles>

    @GET("/api/tallaproducte/")
    suspend fun getTallaProducte(
        @Query("idProducte") idProducte: Int,
        @Query("nomTalla") nomTalla: String,
    ): Response<Int>

    @GET("/api/idtallaproducte/")
    suspend fun getIdTallaProducte(
        @Query("idProducte") idProducte: Int,
        @Query("nomTalla") nomTalla: String,
    ): Response<Int>

    @GET("/api/existenciesTallaProducte/")
    suspend fun getExistenciesTallaProducte(
        @Query("idTallaProducte") idTallaProducte: Int,
    ): Response<Int>

    @POST("/api/tallaproducte/")
    suspend fun postTallaProducte(
        @Query("idProducte") idProducte: Int,
        @Query("nomTalla") nomTalla: String,
        @Query("existencias") existencias: Int,
    ): Boolean

    //CATEGORIA

    @GET("/api/categoriaNom/")
    suspend fun getCategoriaNom(@Query("nom") nom: String): Response<Categoria>

    @GET("/api/categorias/")
    suspend fun getCategorias(): Response<Categories>

    @GET("/api/categoria/")
    suspend fun getCategoria(@Query("codi") codi: Int): Response<Categoria>

    //PRENDA

    @GET("/api/prenda/")
    suspend fun getPrenda(@Query("codi") codi: Int): Response<Prenda>

    @GET("/api/prendaNom/")
    suspend fun getPrendaNom(@Query("nom") nom: String): Response<Prenda>

    @GET("/api/prendas/")
    suspend fun getPrendes(): Response<Prendes>

    //ESTIL

    @GET("/api/estilNom/")
    suspend fun getEstilNom(@Query("nom") nom: String): Response<Estil>

    @GET("/api/estils/")
    suspend fun getEstils(): Response<Estils>

    @GET("/api/estil/")
    suspend fun getEstil(@Query("codi") codi: Int): Response<Estil>

    //TEMPORADA

    @GET("/api/temporadaNom/")
    suspend fun getTemporadaNom(@Query("nom") nom: String): Response<Temporada>

    @GET("/api/temporadas/")
    suspend fun getTemporadas(): Response<Temporades>

    @GET("/api/temporada/")
    suspend fun getTemporada(@Query("codi") codi: Int): Response<Temporada>

}