package com.d4rk.englishwithlidia.plus.ui.components.buttons

import android.content.Context
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.d4rk.englishwithlidia.plus.utils.IntentUtils

@Composable
fun OutlinedUrlButtons(
    url : String ,
    vectorIcon : ImageVector? = null ,
    painterIcon : Painter? = null,
    text : Int ,
    context : Context ,
    modifier : Modifier
) {
    OutlinedButton(onClick = {
        IntentUtils.openUrl(
            context , url = url
        )
    } , modifier = modifier) {

        if (painterIcon != null) {
            Icon(
                painter = painterIcon ,
                contentDescription = null ,
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
        }
        else if (vectorIcon != null) {
            Icon(
                imageVector = vectorIcon ,
                contentDescription = null ,
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
        }

        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text(stringResource(id = text))
    }
}