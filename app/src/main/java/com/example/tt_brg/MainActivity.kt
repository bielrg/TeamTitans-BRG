package com.example.tt_brg

import android.R.attr.id
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tt_brg.ui.theme.TTBRGTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TTBRGTheme {
                val opcionesRol = listOf("Top", "Mid", "ADC", "Support", "Jungla")
                var expandido by remember { mutableStateOf(false) }

                var jugadores = remember {
                    mutableStateListOf<PlayerEsports>()
                }

                var jugadorAEliminar by remember { mutableStateOf<PlayerEsports?>(null)}
                var eliminacion by remember { mutableStateOf(false)}
                var jugadorAModificar by remember { mutableStateOf<PlayerEsports?>(null)}
                var edicion by remember { mutableStateOf(false)}
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding()
                        .padding(16.dp)
                ) {
                    item {
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

                        var name by remember { mutableStateOf("") }
                        var nick by remember { mutableStateOf("") }
                        var clase by remember { mutableStateOf("") }
                        var age by remember { mutableStateOf("") }
                        var lvl by remember { mutableStateOf("") }

                        OutlinedTextField(
                            value = name,
                            singleLine = true,
                            onValueChange = { nuevo ->
                                name = nuevo
                            },
                            label = { Text("Escribe un nombre.") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        OutlinedTextField(
                            value = nick,
                            singleLine = true,
                            onValueChange = { nuevo ->
                                nick = nuevo
                            },
                            label = { Text("Escribe un nickname.") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        ExposedDropdownMenuBox(
                            expanded = expandido,
                            onExpandedChange = { expandido = it }
                        ) {
                            OutlinedTextField(
                                readOnly = true,
                                value = clase,
                                onValueChange = {},
                                label = { Text("Selecciona un rol") },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandido) },
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth()
                            )
                            ExposedDropdownMenu(
                                expanded = expandido,
                                onDismissRequest = { expandido = false }
                            ) {
                                opcionesRol.forEach { opcion ->
                                    DropdownMenuItem(
                                        text = { Text(opcion) },
                                        onClick = {
                                            clase = opcion
                                            expandido = false
                                        }
                                    )
                                }
                            }
                        }

                        Row(modifier = Modifier.fillMaxWidth()) {
                            OutlinedTextField(
                                value = age,
                                onValueChange = { nuevo -> age = nuevo.filter { it.isDigit() } },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                label = { Text("Edad") },
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(end = 4.dp)
                            )
                            OutlinedTextField(
                                value = lvl,
                                onValueChange = { nuevo -> lvl = nuevo.filter { it.isDigit() } },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                label = { Text("Nivel") },
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 4.dp)
                            )
                        }


                        Button(
                            onClick = {
                                val jugador = PlayerEsports(
                                    id = (jugadores.maxOfOrNull { it.id } ?: 0) + 1,
                                    nombre = name,
                                    nickname = nick,
                                    rol = clase,
                                    edad = age.toInt(),
                                    nivel = lvl.toInt()
                                )

                                jugadores.add(jugador)

                                name = ""
                                nick = ""
                                age = ""
                                lvl = ""
                                clase = opcionesRol[0]

                            },
                            enabled = age.isNotBlank() && lvl.isNotBlank() && name.isNotBlank() && nick.isNotBlank() && clase.isNotBlank()
                        ) {
                            Text("Añadir jugador.")
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                    }
                    if (jugadores.isEmpty()) {
                        item{Text(text = "No hay jugadores registrados.")}
                    } else {
                        items(jugadores.size, key = { index -> jugadores[index].id }) { index ->
                            val jugador = jugadores[index]

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(text = "Nombre: ${jugador.nombre}")
                                    Text(text = "Nickname: ${jugador.nickname}")
                                    Text(text = "Rol: ${jugador.rol}")
                                    Row {
                                        Text(text = "Edad: ${jugador.edad}      ")
                                        Text(text = "Nivel: ${jugador.nivel}")
                                    }
                                }
                                Row {
                                    TextButton(onClick = {
                                        jugadorAModificar = jugador
                                        edicion = true
                                    }) {
                                        Text("Editar")
                                    }
                                    TextButton(onClick = {
                                        jugadorAEliminar = jugador
                                        eliminacion = true
                                    }) {
                                        Text("Borrar")
                                    }
                                }
                            }
                        }
                    }
                }
                if(eliminacion) {
                    jugadorAEliminar?.let {
                        jugador ->
                        jugadorABorrar(jugadores, jugador, onBorrar = { eliminacion = false })
                    }
                }
                if(edicion) {
                    jugadorAModificar?.let {
                            jugador ->
                        jugadorAEditar(jugadores, jugador, onBorrar = { edicion = false })
                    }
                }
            }
        }
    }
}

