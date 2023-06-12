package com.example.clotstore.Adaptadors

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.clotstore.API.*
import com.example.clotstore.CarretFactura.Factura
import com.example.clotstore.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AdaptadorCarret(
    var llista: ArrayList<ProducteFactura>,
    var tv: TextView,
    var buton: AppCompatButton,
    var img:ImageView
) :
    RecyclerView.Adapter<AdaptadorCarret.ViewHolder>() {
    class ViewHolder(val vista: View) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(vista) {
        val imatge = vista.findViewById<ImageView>(R.id.imageCarret)
        val nom = vista.findViewById<TextView>(R.id.nomCarret)
        var preu = vista.findViewById<TextView>(R.id.preuCarret)
        var quant = vista.findViewById<TextView>(R.id.quantitatCarret)
        val mes = vista.findViewById<FloatingActionButton>(R.id.btMes)
        val menys = vista.findViewById<FloatingActionButton>(R.id.btMenys)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context)
        var vh: ViewHolder? = null

        vh = ViewHolder(layout.inflate(R.layout.cardview_carret, parent, false))


        return vh!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nom.setText(llista[position].nom)
        holder.preu.setText(String.format("%.2f", llista[position].preu) + "€")
        holder.quant.setText("Quantitat: x" + llista[position].quantitat.toString())
        Glide.with(holder.vista.context).load(llista[position].imatge).into(holder.imatge)
        if (llista.count() == 0) {
            buton.visibility = View.INVISIBLE
            img.visibility = View.VISIBLE
            tv.setText("El teu carret es buit!")
        } else {
            buton.visibility = View.VISIBLE
            img.visibility = View.INVISIBLE
            tv.setText(
                "Preu Total: " + String.format(
                    "%.2f",
                    carret.PreuTotal
                ) + "€"
            )
        }


        holder.mes.setOnClickListener() {
            runBlocking {
                val corrutina = launch {
                    val crudApi = CRUDApi()
                    var existencies: Int = 0
                    existencies =
                        crudApi.getExistenciesTallaProducte(llista[position].idTallaProducte)
                    if (existencies >= llista[position].quantitat + 1) {
                        crudApi.putQuantitat(
                            carret.idCarret,
                            llista[position].idTallaProducte,
                            true
                        )
                        llistaProductesCarret = crudApi.getProductesCarret(carret.idCarret)
                        updateLlista(llistaProductesCarret)
                        carret = crudApi.getCarret(carret.idCarret)
                        carret.PreuTotal = crudApi.getCarretPreu(carret.idCarret)
                        if (llista.count() == 0) {
                            img.visibility = View.INVISIBLE
                            buton.visibility = View.VISIBLE
                            tv.setText("El teu carret es buit!")
                        } else {
                            buton.visibility = View.VISIBLE
                            img.visibility = View.INVISIBLE
                            tv.setText(
                                "Preu Total: " + String.format(
                                    "%.2f",
                                    carret.PreuTotal
                                ) + "€"
                            )
                        }
                    } else {
                        Toast.makeText(
                            holder.vista.context, "No queden més talles per aquest producte",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
                corrutina.join()
            }


        }

        holder.menys.setOnClickListener() {
            runBlocking {
                val corrutina = launch {
                    val crudApi = CRUDApi()
                    crudApi.putQuantitat(carret.idCarret, llista[position].idTallaProducte, false)
                    llistaProductesCarret = crudApi.getProductesCarret(carret.idCarret)
                    updateLlista(llistaProductesCarret)
                    carret = crudApi.getCarret(carret.idCarret)
                    carret.PreuTotal = crudApi.getCarretPreu(carret.idCarret)
                    if (llista.count() == 0) {
                        buton.visibility = View.INVISIBLE
                        img.visibility = View.VISIBLE
                        tv.setText("El teu carret es buit!")
                    } else {
                        buton.visibility = View.VISIBLE
                        img.visibility = View.INVISIBLE
                        tv.setText(
                            "Preu Total: " + String.format(
                                "%.2f",
                                carret.PreuTotal
                            ) + "€"
                        )
                    }
                }
                corrutina.join()
            }
        }

        buton.setOnClickListener() {

            runBlocking {
                val corrutina = launch {
                    val crudApi = CRUDApi()
                    crudApi.deleteCarret(carret.idCarret)


                    val intent = Intent(holder.vista.context, Factura::class.java)
                    ContextCompat.startActivity(holder.vista.context, intent, null)

                    Toast.makeText(
                        holder.vista.context,
                        "Compra realitzada correcament",
                        Toast.LENGTH_SHORT
                    ).show()

                }

                corrutina.join()
            }
        }
    }

    fun updateLlista(llistanova: ArrayList<ProducteFactura>) {
        llista = llistanova
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int = llista.size
}