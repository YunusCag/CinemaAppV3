package com.yunuscagliyan.home.movie_list.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yunuscagliyan.core.data.remote.model.genre.GenreModel
import com.yunuscagliyan.core.util.Constants.StringParameter.EMPTY_STRING
import com.yunuscagliyan.core_ui.components.toggle.ToggleButton
import com.yunuscagliyan.core.R

@Composable
fun GenreListRow(
    modifier: Modifier = Modifier,
    genres: List<GenreModel>,
    selectedIds: List<Int>,
    onSelected: (GenreModel, Boolean) -> Unit,
    onAllClicked: () -> Unit
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Transparent),
        contentPadding = PaddingValues(
            start = 16.dp
        ),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            ToggleButton(
                label = stringResource(id = R.string.common_all),
                isSelected = selectedIds.isEmpty(),
                onClick = { _ ->
                    if(selectedIds.isNotEmpty()){
                        onAllClicked()
                    }
                },
            )
        }
        items(genres.size) { index ->
            val genre = genres[index]

            ToggleButton(
                label = genre.name ?: EMPTY_STRING,
                isSelected = selectedIds.any { it == genre.id },
                onClick = { isSelected ->
                    onSelected(genre, isSelected)
                }
            )
        }
    }
}