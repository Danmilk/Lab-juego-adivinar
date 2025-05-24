package com.example.adivinaadivinador

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.adivinaadivinador.ui.theme.AdivinaAdivinadorTheme
import kotlinx.coroutines.delay

@Composable
fun GameScreen(navController: NavHostController) {
    // Número aleatorio a adivinar entre 1 y 20
    val targetNumber = remember { (1..20).random() }
    // Estado de conteo de tiempo en segundos (60s)
    var timeLeft by remember { mutableStateOf(60) }
    // Vidas o intentos fallidos (máx 3)
    var failures by remember { mutableStateOf(0) }
    // Texto de feedback al usuario
    var feedback by remember { mutableStateOf("") }
    // Entrada del usuario
    var guess by remember { mutableStateOf("") }

    // Lanzar un efecto para decrementar el timer cada segundo
    LaunchedEffect(key1 = timeLeft) {
        if (timeLeft > 0 && failures < 3) {
            delay(1000L)
            timeLeft -= 1
        } else if (timeLeft <= 0 || failures >= 3) {
            // Tiempo agotado o vidas consumidas → pantalla de fin
            navController.navigate("result?won=false&answer=$targetNumber")
        }
    }

    // Composición de la UI de juego
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Mostrar cronómetro HH:MM:SS (sólo segundos)
            Text(
                text = "Tiempo: ${timeLeft}s",
                fontSize = 24.sp,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            // Mostrar vidas como casillas con X en fallos
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(vertical = 8.dp)
            ) {
                repeat(3) { index ->
                    val isLost = index < failures
                    Text(
                        text = if (isLost) "X" else "☐",
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Campo para ingresar el número
            OutlinedTextField(
                value = guess,
                onValueChange = { guess = it },
                label = { Text("Ingresa tu número del 1 al 20") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para enviar el intento
            Button(
                onClick = {
                    val num = guess.toIntOrNull()
                    if (num == null) {
                        feedback = "Ingresa un número válido"
                    } else if (num == targetNumber) {
                        // Adivinó correctamente → fin exitoso
                        navController.navigate("result?won=true&answer=$targetNumber")
                    } else {
                        // Incrementar contador de fallos y dar pista
                        failures += 1
                        feedback = if (num < targetNumber) {
                            "Oops, un poco más arriba"
                        } else {
                            "Oops, un poco más abajo"
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Enviar")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Mensaje de feedback
            Text(
                text = feedback,
                color = MaterialTheme.colorScheme.primary,
                fontSize = 18.sp
            )
        }
    }
}
// Previsualización de la pantalla de bienvenida en Android Studio
@Preview(showBackground = true)
@Composable
fun GameScreen() {
    val fakeNavController = rememberNavController()
    AdivinaAdivinadorTheme {
        GameScreen(navController = fakeNavController)
    }
}
