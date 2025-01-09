package com.pablodev.shamovie.core.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pablodev.shamovie.core.util.MediaKey
import com.pablodev.shamovie.ui.theme.Background
import com.pablodev.shamovie.ui.theme.Greenish

@Composable
fun RadioButtonGroup(
    selectedOption: MediaKey,
    onOptionSelected: (MediaKey) -> Unit,
    modifier: Modifier = Modifier
) {
    val options = MediaKey.entries
    Row(
        modifier = modifier
            .background(
                color = Greenish, // Background color
                shape = RoundedCornerShape(8.dp) // Rounded corners
            )
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        options.forEach { option ->
            val isSelected = selectedOption == option
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp, vertical = 2.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        color = if (isSelected) Background else Color.Transparent
                    )
                    .clickable { onOptionSelected(option) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = option.label, // Show the label
                    color = if (isSelected) Color.Black else Color.White,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}
