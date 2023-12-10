package app.sample.states

import app.sample.db.models.Sample

data class SampleState(
    val samples: List<Sample> = emptyList(),
    val title: String = ""
)