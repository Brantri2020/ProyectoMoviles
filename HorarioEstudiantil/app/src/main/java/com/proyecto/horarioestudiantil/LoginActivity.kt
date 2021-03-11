package com.proyecto.horarioestudiantil

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.proyecto.horarioestudiantil.database.HorarioDbHelper

class LoginActivity : AppCompatActivity() {

    var estudiante = arrayListOf<EstudianteModelClass>()
    lateinit var buttonLogin: Button
    lateinit var buttonLinkRegister: Button
    lateinit var editTexCorreo: EditText
    lateinit var editTexPassword: EditText
    lateinit var checkRecordarme: CheckBox
    var login: String? = null
    var password: String? = null

    //API GOOGle
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        ///Cambiar a modo vertical
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        auth = FirebaseAuth.getInstance();
        /////GOOGLE
        // Initialize Firebase Auth

        auth = Firebase.auth


        editTexCorreo = findViewById<EditText>(R.id.editTextCorreo)
        editTexPassword = findViewById<EditText>(R.id.editTextTextPassword)
        buttonLogin = findViewById<Button>(R.id.btnIngresar)
        buttonLinkRegister = findViewById<Button>(R.id.btnLinkRegistrar)
        checkRecordarme= findViewById<CheckBox>(R.id.checkRecordar)

        //Inicializo llave
        val masterKeyAlias: String = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        val sharedPreferences = EncryptedSharedPreferences.create(
            ARCHIVO_PREFERENCES,//filename
            masterKeyAlias,
            this,//context
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        //val sharedPref = getPreferences(Context.MODE_PRIVATE)

        //Lectura de valores de archivo de preferencias en caso que exita
        login = sharedPreferences.getString(LOGIN_KEY, "")
        password = sharedPreferences.getString(PASSWORD_KEY, "")
        editTexCorreo.setText(login)
        editTexPassword.setText(password)
        if (login.toString() != "" && password.toString() != "") {
            checkRecordarme.isChecked = true
        }


        buttonLogin.setOnClickListener {
            if (!ValidarDatos()) {
                return@setOnClickListener
            }else{

            }
            if (checkRecordarme.isChecked) {
                /*sharedPref
                    .edit()
                    .putString(LOGIN_KEY,editTextTextEmailAddress.text.toString())
                    .putString(PASSWORD_KEY,editTextTextPassword.text.toString())
                    .apply()
                */
                sharedPreferences
                    .edit()
                    .putString(LOGIN_KEY, editTexCorreo.text.toString())
                    .putString(PASSWORD_KEY, editTexPassword.text.toString())
                    .apply()
            } else {
                val editor = sharedPreferences.edit()
                editor.putString(LOGIN_KEY, "")
                editor.putString(PASSWORD_KEY, "")
                editor.commit()
            }

            AutenticarUsuario(editTexCorreo.text.toString(), editTexPassword.text.toString())


        }



        buttonLinkRegister.setOnClickListener{
            val i = Intent(this, RegisterActivity::class.java)
            startActivity(i)

        }
    }

    fun AutenticarUsuario(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    var intent = Intent(this, InicioActivity::class.java)
                    intent.putExtra(CORREO_ESTUDIANTE,email)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(baseContext, task.exception!!.message,
                        Toast.LENGTH_SHORT).show()
                    editTexCorreo.setText("")
                    editTexPassword.setText("")
                }
            }
    }


    fun ValidarDatos(): Boolean {

        fun CharSequence?.isValidEmail() =
            !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
        if (editTexCorreo.text.isNullOrEmpty()) {
            editTexCorreo.setError(getString(R.string.editTextTextEmailAddress_hint))
            editTexCorreo.requestFocus()
            editTexCorreo.setText("")
            return false
        }
        if (!editTexCorreo.text.isValidEmail()) {
            editTexCorreo.setError(getString(R.string.email_NoValido))
            editTexCorreo.requestFocus()
            editTexCorreo.setText("")
            return false
        }
        if (editTexPassword.text.isNullOrEmpty()) {
            editTexPassword.setError(getString(R.string.editTextPassword_hint))
            editTexPassword.requestFocus()
            editTexPassword.setText("")
            return false
        }

        return true
    }




    fun consultarEstudiante(email: String?, pass: String?): Array<String> {
        var arrayLogin = arrayOf("","")
        var mensaje: String? = ""
        var idEstudiante: String?=""
        arrayLogin = HorarioDbHelper(this).readAllStudent(email, pass)

        return arrayLogin

    }

}