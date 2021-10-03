package com.amadev.juniormath.ui.screen.components.emailSent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amadev.juniormath.R


@Preview
@Composable
fun EmailSent() {
    Column(
        modifier = Modifier
            .wrapContentSize()
            .background(color = MaterialTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                Icons.Outlined.Email,
                modifier = Modifier.size(96.dp),
                contentDescription = "",
                tint = MaterialTheme.colors.primary
            )
            Text(
                text = stringResource(id = R.string.checkYourEmail),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Normal,
                color = MaterialTheme.colors.onSurface
            )
            Text(modifier = Modifier.padding(0.dp,12.dp),
                text = stringResource(id = R.string.weSent),
                fontStyle = FontStyle.Normal,
                fontSize = 16.sp,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onSurface
            )
        }
    }
}