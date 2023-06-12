package com.example.clotstore.Adaptadors

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.clotstore.API.Producte

import com.example.clotstore.FitxaProducte.FitxaProducte
import com.example.clotstore.R

class AdaptadorProductesInici(var llista: ArrayList<Producte>) :
    RecyclerView.Adapter<AdaptadorProductesInici.ViewHolder>() {
    class ViewHolder(val vista: View) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(vista) {
        val imatge = vista.findViewById<ImageView>(R.id.image)
        val nom = vista.findViewById<TextView>(R.id.tvnom)
        val preu = vista.findViewById<TextView>(R.id.tvpreu)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context)
        var vh: ViewHolder? = null
        vh = ViewHolder(layout.inflate(R.layout.cardview_producte, parent, false))
        return vh!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nom.setText(llista[position].nom)
        holder.preu.setText(llista[position].preu.toString() + " €")
        Glide.with(holder.vista.context)
            .load(llista[position].imatge)
            .into(holder.imatge)



        holder.vista.setOnClickListener {
            val intent = Intent(holder.vista.context, FitxaProducte::class.java)
            intent.putExtra("id", llista[position].idProducte)
            intent.putExtra("nom", llista[position].nom)
            intent.putExtra("preu", llista[position].preu)
            intent.putExtra("estil", llista[position].estil)
            intent.putExtra("marca", llista[position].marca)
            intent.putExtra("categoria", llista[position].categoria)
            intent.putExtra("temporada", llista[position].temporada)
            intent.putExtra("prenda", llista[position].prenda)
            intent.putExtra("imatge", llista[position].imatge)
            ContextCompat.startActivity(holder.vista.context, intent, null)
        }
    }

    fun updateLlista(llistanova: ArrayList<Producte>) {
        llista = llistanova
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int = llista.size
}