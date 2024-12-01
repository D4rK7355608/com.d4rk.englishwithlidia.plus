package com.d4rk.englishwithlidia.plus.ui.components.drawable

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.AdaptiveIconDrawable
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable

fun Drawable.toBitmapDrawable(resources: Resources = Resources.getSystem()): BitmapDrawable {
    return when (this) {
        is BitmapDrawable -> this
        is AdaptiveIconDrawable -> {
            val bitmap =
                Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            setBounds(0, 0, canvas.width, canvas.height)
            draw(canvas)
            BitmapDrawable(resources, bitmap)
        }

        else -> throw IllegalArgumentException("Unsupported drawable type: ${this::class.java.name}")
    }
}