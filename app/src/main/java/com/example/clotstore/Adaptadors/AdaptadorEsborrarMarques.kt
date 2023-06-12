package com.example.clotstore.Adaptadors


import android.app.AlertDialog
import android.content.ContentProvider
import android.content.DialogInterface
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.clotstore.API.*
import com.example.clotstore.R
import com.example.clotstore.RegistreLogin.IniciarSessio
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AdaptadorEsborrarMarques(var llista: ArrayList<Marca>, var cerca: EditText) :

    RecyclerView.Adapter<AdaptadorEsborrarMarques.ViewHolder>() {
    class ViewHolder(val vista: View) : RecyclerView.ViewHolder(vista) {
        val imatge = vista.findViewById<ImageView>(R.id.imagen)
        val nom = vista.findViewById<TextView>(R.id.nom)
        val btD = vista.findViewById<ImageButton>(R.id.btDlt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context)
        return ViewHolder(layout.inflate(R.layout.cardview_esborrar_marca, parent, false))

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nom.setText(llista[position].nom)
        Glide.with(holder.vista.context).load(llista[position].imatge).into(holder.imatge)

        cerca.addTextChangedListener { filtre ->
            val novallista = llistaMarques.filter { marca ->
                marca.nom.lowercase().contains(filtre.toString().lowercase())
            } as ArrayList<Marca>
            holder.btD.setOnClickListener {
                runBlocking {
                    val corrutina = launch {
                        val crudApi = CRUDApi()
                        crudApi.deleteMarca(llista[position].idMarca)
                        llistaMarques.removeAt(position)
                        cerca.setText("")
                    }
                    corrutina.join()
                }
                notifyDataSetChanged()
            }
            updateLlista(novallista)
        }

        holder.btD.setOnClickListener {
            runBlocking {
                val corrutina = launch {
                    val crudApi = CRUDApi()
                    crudApi.deleteMarca(llista[position].idMarca)
                    llistaMarques.removeAt(position)
                    cerca.setText("")
                }
                corrutina.join()
            }
            notifyDataSetChanged()
            runBlocking {
                val corrutina = launch {
                    val crudApi = CRUDApi()
                    llistaMarques = crudApi.getAllMarques()
                    updateLlista(llistaMarques)
                }
                corrutina.join()
            }
        }


    }

    override fun getItemCount() = llista.size

    fun updateLlista(llistanova: ArrayList<Marca>) {
        llista = llistanova
        notifyDataSetChanged()
    }


}