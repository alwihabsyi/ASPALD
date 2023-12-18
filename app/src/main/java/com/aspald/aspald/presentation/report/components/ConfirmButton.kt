package com.aspald.aspald.presentation.report.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aspald.aspald.R
import com.aspald.aspald.ui.theme.AspaldYellow

@Composable
fun ConfirmButton(
    modifier: Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(15.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = AspaldYellow
        )
    ) {
        Text(
            text = "Confirm",
            fontFamily = FontFamily(Font(R.font.poppins_medium, FontWeight.Medium)),
            fontSize = 18.sp
        )
    }
}