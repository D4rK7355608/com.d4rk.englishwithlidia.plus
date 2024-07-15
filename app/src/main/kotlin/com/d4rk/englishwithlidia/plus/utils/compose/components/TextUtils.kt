package com.d4rk.englishwithlidia.plus.utils.compose.components

import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration

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