package com.d4rk.englishwithlidia.plus.ui.settings.display.theme.style

import androidx.compose.material3.Typography
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)

fun String.annotatedStringHtmlParser(
    boldTag: String = "<b>",
    underlineTag: String = "<i>",
    italicTag: String = "\n",
) =
    buildAnnotatedString {
        val pattern =
            "(${Regex.escape(boldTag)}|${Regex.escape(underlineTag)}|${Regex.escape(italicTag)})(.*?)(\\1|$)".toRegex()
        var lastIndex = 0
        val text = this@annotatedStringHtmlParser
        pattern.findAll(text).forEach { result ->
            val (tag, content) = result.destructured
            append(text.substring(lastIndex, result.range.first))
            val start = length
            append(content)
            val end = length
            when (tag) {
                boldTag -> addStyle(
                    style = SpanStyle(fontWeight = FontWeight.Bold),
                    start = start,
                    end = end,
                )

                underlineTag -> addStyle(
                    style = SpanStyle(textDecoration = TextDecoration.Underline),
                    start = start,
                    end = end,
                )

                italicTag -> addStyle(
                    style = SpanStyle(fontStyle = FontStyle.Italic),
                    start = start,
                    end = end,
                )
            }
            lastIndex = result.range.last + 1
            if (tag != italicTag && lastIndex < text.length && text[lastIndex] == '\n') {
                lastIndex++
            }
        }
        append(text.substring(lastIndex, text.length))
    }