package com.example.androidpracticeapp_passwordmanager.views

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import com.example.androidpracticeapp_passwordmanager.R

class SampleTextProvider : PreviewParameterProvider<String> {
    override val values = sequenceOf(
        "Hello",
        "World",
        "Compose"
    )
}

@Composable
@Preview(showBackground = false)
fun SearchBarPreview(
    @PreviewParameter(SampleTextProvider::class) sampleText: String
) {
    SearchBar(sampleText, callback = { /* do nothing */ })
}

@Composable
fun SearchBar(text: String, callback: (String) -> Unit, modifier: Modifier = Modifier) {
    Box(modifier = modifier){
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp)
                .border(0.5.dp, colorResource(R.color.primary), RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .padding(vertical = 5.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(text, onValueChange = callback, textStyle = TextStyle(color = colorResource(R.color.text)))
                Icon(Icons.Filled.Search, tint = colorResource(R.color.text), contentDescription = null)
            }
        }}
}