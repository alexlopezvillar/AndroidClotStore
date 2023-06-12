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
import com.example.clotstore.Administrador.ModificarProducte
import com.example.clotstore.R

class AdaptadorModificarProducte(var llista: ArrayList<Producte>) :
    RecyclerView.Adapter<AdaptadorModificarProducte.ViewHolder>() {
    class ViewHolder(val vista: View) : RecyclerView.ViewHolder(vista) {
        val imatge = vista.findViewById<ImageView>(R.id.image)
        val nom = vista.findViewById<TextView>(R.id.tvnom)
        val preu = vista.findViewById<TextView>(R.id.tvpreu)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context)
        return ViewHolder(layout.inflate(R.layout.cardview_producte, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nom.setText(llista[position].nom)
        holder.preu.setText(llista[position].preu.toString() + " €")
        Glide.with(holder.vista.context).load(llista[position].imatge).into(holder.imatge)
        holder.vista.setOnClickListener {
            val intent = Intent(holder.vista.context, ModificarProducte::class.java)
            intent.putExtra("id", llista[position].idProducte)
            intent.putExtra("nom", llista[position].nom)
            intent.putExtra("preu", llista[position].preu)
            intent.putExtra("imatge", llista[position].imatge)
            intent.putExtra("prenda", llista[position].prenda)
            ContextCompat.startActivity(holder.vista.context, intent, null)
    }}

    override fun getItemCount() = llista.size

    fun updateLlista(llistanova: ArrayList<Producte>) {
        llista = llistanova
        notifyDataSetChanged()
    }
}