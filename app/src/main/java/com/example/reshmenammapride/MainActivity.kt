package com.example.reshmenammapride

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.reshmenammapride.data.ReshmeDatabase
import com.example.reshmenammapride.ui.ReshmeNammaApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = ReshmeDatabase.getDatabase(this)
        setContent {
            ReshmeNammaTheme {
                ReshmeNammaApp(database = database)
            }
        }
    }
}

@Composable
fun ReshmeNammaTheme(content: @Composable () -> Unit) {
    val colors = lightColorScheme(
        primary = Color(0xFF1B5E20),
        secondary = Color(0xFF00695C),
        tertiary = Color(0xFF8D6E63),
        background = Color(0xFFF4FBF5),
        surface = Color.White,
        onPrimary = Color.White,
        onSecondary = Color.White,
        onBackground = Color(0xFF1B1B1B),
        onSurface = Color(0xFF1B1B1B)
    )

    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}
