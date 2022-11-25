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
        nonShape: CornerBasedShape,
        small: CornerBasedShape,
        medium: CornerBasedShape,
        large: CornerBasedShape
    ) : this(
        defaultSmallShape = defaultSmallShape,
        defaultMediumShape = defaultMediumShape,
        defaultLargeShape = defaultLargeShape,
        nonShape = nonShape,
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
        nonShape: CornerBasedShape = this.nonShape,
        small: CornerBasedShape = this.small,
        medium: CornerBasedShape = this.medium,
        large: CornerBasedShape = this.large
    ) = CinemaAppShapes(
        defaultSmallShape = defaultSmallShape,
        defaultMediumShape = defaultMediumShape,
        defaultLargeShape = defaultLargeShape,
        nonShape = nonShape,
        small = small,
        medium = medium,
        large = large
    )
}

private val appShapes = CinemaAppShapes()

fun defaultAppShapes() = appShapes

fun appShapes() = appShapes

val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(6.dp),
    large = RoundedCornerShape(8.dp),
)