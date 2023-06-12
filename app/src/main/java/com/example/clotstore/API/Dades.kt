package com.example.clotstore.API

import android.content.Context
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


data class Resposta(
    val missatge: String
)

//data class UploadResponse(
//    val error: Boolean,
//    val image: String
//)

val iniciUrlDocument = "/external_files"
val iniciUrl = "/storage/emulated/0"
const val REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE = 1

data class Usuari(
    var idUsuari: Int?,
    var nom: String,
    var contra: String,
    var tipus: String,

    )

data class Carret(
    var idCarret: Int,
    var PreuTotal: Double?,
    var idUsuari: Int?,

    )

data class Preference(
    var idPreferences: Int,
    var temporada: Int?,
    var estil: Int?,
    var categoria: Int?,
    var idUsuari: Int?,
    var idColor: Int?,
)

data class Marca(
    var idMarca: Int,
    var nom: String,
    var imatge: String,
)

data class Talla(
    var id: Int,
    var nom: String,
)

data class Prenda(
    var idPrenda: Int,
    var nom: String,
    var imatge: String,
)

data class Temporada(
    var idTemporada: Int,
    var nom: String,
    var imatge: String,
)


data class Estil(
    var idEstil: Int,
    var nom: String,
    var imatge: String,
)

data class Categoria(
    var idCategoria: Int,
    var nom: String,
    var imatge: String,
)

data class Producte(
    var idProducte: Int,
    var nom: String,
    var preu: Double,
    var imatge: String,
    var categoria: Int,
    var prenda: Int,
    var marca: Int,
    var temporada: Int,
    var estil: Int,
)

data class ProducteFactura(
    var idProducte: Int,
    var idTallaProducte: Int,
    var nom: String,
    var preu: Double,
    var imatge: String,
    var quantitat: Int,
)

class ProductesCarret : ArrayList<ProducteFactura>()
class Productes : ArrayList<Producte>()
class Marques : ArrayList<Marca>()
class Prendes : ArrayList<Prenda>()
class Temporades : ArrayList<Temporada>()
class Estils : ArrayList<Estil>()
class Categories : ArrayList<Categoria>()
class Talles : ArrayList<Talla>()


var preferencesUsuari: Preference? = Preference(0, 0, 0, 0, 0, 0)
var usuariLogejat: Usuari = Usuari(0, "", "", "")
var carret: Carret = Carret(0, 0.0, 0)

var outfit1: ArrayList<Producte>? = null


lateinit var llistaProductes: ArrayList<Producte>
lateinit var llistaSamarretes: ArrayList<Producte>
lateinit var llistaPantalo: ArrayList<Producte>
lateinit var llistaCalcat: ArrayList<Producte>
lateinit var llistaJaquetes: ArrayList<Producte>
lateinit var llistaVestits: ArrayList<Producte>

lateinit var prductesPrendes: ArrayList<Producte>
lateinit var llistaProductesCarret: ArrayList<ProducteFactura>

lateinit var llistaMarques: ArrayList<Marca>
lateinit var llistaPrendes: ArrayList<Prenda>
lateinit var llistaTemporades: ArrayList<Temporada>
lateinit var llistaEstils: ArrayList<Estil>
lateinit var llistaCategories: ArrayList<Categoria>
lateinit var llistaTallas: ArrayList<Talla>