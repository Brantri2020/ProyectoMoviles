package com.proyecto.horarioestudiantil

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class RecordatorioAdapter(
    private val context: Activity,
    private val recordatorio: ArrayList<RecordatorioModelClass>
) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.schedule_list, null, true)

        val txtMateria = rowView.findViewById<TextView>(R.id.txtMateria)
        val txtNombre = rowView.findViewById<TextView>(R.id.txtNombre)
        val txtTipoRecordatorio = rowView.findViewById<TextView>(R.id.txtTipoRecordatorio)
        val txtDescripcionRecordatorio =
            rowView.findViewById<TextView>(R.id.txtDescripcionRecordatorio)

        txtMateria.text = "  ${recordatorio[position].materia}   "
        txtNombre.text = "  ${recordatorio[position].nombre}   "
        txtTipoRecordatorio.text = "  ${recordatorio[position].tipo}   "
        txtDescripcionRecordatorio.text = "  ${recordatorio[position].descripcion}   "



        return rowView
    }

    override fun getItem(position: Int): Any? {
        return recordatorio.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return recordatorio.size
    }
}
