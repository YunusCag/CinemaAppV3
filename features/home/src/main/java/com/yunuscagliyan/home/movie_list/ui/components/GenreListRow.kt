package com.yunuscagliyan.home.movie_list.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.yunuscagliyan.core.R
import com.yunuscagliyan.core.data.remote.model.genre.GenreModel
import com.yunuscagliyan.core.util.Constants.StringParameter.EMPTY_STRING
import com.yunuscagliyan.core_ui.components.toggle.ToggleButton

@Composable
fun GenreListRow(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    genres: List<GenreModel>,
    selectedIds: List<Int>,
    onSelected: (GenreModel, Boolean) -> Unit,
    onAllClicked: () -> Unit
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(
            start = 16.dp
        ),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (!isLoading && genres.isNotEmpty()) {
            item {
                ToggleButton(
                    label = stringResource(id = R.string.common_all),
                    isSelected = selectedIds.isEmpty(),
                    onClick = { _ ->
                        if (selectedIds.isNotEmpty()) {
                            onAllClicked()
                        }
                    },
                )
            }
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
            if (index != genres.size - 1) {
                Spacer(modifier = Modifier.width(16.dp))
            }
        }
    }
}