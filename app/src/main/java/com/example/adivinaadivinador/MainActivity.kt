package com.example.adivinaadivinador
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.adivinaadivinador.ui.theme.AdivinaAdivinadorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AdivinaAdivinadorTheme {
                // El nav controller para navegacion entre pantallas
                val navController = rememberNavController()

                // Setteamos la navegacion para que el start sea el welcome screen
                NavHost(
                    navController = navController,
                    startDestination = "welcome"
                ) {
                    // definimos la navegacion a cada una de las pantallas
                    composable("welcome") { WelcomeScreen(navController) }
                    composable("game") { GameScreen(navController)  }
                    composable(
                        // definimos won y answer para la pantalla de resultado
                        route = "result?won={won}&answer={answer}",
                        arguments = listOf(
                            navArgument("won") {
                            type = NavType.BoolType
                            defaultValue = false
                        },
                            navArgument("answer") {
                                type = NavType.IntType
                                defaultValue = 0
                        })
                    ) { backStack ->
                        // Extraemos el valor de won y sold
                        val won = backStack.arguments?.getBoolean("won") ?: false
                        val answer = backStack.arguments?.getInt("answer") ?: 0
                        EndScreen(navController = navController, won = won, answer = answer)
                    }
                }
            }
        }
    }
}

@Composable
fun WelcomeScreen(navController: NavHostController) {
    // el surface es para mantener el theme constante (lo vi en un tutorial)
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        // columna principal para añadir los elementos
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Imagen decorativa
            Image(
                painter = painterResource(id = R.drawable.chess_piece),
                contentDescription = "Pieza de ajedrez",
                modifier = Modifier
                    .size(300.dp)
                    .padding(bottom = 16.dp)
            )
            // Titulo
            Text(
                text = "Bienvenidos al juego de adivinanzas",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // reglas
            Text(
                text = "Tienes 3 intentos para adivinar el número",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Text(
                text = "El tiempo límite es de un minuto",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Text(
                text = "Si no logras adivinar el número, me pones 100",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Navigation para entrar al juego
            Button(
                onClick = { navController.navigate("game") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(text = "Jugar")
            }
        }
    }
}
// Previsualización de la pantalla de bienvenida en Android Studio
@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {

    val fakeNavController = rememberNavController()
    AdivinaAdivinadorTheme {
        WelcomeScreen(navController = fakeNavController)
    }
}
