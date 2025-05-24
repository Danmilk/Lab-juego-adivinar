package com.example.adivinaadivinador


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

/**
 * EndScreen muestra un mensaje y una imagen diferente
 * según si el jugador ganó o perdió.
 *
 * @param won Booleano que indica si se adivinó correctamente (true) o no (false)
 */
@Composable
fun EndScreen(navController: NavHostController, won: Boolean,answer: Int) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            // Centrar todo el contenido en la pantalla
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Imagen según resultado
            val imageRes = if (won) {
                R.drawable.shinji_aplausos
            } else {
                R.drawable.bebe_ceniza
            }

            Image(
                painter = painterResource(id = imageRes),
                contentDescription = if (won) "Shinji aplaudiendo" else "Bebé ceniza",
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Título según resultado
            Text(
                text = if (won) "¡Felicidades!" else "Mala suerte, a la siguiente sí",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            if (!won) {
                // Aquí mostramos el número correcto
                Text(
                    text = "El número correcto era $answer",
                    fontSize = 18.sp,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(bottom = 24.dp)
                )}

            // Botón para reiniciar o volver al inicio
            Button(
                onClick = {
                    // Regresa al inicio del juego
                    navController.popBackStack(route = "welcome", inclusive = false)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "Volver a jugar")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EndScreenPreview_Won() {
    // Preview en modo "ganado"
    EndScreen(
        navController = rememberNavController(),
        won = true,
        answer = 0
    )
}

@Preview(showBackground = true)
@Composable
fun EndScreenPreview_Lost() {
    // Preview en modo "perdido"
    EndScreen(
        navController = rememberNavController(),
        won = false,
        answer = 42
    )
}