package com.example.tt_brg

import android.icu.text.ListFormatter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tt_brg.ui.theme.TTBRGTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TTBRGTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding()
                ){
                    Text(
                        text = "TEAM TITANS",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleLarge,
                        fontSize = 50.sp,
                        textAlign = TextAlign.Center,
                    )
                    Divider(
                        color = MaterialTheme.colorScheme.error,
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    PlayerEsports("Xx_Hueco_xX", "Jungla", 20, 16, R.drawable.jungla)

                    Spacer(modifier = Modifier.height(10.dp))

                    PlayerEsports("Chicagameruwu", "Support", 18, 4, R.drawable.support)

                    Spacer(modifier = Modifier.height(10.dp))

                    PlayerEsports("Creepeer33", "Top", 22, 45, R.drawable.top)

                    Spacer(modifier = Modifier.height(10.dp))

                    PlayerEsports("NoobDestroyer6969", "ADC", 19, 40, R.drawable.adc)

                    Spacer(modifier = Modifier.height(10.dp))

                    PlayerEsports("Alexelpro_777", "Mid", 21, 68, R.drawable.mid)
                }
            }
        }
    }
}

@Composable
fun PlayerEsports(
    nombre: String,
    rol: String,
    edad: Int,
    nivel: Int,
    @DrawableRes imagen: Int
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column() {
            Row() {
                Image(
                    painter = painterResource(id = imagen),
                    "Imagen de Jugador",
                    Modifier.size(100.dp, 100.dp)
                )
                Column() {
                    Text(
                        text = "Nombre: " + nombre
                    )
                    Text(
                        text = "Rol: " + rol
                    )
                    Row() {
                        Text(
                            text = "Edad: " + edad.toString() + "      "
                        )
                        Text(
                            text = "Nivel: " + nivel.toString()
                        )
                    }
                }
            }
        }
    }
}