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
import com.example.clotstore.API.Estil
import com.example.clotstore.ProductesMenu.ActivityEstil
import com.example.clotstore.R

class AdaptadorEstils(var llista: ArrayList<Estil>) :
    RecyclerView.Adapter<AdaptadorEstils.ViewHolder>() {
    class ViewHolder(val vista: View) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(vista) {

        val nom = vista.findViewById<TextView>(R.id.nomCVmenu)
        val imatge = vista.findViewById<ImageView>(R.id.imageCVmenu)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context)
        var vh: ViewHolder? = null

        vh = ViewHolder(layout.inflate(R.layout.cardview_menu, parent, false))


        return vh!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nom.setText(llista[position].nom)
        Glide.with(holder.vista.context).load(llista[position].imatge).into(holder.imatge)

        holder.vista.setOnClickListener {
            val intent = Intent(holder.vista.context, ActivityEstil::class.java)
            intent.putExtra("idEstil", llista[position].idEstil)
            intent.putExtra("nom", llista[position].nom)
            ContextCompat.startActivity(holder.vista.context, intent, null)
        }
    }

    fun updateLlista(llistanova: ArrayList<Estil>) {
        llista = llistanova
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int = llista.size

}