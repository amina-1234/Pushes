package com.example.pushes.presentation.pushes

import android.Manifest
import android.app.AlarmManager
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.pushes.ComposeFragment
import com.example.pushes.R
import com.example.pushes.domain.Event
import com.example.pushes.domain.GoNext
import com.example.pushes.domain.RequestNotificationPermissions
import com.example.pushes.domain.ShowPermissionRationale
import com.example.pushes.presentation.pushes.composable.PushesScreen
import kotlinx.coroutines.launch

class PushesFragment : ComposeFragment() {

    private val viewModel: PushesViewModel by viewModels()

    private val notificationPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { permissionGranted ->
            if (permissionGranted) {
                requestNotificationsPermissions()
            } else {
                viewModel.onNotificationPermissionDenied()
            }
        }

    private val alarmManager by lazy {
        ContextCompat.getSystemService(requireContext(), AlarmManager::class.java)
    }

    private val notificationsGranted: Boolean
        get() = NotificationManagerCompat.from(requireContext()).areNotificationsEnabled()

    private val scheduleNotificationsGranted: Boolean
        get() = Build.VERSION.SDK_INT < Build.VERSION_CODES.S || alarmManager?.canScheduleExactAlarms() == true

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
            RequestNotificationPermissions -> requestNotificationsPermissions()
            ShowPermissionRationale -> showPermissionsRationale()
            GoNext -> {}
        }
    }

    private fun requestNotificationsPermissions() {
        when {
            notificationsGranted.not() -> requestNotificationPermission()
            scheduleNotificationsGranted.not() -> scheduleNotificationsRationaleDialog.show()
            else -> viewModel.onNotificationPermissionGranted()
        }
    }

    private fun showPermissionsRationale() {
        when {
            notificationsGranted.not() -> notificationsRationaleDialog.show()
            scheduleNotificationsGranted.not() -> scheduleNotificationsRationaleDialog.show()
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            notificationPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
        } else {
            viewModel.onNotificationPermissionDenied()
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

    @RequiresApi(Build.VERSION_CODES.S)
    private fun openScheduleNotificationsSettings() {
        runCatching {
            val intent = Intent(
                Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM,
                Uri.fromParts("package", activity?.packageName, null)
            )
            activity?.startActivity(intent)
        }
    }

    private val notificationsRationaleDialog by lazy {
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

    private val scheduleNotificationsRationaleDialog by lazy {
        AlertDialog.Builder(requireContext()).apply {

            setTitle(getString(R.string.pushes_schedule_rationale_title))
            setMessage(getString(R.string.pushes_schedule_rationale_message))

            setPositiveButton(getString(R.string.settings)) { _, _ ->
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S) openScheduleNotificationsSettings()
            }
            setNegativeButton(getString(R.string.pushes_skip_step)) { _, _ ->
                viewModel.onSkipStepClick()
            }
            create()
        }
    }
}