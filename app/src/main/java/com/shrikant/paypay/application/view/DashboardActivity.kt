package com.shrikant.paypay.application.view

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.shrikant.paypay.application.ui.theme.PayPayApplicationTheme
import com.shrikant.paypay.application.view.compose.DashboardScreen
import dagger.android.AndroidInjection
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
class DashboardActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: DashboardViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setComposeContent()
    }

    fun setComposeContent() {
        setContent {
            PayPayApplicationTheme {
                DashboardScreen(Modifier.fillMaxSize().navigationBarsPadding(), viewModel)
            }
        }
    }
}