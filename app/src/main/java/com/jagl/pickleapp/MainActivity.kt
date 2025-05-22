package com.jagl.pickleapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.jagl.pickleapp.features.home.HomeScreen
import com.jagl.pickleapp.ui.theme.PickleAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PickleAppTheme {
                HomeScreen {
                    println("Clicked on character with id: $it")
                }
            }
        }
    }
}