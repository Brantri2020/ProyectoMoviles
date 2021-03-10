package com.proyecto.horarioestudiantil.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.proyecto.horarioestudiantil.EstudianteModelClass
import com.proyecto.horarioestudiantil.HorarioModelClass
import com.proyecto.horarioestudiantil.MateriaModelClass

class HorarioDbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
        db.execSQL(SQL_CREATE_ENTRIES2)
        db.execSQL(SQL_CREATE_ENTRIES3)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES)
        db.execSQL(SQL_DELETE_ENTRIES2)
        db.execSQL(SQL_DELETE_ENTRIES3)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    companion object {
        //Crear tabla ESTUDIANTE
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "HorarioBDD.db"
        const val TABLE_NAME = "TBL_ESTUDIANTE"
        const val COLUMN_NAME_USERID = "UserID"
        const val COLUMN_NAME_FIRSTNAME = "FirstName"
        const val COLUMN_NAME_LASTNAME = "LastName"
        const val COLUMN_NAME_EMAIL = "Email"
        const val COLUMN_NAME_PASS = "Pass"
        const val SQL_CREATE_ENTRIES =
            "CREATE TABLE ${TABLE_NAME} (" +
                    "${COLUMN_NAME_USERID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "${COLUMN_NAME_FIRSTNAME} TEXT," +
                    "${COLUMN_NAME_LASTNAME} TEXT," +
                    "${COLUMN_NAME_EMAIL} TEXT," +
                    "${COLUMN_NAME_PASS} TEXT)"


        const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${TABLE_NAME}"

        //Crear tabla Materia
        // If you change the database schema, you must increment the database version.
        const val TABLE_NAME2 = "TBL_MATERIA"
        const val COLUMN_NAME_SUBJECTID = "SubjectID"
        const val COLUMN_NAME_SUBJECTNAME = "SubjectName"
        const val SQL_CREATE_ENTRIES2 =
            "CREATE TABLE ${TABLE_NAME2} (" +
                    "${COLUMN_NAME_SUBJECTID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "${COLUMN_NAME_SUBJECTNAME} TEXT," +
                    "${COLUMN_NAME_USERID} INTEGER," +
                    "FOREIGN KEY (${COLUMN_NAME_USERID}) REFERENCES ${TABLE_NAME}(${COLUMN_NAME_USERID}))"

        const val SQL_DELETE_ENTRIES2 = "DROP TABLE IF EXISTS ${TABLE_NAME2}"


        //Crear tabla Horario
        // If you change the database schema, you must increment the database version.
        const val TABLE_NAME3 = "TBL_HORARIO"
        const val COLUMN_NAME_SCHEDULEID = "ScheduleID"
        const val COLUMN_NAME_DAY = "Day"
        const val COLUMN_NAME_STARTTIME = "StartTime"
        const val COLUMN_NAME_FINISHTIME = "FinishTime"
        const val SQL_CREATE_ENTRIES3 =
            "CREATE TABLE ${TABLE_NAME3} (" +
                    "${COLUMN_NAME_SCHEDULEID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "${COLUMN_NAME_SUBJECTID} INTEGER," +
                    "${COLUMN_NAME_DAY} TEXT," +
                    "${COLUMN_NAME_STARTTIME} TEXT," +
                    "${COLUMN_NAME_FINISHTIME} TEXT," +
                    "FOREIGN KEY (${COLUMN_NAME_SUBJECTID}) REFERENCES ${TABLE_NAME2}(${COLUMN_NAME_SUBJECTID}))"

        const val SQL_DELETE_ENTRIES3 = "DROP TABLE IF EXISTS ${TABLE_NAME3}"


    }

    fun createStudent(estudiante: EstudianteModelClass): Int {

        // Gets the data repository in write mode
        val db = this.writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues().apply {
            put(COLUMN_NAME_FIRSTNAME, estudiante.firstName)
            put(COLUMN_NAME_LASTNAME, estudiante.lastName)
            put(COLUMN_NAME_EMAIL, estudiante.emailAddress)
            put(COLUMN_NAME_PASS, estudiante.pass)
        }

        // Insert the new row, returning the primary key value of the new row
        val newRowId = db?.insert(TABLE_NAME, null, values)
        db.close()
        return newRowId!!.toInt()

    }


    fun readAllStudent(emailEnviado: String?, passEnviado: String?): Array<String> {

        val db = this.readableDatabase

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        val projection = arrayOf(
            COLUMN_NAME_USERID,
            COLUMN_NAME_FIRSTNAME,
            COLUMN_NAME_LASTNAME,
            COLUMN_NAME_EMAIL,
            COLUMN_NAME_PASS
        )

        // Filter results WHERE "title" = 'My Title'
        //val selection = "${FeedEntry.COLUMN_NAME_TITLE} = ?"
        //val selectionArgs = arrayOf("My Title")

        // How you want the results sorted in the resulting Cursor
        //val sortOrder = "${COLUMN_NAME_USERID} ASC"

        val cursor = db.query(
            TABLE_NAME,   // The table to query
            projection,             // The array of columns to return (pass null to get all)
            null,              // The columns for the WHERE clause
            null,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            null               // The sort order
        )

        var mensaje: String = ""
        var bandera: Boolean = false
        var id: String = ""
        var arrayLogin = arrayOf("", id, "")


        //val students = arrayListOf<EstudianteModelClass>()
        with(cursor) {
            while (moveToNext()) {
                val idStudent = getInt(getColumnIndexOrThrow(COLUMN_NAME_USERID))
                val firstName = getString(getColumnIndexOrThrow(COLUMN_NAME_FIRSTNAME))
                val lastName = getString(getColumnIndexOrThrow(COLUMN_NAME_LASTNAME))
                val emailAddress = getString(getColumnIndexOrThrow(COLUMN_NAME_EMAIL))
                val pass = getString(getColumnIndexOrThrow(COLUMN_NAME_PASS))
                //AQUI BORRE EL MODELO

                if (emailAddress == emailEnviado && bandera == false) {
                    bandera = true
                    if (pass == passEnviado) {
                        mensaje = "Bienvenid@ " + firstName + " " + lastName
                        arrayLogin[0] = mensaje
                        arrayLogin[1] = idStudent.toString()
                        arrayLogin[2] = firstName + " " + lastName

                    } else {
                        mensaje = "Contraseña incorrecta"
                        arrayLogin[0] = mensaje
                    }
                } else {
                    mensaje = "El usuario no existe"
                    arrayLogin[0] = mensaje
                }


            }
        }

        return arrayLogin
    }

    fun createSubject(materia: MateriaModelClass): Int {

        // Gets the data repository in write mode
        val db = this.writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues().apply {
            put(COLUMN_NAME_SUBJECTNAME, materia.NameSubject)
            put(COLUMN_NAME_USERID, materia.studentId)
        }

        // Insert the new row, returning the primary key value of the new row
        val newRowId = db?.insert(TABLE_NAME2, null, values)
        db.close()
        return newRowId!!.toInt()

    }

    fun readAllSubjects(idEstudiante: String?): ArrayList<MateriaModelClass> {

        val db = this.readableDatabase
        val ID_ESTUDIANTE = idEstudiante.toString()

        val args = arrayOf(ID_ESTUDIANTE)
        val selection = "${COLUMN_NAME_USERID} = ?"


        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        val projection = arrayOf(
            COLUMN_NAME_SUBJECTID,
            COLUMN_NAME_SUBJECTNAME,
            COLUMN_NAME_USERID,
        )

        // Filter results WHERE "title" = 'My Title'
        //val selection = "${FeedEntry.COLUMN_NAME_TITLE} = ?"
        //val selectionArgs = arrayOf("My Title")

        // How you want the results sorted in the resulting Cursor
        //val sortOrder = "${COLUMN_NAME_USERID} ASC"
        val cursor = db.query(
            TABLE_NAME2,   // The table to query
            projection,             // The array of columns to return (pass null to get all)
            selection,              // The columns for the WHERE clause
            args,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            null               // The sort order
        )


        val subjects = arrayListOf<MateriaModelClass>()
        with(cursor) {
            while (moveToNext()) {
                val idSubject = getInt(getColumnIndexOrThrow(COLUMN_NAME_SUBJECTID))
                val nameSubject = getString(getColumnIndexOrThrow(COLUMN_NAME_SUBJECTNAME))
                val idStudent = getInt(getColumnIndexOrThrow(COLUMN_NAME_USERID))
                subjects.add(MateriaModelClass(idSubject, nameSubject, idStudent))
            }
        }

        return subjects
    }

    fun consultarMateria(nombreMateria: String?): Boolean {

        val db = this.readableDatabase
        val NOMBRE_MATERIA = nombreMateria.toString()

        val args = arrayOf(NOMBRE_MATERIA)
        val selection = "${COLUMN_NAME_SUBJECTNAME} = ?"


        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        val projection = arrayOf(
            COLUMN_NAME_SUBJECTNAME
        )

        // Filter results WHERE "title" = 'My Title'
        //val selection = "${FeedEntry.COLUMN_NAME_TITLE} = ?"
        //val selectionArgs = arrayOf("My Title")

        // How you want the results sorted in the resulting Cursor
        //val sortOrder = "${COLUMN_NAME_USERID} ASC"
        val cursor = db.query(
            TABLE_NAME2,   // The table to query
            projection,             // The array of columns to return (pass null to get all)
            selection,              // The columns for the WHERE clause
            args,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            null               // The sort order
        )


        var existe: Boolean = false
        with(cursor) {
            while (moveToNext()) {
                val nameSubject = getString(getColumnIndexOrThrow(COLUMN_NAME_SUBJECTNAME))
                if (nameSubject == nombreMateria) {
                    existe = true
                }
            }
        }

        return existe
    }


    fun actualizarMateria(materia: MateriaModelClass, nombre: String): Int {
        val db = this.writableDatabase

        // New value for one column
        val values = ContentValues().apply {
            put(COLUMN_NAME_SUBJECTNAME, materia.NameSubject)
        }
        // Which row to update, based on the title
        val selection = "${COLUMN_NAME_SUBJECTNAME} = ?"
        val selectionArgs = arrayOf(nombre)
        val count = db.update(
            TABLE_NAME2,
            values,
            selection,
            selectionArgs
        )
        return count
    }


    fun eliminarMateria(nombreMateria: String): Int {
        val db = this.writableDatabase
        // Define 'where' part of query.
        val selection = "${COLUMN_NAME_SUBJECTNAME} = ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(nombreMateria)
        // Issue SQL statement.
        val deletedRows = db.delete(TABLE_NAME2, selection, selectionArgs)
        return deletedRows
    }

    fun readAllSchedule(idEstudiante: String?): ArrayList<HorarioModelClass> {

        val db = this.readableDatabase
        val ID_ESTUDIANTE = idEstudiante.toString()

        val args = arrayOf(ID_ESTUDIANTE)
        val selection = "${COLUMN_NAME_USERID} = ?"


        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        val projection = arrayOf(
            COLUMN_NAME_SUBJECTID
        )

        // Filter results WHERE "title" = 'My Title'
        //val selection = "${FeedEntry.COLUMN_NAME_TITLE} = ?"
        //val selectionArgs = arrayOf("My Title")

        // How you want the results sorted in the resulting Cursor
        //val sortOrder = "${COLUMN_NAME_USERID} ASC"
        val cursor = db.query(
            TABLE_NAME2,   // The table to query
            projection,             // The array of columns to return (pass null to get all)
            selection,              // The columns for the WHERE clause
            args,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            null               // The sort order
        )

        val idSubjects = arrayListOf<String>()
        with(cursor) {
            while (moveToNext()) {
                val idSubject = getInt(getColumnIndexOrThrow(COLUMN_NAME_SUBJECTID))
                idSubjects.add(idSubject.toString())
            }
        }

//        val NOMBRE_MATERIA = nombreMateria.toString()

        var ids = arrayOfNulls<String>(idSubjects.size)
        ids = idSubjects.toArray(ids)

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        val projection2 = arrayOf(
            COLUMN_NAME_SCHEDULEID,
            COLUMN_NAME_SUBJECTID,
            COLUMN_NAME_DAY,
            COLUMN_NAME_STARTTIME,
            COLUMN_NAME_FINISHTIME
        )

        val selection2 = "${COLUMN_NAME_SUBJECTID} = ?"


        // Define a projection that specifies which columns from the database
        // you will actually use after this query.

        // Filter results WHERE "title" = 'My Title'
        //val selection = "${FeedEntry.COLUMN_NAME_TITLE} = ?"
        //val selectionArgs = arrayOf("My Title")

        // How you want the results sorted in the resulting Cursor
        //val sortOrder = "${COLUMN_NAME_USERID} ASC"
        val cursor2 = db.query(
            TABLE_NAME3,   // The table to query
            projection2,             // The array of columns to return (pass null to get all)
            selection2,              // The columns for the WHERE clause
            ids,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            null               // The sort order
        )


        val schedules = arrayListOf<HorarioModelClass>()
        with(cursor) {
            while (moveToNext()) {
                val idSchedule = getInt(getColumnIndexOrThrow(COLUMN_NAME_SCHEDULEID))
                val idSubject = getInt(getColumnIndexOrThrow(COLUMN_NAME_SUBJECTID))
                val day = getString(getColumnIndexOrThrow(COLUMN_NAME_DAY))
                val startTime = getString(getColumnIndexOrThrow(COLUMN_NAME_STARTTIME))
                val finishTime = getString(getColumnIndexOrThrow(COLUMN_NAME_FINISHTIME))
                schedules.add(HorarioModelClass(idSchedule, idSubject, day, startTime, finishTime))
            }
        }


        return schedules
    }

    fun llenarSpinnerMateria(idEstudiante: String?): Array<String?> {

        val db = this.readableDatabase
        val ID_ESTUDIANTE = idEstudiante.toString()

        val args = arrayOf(ID_ESTUDIANTE)
        val selection = "${COLUMN_NAME_USERID} = ?"


        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        val projection = arrayOf(
            COLUMN_NAME_SUBJECTID,
            COLUMN_NAME_SUBJECTNAME,
            COLUMN_NAME_USERID,
        )

        // Filter results WHERE "title" = 'My Title'
        //val selection = "${FeedEntry.COLUMN_NAME_TITLE} = ?"
        //val selectionArgs = arrayOf("My Title")

        // How you want the results sorted in the resulting Cursor
        //val sortOrder = "${COLUMN_NAME_USERID} ASC"
        val cursor = db.query(
            TABLE_NAME2,   // The table to query
            projection,             // The array of columns to return (pass null to get all)
            selection,              // The columns for the WHERE clause
            args,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            null               // The sort order
        )


        val subjects = arrayListOf<String>()
        with(cursor) {
            while (moveToNext()) {
                val nameSubject = getString(getColumnIndexOrThrow(COLUMN_NAME_SUBJECTNAME))
                subjects.add(nameSubject)
            }
        }

        var materias = arrayOfNulls<String>(subjects.size)
        materias = subjects.toArray(materias)

        return materias
    }

    fun consultarDiaHora(dia: String?, horaInicio: String?, horaFin: String?): Boolean {

        val db = this.readableDatabase
        val DIA = dia.toString()
        val HORA_INICIO = horaInicio.toString()
        val HORA_FIN = horaFin.toString()

        val args = arrayOf(DIA)
        val selection = "${COLUMN_NAME_DAY} = ?"


        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        val projection = arrayOf(
            COLUMN_NAME_STARTTIME,
            COLUMN_NAME_FINISHTIME
        )

        // Filter results WHERE "title" = 'My Title'
        //val selection = "${FeedEntry.COLUMN_NAME_TITLE} = ?"
        //val selectionArgs = arrayOf("My Title")

        // How you want the results sorted in the resulting Cursor
        val sortOrder = "${COLUMN_NAME_STARTTIME} ASC"

        val cursor = db.query(
            TABLE_NAME3,   // The table to query
            projection,             // The array of columns to return (pass null to get all)
            selection,              // The columns for the WHERE clause
            args,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            sortOrder            // The sort order
        )


        var existe: Boolean = false
        val horas = mutableListOf<String>()
        with(cursor) {
            while (moveToNext()) {
                val inicio = getString(getColumnIndexOrThrow(COLUMN_NAME_STARTTIME))
                val fin = getString(getColumnIndexOrThrow(COLUMN_NAME_FINISHTIME))
                horas.add(inicio.toString())
                horas.add(fin.toString())
            }
        }

        if (horas.size != 0) {

            val horasOcupadas = mutableListOf<Int>()

            val tamanho: Int = horas.size


            var i = 0
            var inicial = 0
            var final = 0
            while (i < tamanho-1) {
                inicial = horas[i].toInt()
                final = horas[i + 1].toInt() - 1
                for (j in inicial..final) {
                    horasOcupadas.add(j)
                }
                i = i + 2
            }

            for (k in 0..horasOcupadas.size-1) {
                if (horaFin != null && horaInicio != null) {

                    existe = HORA_INICIO.toInt() == horasOcupadas[k] || HORA_FIN.toInt() - 1 == horasOcupadas[k]
                }
            }


        } else {
            existe = false
        }


        return existe
    }

    fun createSchedule(horario: HorarioModelClass): Int {

        // Gets the data repository in write mode
        val db = this.writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues().apply {
            put(COLUMN_NAME_SUBJECTID, horario.idSubject)
            put(COLUMN_NAME_DAY, horario.dia)
            put(COLUMN_NAME_STARTTIME, horario.horaInicio)
            put(COLUMN_NAME_FINISHTIME, horario.horaFin)
        }

        // Insert the new row, returning the primary key value of the new row
        val newRowId = db?.insert(TABLE_NAME3, null, values)
        db.close()
        return newRowId!!.toInt()

    }


    fun consultarIdMateria(idEstudiante: String?, nombreMateria: String?): Int {

        val db = this.readableDatabase
        val ID_ESTUDIANTE = idEstudiante.toString()
        val NOMBRE_MATERIA = nombreMateria.toString()

        val args = arrayOf(ID_ESTUDIANTE,NOMBRE_MATERIA)
        val selection = "${COLUMN_NAME_USERID} = ? AND ${COLUMN_NAME_SUBJECTNAME} = ?"


        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        val projection = arrayOf(
            COLUMN_NAME_SUBJECTID
        )

        // Filter results WHERE "title" = 'My Title'
        //val selection = "${FeedEntry.COLUMN_NAME_TITLE} = ?"
        //val selectionArgs = arrayOf("My Title")

        // How you want the results sorted in the resulting Cursor
        //val sortOrder = "${COLUMN_NAME_USERID} ASC"
        val cursor = db.query(
            TABLE_NAME2,   // The table to query
            projection,             // The array of columns to return (pass null to get all)
            selection,              // The columns for the WHERE clause
            args,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            null               // The sort order
        )


        var idMat=0
        with(cursor) {
            while (moveToNext()) {
                val idMateria = getInt(getColumnIndexOrThrow(COLUMN_NAME_SUBJECTID))
                idMat=idMateria
            }
        }



        return idMat
    }


}