package com.example.pushes.pushes

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.example.pushes.App
import com.example.pushes.ComposeFragment
import com.example.pushes.pushes.composable.PushesScreen

class PushesFragment : ComposeFragment() {

    private val viewModel by lazy { App.instance.serviceLocator.pushesViewModel }

    @Composable
    override fun FragmentContent() {
        PushesScreen(
            state = viewModel.uiState.collectAsState().value,
            uiListener = viewModel
        )
    }
}