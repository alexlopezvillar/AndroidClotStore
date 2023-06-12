package com.example.clotstore.Adaptadors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.clotstore.API.ProducteFactura
import com.example.clotstore.R


class AdaptadorFactura(var llista: ArrayList<ProducteFactura>) :
    RecyclerView.Adapter<AdaptadorFactura.ViewHolder>() {
    class ViewHolder(val vista: View) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(vista) {
        val nom = vista.findViewById<TextView>(R.id.nomFactura)
        var preu = vista.findViewById<TextView>(R.id.preuFactura)
        var quant = vista.findViewById<TextView>(R.id.quantitatFactura)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context)
        var vh: ViewHolder? = null

        vh = ViewHolder(layout.inflate(R.layout.cardview_factura, parent, false))


        return vh!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nom.setText(llista[position].nom)
        holder.preu.setText(String.format("%.2f", llista[position].preu) + "€")
        holder.quant.setText("Quantitat: x" + llista[position].quantitat.toString())


    }

    fun updateLlista(llistanova: ArrayList<ProducteFactura>) {
        llista = llistanova
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int = llista.size
}