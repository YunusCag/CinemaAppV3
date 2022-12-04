package com.yunuscagliyan.home.movie_list.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yunuscagliyan.core.data.remote.model.genre.GenreModel
import com.yunuscagliyan.core.util.Constants.StringParameter.EMPTY_STRING
import com.yunuscagliyan.core_ui.components.toggle.ToggleButton

@Composable
fun GenreListRow(
    modifier: Modifier = Modifier,
    genres: List<GenreModel>,
    selectedIds: List<Int>,
    onSelected: (GenreModel, Boolean) -> Unit,
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(
            start = 8.dp
        ),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(genres.size) { index ->
            val genre = genres[index]

            ToggleButton(
                label = genre.name ?: EMPTY_STRING,
                isSelected = selectedIds.any { it==genre.id },
                onClick = { isSelected ->
                    onSelected(genre, isSelected)
                }
            )
        }
    }
}