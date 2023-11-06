package com.example.gymapp.ui.screens.createworkout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gymapp.ui.quicksandMedium

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hintText: String = "",
    maxLines: Int = 1,
    keyboardType: KeyboardType = KeyboardType.Text,
    painterId : Int? = null,
    isDisabled : Boolean = false
) {
    BasicTextField(
        value = value,
        enabled = !isDisabled,
        onValueChange = { if (it.length <= 30) onValueChange(it) },
        maxLines = maxLines,
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = keyboardType),
        decorationBox = { innerTextField ->
            Box(modifier = modifier.padding(horizontal = 10.dp, vertical = 14.dp)) {
                if (value.isEmpty()) {
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = hintText,
                            color = Color.Gray.copy(alpha = 0.7f),
                            fontFamily = quicksandMedium,
                        )
                        painterId?.let { painterResource(it) }?.let {
                            Icon(
                                painter = it,
                                tint = Color(0xFF8F9BB3),
                                contentDescription = null,
                            )
                        }
                    }
                }
                innerTextField()
            }
        }
    )
}

@Preview
@Composable
fun CustomTextFieldPrev() {
    CustomTextField(
        value = "kazkas",
        onValueChange = {})
}