@Composable
fun jugadorABorrar(jugadores: SnapshotStateList<PlayerEsports>, jugador: PlayerEsports, onBorrar: () -> Unit){
    Column(){
        AlertDialog(
            modifier = Modifier.weight(1f),
            onDismissRequest = onBorrar,
            title = {Text("Borrado de ${jugador.nombre}")},
            text = {
                    Text("Estas seguro que quieres borrar a ${jugador.nombre}?")
            },
            confirmButton = {
                TextButton(onClick = {
                    jugador?.let {
                        jugadores.remove(it)
                    }
                    onBorrar()
                }) {
                    Text("Sí")
                }
            },
            dismissButton = {
                TextButton(onClick = onBorrar ) {
                    Text("No")
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun jugadorAEditar(jugadores: SnapshotStateList<PlayerEsports>, jugador: PlayerEsports, onBorrar: () -> Unit){
    Column(){
        var nuevonombre by remember {mutableStateOf("")}
        var nuevonick by remember {mutableStateOf("")}
        var nuevaclase by remember {mutableStateOf("")}
        var nuevaedad by remember {mutableStateOf("")}
        var nuevonivel by remember {mutableStateOf("")}
        AlertDialog(
            modifier = Modifier.weight(1f),
            onDismissRequest = onBorrar,
            title = {Text("Edición de ${jugador.nombre}")},
            text = {
                Column(){
                    val opcionesRol = listOf("Top", "Mid", "ADC", "Support", "Jungla")
                    var expandido by remember { mutableStateOf(false) }

                    OutlinedTextField(
                        value = nuevonombre,
                        singleLine = true,
                        onValueChange = { nuevo ->
                            nuevonombre = nuevo
                        },
                        label = { Text("${jugador.nombre}") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = nuevonick,
                        singleLine = true,
                        onValueChange = { nuevo ->
                            nuevonick = nuevo
                        },
                        label = { Text("${jugador.nickname}") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    ExposedDropdownMenuBox(
                        expanded = expandido,
                        onExpandedChange = { expandido = it }
                    ) {
                        OutlinedTextField(
                            readOnly = true,
                            value = nuevaclase,
                            onValueChange = {},
                            label = { Text("${jugador.rol}") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandido) },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                        )
                        ExposedDropdownMenu(
                            expanded = expandido,
                            onDismissRequest = { expandido = false }
                        ) {
                            opcionesRol.forEach { opcion ->
                                DropdownMenuItem(
                                    text = { Text(opcion) },
                                    onClick = {
                                        nuevaclase = opcion
                                        expandido = false
                                    }
                                )
                            }
                        }
                    }

                    Row(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = nuevaedad,
                            onValueChange = { nuevo -> nuevaedad = nuevo.filter { it.isDigit() } },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            label = { Text("${jugador.edad}") },
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 4.dp)
                        )
                        OutlinedTextField(
                            value = nuevonivel,
                            onValueChange = { nuevo -> nuevonivel = nuevo.filter { it.isDigit() } },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            label = { Text("${jugador.nivel}") },
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 4.dp)
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    if(nuevonombre.isNotBlank()) {
                        jugador.nombre = nuevonombre
                    }
                    if(nuevonick.isNotBlank()){
                        jugador.nickname = nuevonick
                    }
                    if(nuevaclase.isNotBlank()) {
                        jugador.rol = nuevaclase
                    }
                    if(nuevaedad.isNotBlank()) {
                        jugador.edad = nuevaedad.toInt()
                    }
                    if(nuevonivel.isNotBlank()){
                        jugador.nivel = nuevonivel.toInt()
                    }

                    onBorrar()
                }) {
                    Text("Editar")
                }
            },
            dismissButton = {
                TextButton(onClick = onBorrar ) {
                    Text("Cancelar")
                }
            }
        )
    }
}

data class PlayerEsports(
    var id: Int,
    var nombre: String,
    var nickname: String,
    var rol: String,
    var edad: Int,
    var nivel: Int
)