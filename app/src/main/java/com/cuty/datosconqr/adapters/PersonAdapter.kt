package com.cuty.datosconqr.adapters

import android.content.Context
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cuty.datosconqr.R
import com.cuty.datosconqr.base.BaseViewHolder
import com.cuty.datosconqr.models.Persona

class PersonAdapter(
    private val context: Context,
    private val personaList: List<Persona>
) : RecyclerView.Adapter<BaseViewHolder<*>>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
       return PersonaViewHolder(
           LayoutInflater.from(context).inflate(R.layout.dni_rv_item,parent,false)
       )
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is PersonaViewHolder -> personaList.get(position).let { holder.bind(it,position) }
        }
    }

    override fun getItemCount(): Int {
        return personaList.size
    }

    inner class PersonaViewHolder(itemView:View) : BaseViewHolder<Persona>(itemView){
        private val nameTv : TextView = itemView.findViewById(R.id.dni_item_name)
        private val surnameTV : TextView = itemView.findViewById(R.id.dni_item_surname)
        private val idNumber :TextView = itemView.findViewById(R.id.dni_item_number_id)
        private val address : TextView = itemView.findViewById(R.id.dni_item_address)
        override fun bind(item: Persona, position: Int) {
            nameTv.text = item.primerNombre
            surnameTV.text = item.primerApellido
            idNumber.text = item.id.toString()
            address.text = item.diaDeNacimiento.toString()
        }
    }
}