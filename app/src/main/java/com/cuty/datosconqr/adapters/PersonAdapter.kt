package com.cuty.datosconqr.adapters

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.cuty.datosconqr.R
import com.cuty.datosconqr.base.BaseViewHolder
import com.cuty.datosconqr.models.Persona


class PersonAdapter(
    private val context: Context,
    private val personaList: List<Persona>,
    private val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnItemClickListener {
        fun onClick(item: Persona, position: Int)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        return PersonaViewHolder(
            LayoutInflater.from(context).inflate(R.layout.dni_rv_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is PersonaViewHolder -> personaList.get(position).let { holder.bind(it, position) }
        }
    }

    override fun getItemCount(): Int {
        return personaList.size
    }

    inner class PersonaViewHolder(itemView: View) : BaseViewHolder<Persona>(itemView) {
        private val nameTv: TextView = itemView.findViewById(R.id.dni_item_name)
        private val surnameTV: TextView = itemView.findViewById(R.id.dni_item_surname)
        private val idNumber: TextView = itemView.findViewById(R.id.dni_item_number_id)
        private val address: TextView = itemView.findViewById(R.id.dni_item_address)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.bu_rv_delete)
        override fun bind(item: Persona, position: Int) {
            nameTv.text = item.primerNombre
            surnameTV.text = item.primerApellido
            idNumber.text = item.id.toString()
            address.text = item.diaDeNacimiento.toString()
            deleteButton.setOnClickListener {
                AlertDialog.Builder(context)
                    .setTitle("Borrar Persona")
                    .setMessage("Estas seguro que quieres borrar a ${item.id} ?")
                    .setPositiveButton("Si") { dialog, which ->
                        onItemClickListener.onClick(item, position)
                        Toast.makeText(context,"Persona borada de la base de datos",Toast.LENGTH_LONG).show()
                    }
                    .setNegativeButton("No", null)
                    .setIcon(R.drawable.ic_baseline_delete_24)
                    .show()
            }


        }

    }
}