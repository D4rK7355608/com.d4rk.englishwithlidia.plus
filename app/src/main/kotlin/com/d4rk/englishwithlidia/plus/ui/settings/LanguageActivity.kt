package com.d4rk.englishwithlidia.plus.ui.settings
import android.os.Bundle
import android.widget.RadioGroup
import com.d4rk.englishwithlidia.plus.R
import com.kieronquinn.monetcompat.app.MonetCompatActivity
import com.kieronquinn.monetcompat.view.MonetSwitch
class LanguageActivity : MonetCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language)
        val languageGroup = findViewById<RadioGroup>(R.id.languageGroup)
        val defaultLanguage = findViewById<MonetSwitch>(R.id.defaultLanguage)
        val sharedPreferences = getSharedPreferences("save", MODE_PRIVATE)
        defaultLanguage.isChecked = sharedPreferences.getBoolean("value", true)
        val checkRefreshDefaultLanguage: Boolean? = null
        if (checkRefreshDefaultLanguage == false) {
            val editor = getSharedPreferences("save", MODE_PRIVATE).edit()
            editor.putBoolean("value", false)
            editor.apply()
            defaultLanguage.isChecked = false
            for (i in 0 until languageGroup.childCount) {
                languageGroup.getChildAt(i).isEnabled = true
            }
        } else {
            val editor = getSharedPreferences("save", MODE_PRIVATE).edit()
            editor.putBoolean("value", true)
            editor.apply()
            defaultLanguage.isChecked = true
            for (i in 0 until languageGroup.childCount) {
                languageGroup.getChildAt(i).isEnabled = false
            }
        }
        defaultLanguage.setOnCheckedChangeListener { _, _ ->
            if (defaultLanguage.isChecked)
            {
                val editor = getSharedPreferences("save", MODE_PRIVATE).edit()
                editor.putBoolean("value", true)
                editor.apply()
                defaultLanguage.isChecked = true
                for (i in 0 until languageGroup.childCount) {
                    languageGroup.getChildAt(i).isEnabled = false
                }
            } else {
                val editor = getSharedPreferences("save", MODE_PRIVATE).edit()
                editor.putBoolean("value", false)
                editor.apply()
                defaultLanguage.isChecked = false
                for (i in 0 until languageGroup.childCount) {
                    languageGroup.getChildAt(i).isEnabled = true
                }
            }
        }
    }
}