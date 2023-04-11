package com.yunuscagliyan.core_ui.theme

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.unit.dp
import javax.annotation.concurrent.Immutable

@Immutable
data class CinemaAppShapes(
    val defaultSmallShape: CornerBasedShape = RoundedCornerShape(6.dp),
    val defaultMediumShape: CornerBasedShape = RoundedCornerShape(8.dp),
    val defaultLargeShape: CornerBasedShape = RoundedCornerShape(10.dp),
    val bottomRoundedLargeShape: CornerBasedShape = RoundedCornerShape(
        bottomEnd = 16.dp,
        bottomStart = 16.dp
    ),
    val nonShape: CornerBasedShape = RoundedCornerShape(0.dp),
    val materialShapes: Shapes = Shapes()
) {
    val small: CornerBasedShape
        get() = materialShapes.small
    val medium: CornerBasedShape
        get() = materialShapes.medium
    val large: CornerBasedShape
        get() = materialShapes.large

    constructor(
        defaultSmallShape: CornerBasedShape,
        defaultMediumShape: CornerBasedShape,
        defaultLargeShape: CornerBasedShape,
        bottomRoundedLargeShape: CornerBasedShape,
        nonShape: CornerBasedShape,
        small: CornerBasedShape,
        medium: CornerBasedShape,
        large: CornerBasedShape
    ) : this(
        defaultSmallShape = defaultSmallShape,
        defaultMediumShape = defaultMediumShape,
        defaultLargeShape = defaultLargeShape,
        nonShape = nonShape,
        bottomRoundedLargeShape = bottomRoundedLargeShape,
        materialShapes = Shapes(
            small = small,
            medium = medium,
            large = large
        )
    )

    fun copy(
        defaultSmallShape: CornerBasedShape = this.defaultSmallShape,
        defaultMediumShape: CornerBasedShape = this.defaultMediumShape,
        defaultLargeShape: CornerBasedShape = this.defaultLargeShape,
        bottomRoundedLargeShape: CornerBasedShape = this.bottomRoundedLargeShape,
        nonShape: CornerBasedShape = this.nonShape,
        small: CornerBasedShape = this.small,
        medium: CornerBasedShape = this.medium,
        large: CornerBasedShape = this.large
    ) = CinemaAppShapes(
        defaultSmallShape = defaultSmallShape,
        defaultMediumShape = defaultMediumShape,
        defaultLargeShape = defaultLargeShape,
        bottomRoundedLargeShape = bottomRoundedLargeShape,
        nonShape = nonShape,
        small = small,
        medium = medium,
        large = large
    )
}

private val appShapes = CinemaAppShapes()

fun defaultAppShapes() = appShapes

fun appShapes() = appShapes