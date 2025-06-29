package com.example.productoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// ✅ Define tu paleta de colores
private val LightColorPalette = lightColors(
    primary = Color(0xFF6A1B9A), // morado fuerte
    primaryVariant = Color(0xFF9C27B0), // morado
    secondary = Color(0xFFFFC107), // ámbar
    background = Color(0xFFF3E5F5), // morado claro
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProductAppTheme {
                ProductApp()
            }
        }
    }
}

// ✅ Tema Composable
@Composable
fun ProductAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = LightColorPalette,
        typography = Typography(),
        shapes = Shapes(),
        content = content
    )
}

// ✅ Clase de datos Product
data class Product(
    val nombre: String,
    val precio: Double,
    val categoria: String
)

// ✅ DataSource con lista de productos
object DataSource {
    val products = listOf(
        Product("Laptop", 999.99, "Electrónica"),
        Product("Camiseta", 19.99, "Ropa"),
        Product("Mouse", 29.99, "Electrónica"),
        Product("Zapatos", 49.99, "Ropa")
    )
}

// ✅ UI principal con menú alineado a la derecha
@Composable
fun ProductApp() {
    var selectedCategory by remember { mutableStateOf("All") }
    var expanded by remember { mutableStateOf(false) }

    val categories = listOf("All", "Electrónica", "Ropa", "Otros")
    val filteredProducts = if (selectedCategory == "All") {
        DataSource.products
    } else {
        DataSource.products.filter { it.categoria == selectedCategory }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista de Productos") },
                backgroundColor = MaterialTheme.colors.primary
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
        ) {

            // Dropdown Menu alineado a la derecha
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd // Alinea a la derecha
            ) {
                Button(onClick = { expanded = true }) {
                    Text(text = "Filtrar: $selectedCategory")
                    Icon(Icons.Filled.ArrowDropDown, contentDescription = "Arrow Down")
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    categories.forEach { category ->
                        DropdownMenuItem(onClick = {
                            selectedCategory = category
                            expanded = false
                        }) {
                            Text(text = category)
                        }
                    }
                }
            }

            // Product List
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn {
                items(filteredProducts) { product ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        elevation = 4.dp
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Nombre: ${product.nombre}",
                                style = MaterialTheme.typography.h6
                            )
                            Text(
                                text = "Precio: $${product.precio}",
                                style = MaterialTheme.typography.body1
                            )
                            Text(
                                text = "Categoría: ${product.categoria}",
                                style = MaterialTheme.typography.body2
                            )
                        }
                    }
                }
            }
        }
    }
}

// ✅ Vista previa
@Preview(showBackground = true)
@Composable
fun ProductAppPreview() {
    ProductAppTheme {
        ProductApp()
    }
}
