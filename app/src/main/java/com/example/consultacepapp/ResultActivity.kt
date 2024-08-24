package com.example.consultacepapp

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.consultacepapp.ui.theme.ConsultaCEPAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL

class ResultActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val resultTextView = findViewById<TextView>(R.id.resultTextView)
        val cep = intent.getStringExtra("CEP") ?: ""

        if (cep.isNotEmpty()) {
            fetchAddress(cep) { endereco ->
                println(endereco)
                resultTextView.text = endereco.toString()
            }
        }
    }

    private fun fetchAddress(cep: String, callback: (Endereco) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = URL("https://viacep.com.br/ws/$cep/json/").readText()
                val jsonObject = JSONObject(response)

                if (jsonObject.has("erro")) {
                    withContext(Dispatchers.Main) {
                        println("CEP Inválido")
                    }
                }
                val endereco = Endereco(
                    cep = jsonObject.getString("cep"),
                    logradouro = jsonObject.getString("logradouro"),
                    complemento = jsonObject.getString("complemento"),
                    bairro = jsonObject.getString("bairro"),
                    localidade = jsonObject.getString("localidade"),
                    uf = jsonObject.getString("uf"),
                    ibge = jsonObject.getString("ibge"),
                    gia = jsonObject.getString("gia"),
                    ddd = jsonObject.getString("ddd"),
                    siafi = jsonObject.getString("siafi")
                )

                withContext(Dispatchers.Main) {
                    callback(endereco)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

