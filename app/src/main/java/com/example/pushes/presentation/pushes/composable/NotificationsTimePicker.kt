package com.example.pushes.presentation.pushes.composable

import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.pushes.R
import com.example.pushes.domain.TimeConstraints
import com.example.pushes.ui.theme.DarkGrey
import com.example.pushes.ui.theme.Typography
import com.example.pushes.ui.theme.White

@Composable
fun NotificationsTimePicker(
    pledgeTime: TimeConstraints,
    reviewTime: TimeConstraints,
    onPledgeTimeChange: (hour: Int, minute: Int) -> Unit,
    onReviewTimeChange: (hour: Int, minute: Int) -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(
            containerColor = White,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
        )
    ) {
        Column {
            NotificationTimeItem(
                stringResource(id = R.string.pushes_daily_pledge),
                value = pledgeTime,
                onValueChange = onPledgeTimeChange
            )
            Divider(color = DarkGrey)
            NotificationTimeItem(
                title = stringResource(id = R.string.pushes_daily_review),
                value = reviewTime,
                onValueChange = onReviewTimeChange
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationTimeItem(
    title: String,
    value: TimeConstraints,
    onValueChange: (hour: Int, minute: Int) -> Unit,
) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.alignByBaseline(),
            text = title,
            style = Typography.bodyLarge
        )
        Card(
            modifier = Modifier.alignByBaseline(),
            shape = MaterialTheme.shapes.small,
            colors = CardDefaults.cardColors(
                containerColor = DarkGrey,
            ),
            onClick = {
                showTimePicker(
                    context = context,
                    hourOfDay = value.hour,
                    minute = value.minute,
                    onTimeSelected = onValueChange
                )
            }
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                text = value.time,
                style = Typography.bodyLarge
            )
        }
    }
}

fun showTimePicker(
    context: Context,
    hourOfDay: Int,
    minute: Int,
    onTimeSelected: (Int, Int) -> Unit
) {
    TimePickerDialog(
        context,
        { _, hourOfDay, minute -> onTimeSelected(hourOfDay, minute) },
        hourOfDay,
        minute,
        true
    ).show()
}