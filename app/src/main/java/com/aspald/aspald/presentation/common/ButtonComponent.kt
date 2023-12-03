package com.aspald.aspald.presentation.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.aspald.aspald.R

@Composable
fun ButtonComponent(
    onClick: () -> Unit,
    buttonText: String,
    isEnabled: Boolean,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        enabled = isEnabled,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.primary))
    ) {
        Text(
            text = buttonText,
            style = MaterialTheme.typography.titleMedium,
            color = colorResource(id = R.color.secondary),
        )
    }
}