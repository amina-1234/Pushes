package com.example.pushes.pushes

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.lifecycleScope
import com.example.pushes.App
import com.example.pushes.ComposeFragment
import com.example.pushes.R
import com.example.pushes.pushes.composable.PushesScreen
import com.example.pushes.pushes.domain.Event
import com.example.pushes.pushes.domain.GoNext
import com.example.pushes.pushes.domain.RequestNotificationPermission
import com.example.pushes.pushes.domain.ShowPermissionRationale
import kotlinx.coroutines.launch

class PushesFragment : ComposeFragment() {

    private val viewModel by lazy { App.instance.serviceLocator.pushesViewModel }

    private val notificationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { permissionGranted ->
            if (permissionGranted) {
                viewModel.onNotificationPermissionGranted()
            } else {
                viewModel.onNotificationPermissionDenied()
            }
        }

    private val permissionRationaleDialog by lazy {
        AlertDialog.Builder(requireContext()).apply {
            setTitle(getString(R.string.pushes_permission_rationale_title))
            setMessage(getString(R.string.pushes_permission_rationale_message))
            setPositiveButton(getString(R.string.ok)) { _, _ ->
                viewModel.onSkipStepClick()
            }
            setNegativeButton(getString(R.string.settings)) { _, _ ->
                openSettings()
            }
            create()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.eventFlow.collect { event -> handleEvent(event) }
        }
    }

    @Composable
    override fun FragmentContent() {
        PushesScreen(
            state = viewModel.uiState.collectAsState().value,
            uiListener = viewModel
        )
    }

    private fun handleEvent(event: Event) {
        when (event) {
            RequestNotificationPermission -> requestNotificationsPermission()
            ShowPermissionRationale -> permissionRationaleDialog.show()
            GoNext -> {}
        }
    }

    private fun requestNotificationsPermission() {
        if (NotificationManagerCompat.from(requireContext()).areNotificationsEnabled()) {
            viewModel.onNotificationPermissionGranted()
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                notificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
            } else {
                viewModel.onNotificationPermissionDenied()
            }
        }
    }

    private fun openSettings() {
        runCatching {
            val intent = Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", activity?.packageName, null)
            )
            activity?.startActivity(intent)
        }
    }
}