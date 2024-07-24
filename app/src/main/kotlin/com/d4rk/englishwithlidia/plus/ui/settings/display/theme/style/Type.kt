package com.d4rk.englishwithlidia.plus.ui.settings.display.theme.style

import androidx.compose.material3.Typography
import androidx.compose.ui.text.AnnotatedString
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
    underlineTag: String = "<u>",
    italicTag: String = "<i>",
): AnnotatedString = buildAnnotatedString {
    val pattern =
        "(${Regex.escape(boldTag)}|${Regex.escape(underlineTag)}|${Regex.escape(italicTag)})(.*?)(\\1|$)".toRegex()
    var lastIndex = 0
    val text = this@annotatedStringHtmlParser.replace("\\n", "\n")

    pattern.findAll(text).forEach { result ->
        val (tag, content) = result.destructured
        append(text.substring(lastIndex, result.range.first))

        val start = length
        append(content)
        val end = length

        when (tag) {
            boldTag -> addStyle(SpanStyle(fontWeight = FontWeight.Bold), start, end)
            underlineTag -> addStyle(
                SpanStyle(textDecoration = TextDecoration.Underline),
                start,
                end
            )

            italicTag -> addStyle(SpanStyle(fontStyle = FontStyle.Italic), start, end)
        }

        lastIndex = result.range.last + 1
        if (tag != italicTag && lastIndex < text.length && text[lastIndex] == '\n') {
            lastIndex++
        }
    }

    append(text.substring(lastIndex, text.length))
}