package com.ufovanguard.planetpulseacademy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ufovanguard.planetpulseacademy.ui.theme.PlanetPulseAcademyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlanetPulseAcademyTheme {

            }
        }
    }
}
