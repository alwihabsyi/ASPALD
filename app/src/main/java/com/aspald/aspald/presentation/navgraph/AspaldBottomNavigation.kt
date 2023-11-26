package com.aspald.aspald.presentation.navgraph

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.aspald.aspald.R
import com.aspald.aspald.presentation.Dimens
import com.aspald.aspald.presentation.Dimens.IconSize
import com.aspald.aspald.ui.theme.AspaldOrange
import com.aspald.aspald.ui.theme.AspaldYellow

@Composable
fun AspaldBottomNavigation(
    items: List<BottomNavigationItem>,
    selected: Int,
    onItemClicked: (Int) -> Unit
) {
    BottomNavigation(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        backgroundColor = Color.White,
        elevation = 10.dp
    ) {
        items.forEachIndexed { index, item ->
            val isSelected = index == selected
            BottomNavigationItem(
                modifier = Modifier.background(Color.White),
                selected = isSelected,
                onClick = { onItemClicked(index) },
                icon = {
                    Column(
                        modifier = Modifier.background(Color.Transparent),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = null,
                            modifier = Modifier
                                .size(IconSize),
                            tint = if (isSelected) AspaldYellow else AspaldOrange
                        )
                        if (isSelected) {
                            Spacer(modifier = Modifier.height(Dimens.SmallPadding))
                            Text(
                                text = item.text,
                                style = MaterialTheme.typography.labelSmall,
                                color = AspaldYellow,
                                fontFamily = FontFamily(Font(R.font.poppins_medium, FontWeight.Medium))
                            )
                        }
                    }
                },
            )
        }
    }
}

data class BottomNavigationItem(
    val icon: Int,
    val text: String
)