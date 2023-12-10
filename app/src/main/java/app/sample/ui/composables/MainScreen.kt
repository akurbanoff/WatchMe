package app.sample.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import app.sample.viewModels.SampleViewModel

@Composable
fun MainScreen(sampleViewModel: SampleViewModel) {
    Column {
        Text(text = "Main")
    }
}