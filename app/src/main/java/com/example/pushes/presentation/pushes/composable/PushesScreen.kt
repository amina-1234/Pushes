package com.example.pushes.presentation.pushes.composable

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pushes.R
import com.example.pushes.presentation.pushes.PushesState
import com.example.pushes.ui.theme.Black
import com.example.pushes.ui.theme.DarkGrey
import com.example.pushes.ui.theme.Typography
import com.example.pushes.ui.theme.White

interface PushesScreenClickListener {
    fun onPledgeTimeChange(hour: Int, minute: Int)
    fun onReviewTimeChange(hour: Int, minute: Int)
    fun onAllowNotificationClick()
    fun onSkipStepClick()
}

@Composable
fun PushesScreen(
    state: PushesState,
    uiListener: PushesScreenClickListener
) {
    Scaffold { pv ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(pv)
                .padding(vertical = 24.dp, horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(top = 24.dp),
                text = stringResource(id = R.string.pushes_importance_description),
                style = Typography.titleLarge,
            )
            Image(
                modifier = Modifier
                    .padding(60.dp)
                    .size(160.dp),
                painter = painterResource(id = R.drawable.img_bell),
                contentDescription = null
            )
            NotificationsTimePicker(
                pledgeTime = state.pledgeTime,
                reviewTime = state.reviewTime,
                onPledgeTimeChange = uiListener::onPledgeTimeChange,
                onReviewTimeChange = uiListener::onReviewTimeChange
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = MaterialTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Black,
                    contentColor = White
                ),
                onClick = { uiListener.onAllowNotificationClick() }
            ) {
                Text(
                    text = stringResource(id = R.string.pushes_confirm),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            OutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .height(48.dp),
                shape = MaterialTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(
                    containerColor = White,
                    contentColor = Black
                ),
                border = BorderStroke(
                    width = 1.dp,
                    color = DarkGrey
                ),
                onClick = { uiListener.onSkipStepClick() }
            ) {
                Text(
                    text = stringResource(id = R.string.pushes_skip_step),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    PushesScreen(
        state = PushesState.initial(),
        uiListener = object : PushesScreenClickListener {
            override fun onPledgeTimeChange(hour: Int, minute: Int) {}
            override fun onReviewTimeChange(hour: Int, minute: Int) {}
            override fun onAllowNotificationClick() {}
            override fun onSkipStepClick() {}
        }
    )
}