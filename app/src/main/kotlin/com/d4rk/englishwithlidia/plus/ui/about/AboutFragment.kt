package com.d4rk.englishwithlidia.plus.ui.about
import android.content.ClipData
import android.content.Intent
import android.content.Context
import android.content.ClipboardManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.d4rk.englishwithlidia.plus.BuildConfig
import com.d4rk.englishwithlidia.plus.R
import com.d4rk.englishwithlidia.plus.databinding.FragmentAboutBinding
import com.d4rk.englishwithlidia.plus.ui.viewmodel.ViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import me.zhanghai.android.fastscroll.FastScrollerBuilder
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Calendar
class AboutFragment : Fragment() {
    private lateinit var binding: FragmentAboutBinding
    private val calendar: Calendar = Calendar.getInstance()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        ViewModelProvider(this)[ViewModel::class.java]
        binding = FragmentAboutBinding.inflate(inflater, container, false)
        FastScrollerBuilder(binding.scrollView).useMd2Style().build()
        MobileAds.initialize(requireContext())
        binding.adView.loadAd(AdRequest.Builder().build())
        val version = String.format(resources.getString(R.string.app_version), BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE)
        binding.textViewAppVersion.text = version
        val simpleDateFormat = SimpleDateFormat("yyyy", Locale.getDefault())
        val dateText = simpleDateFormat.format(calendar.time)
        val copyright = requireContext().getString(R.string.copyright, dateText)
        binding.textViewCopyright.text = copyright
        binding.textViewAppVersion.setOnLongClickListener {
            val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipboard.setPrimaryClip(ClipData.newPlainText("Label", binding.textViewAppVersion.text))
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
                Toast.makeText(context, R.string.copied_to_clipboard, Toast.LENGTH_SHORT).show()
            }
            true
        }
        binding.imageViewAppIcon.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://sites.google.com/view/d4rk7355608")))
        }
        binding.chipGoogleDev.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://g.dev/D4rK7355608")))
        }
        binding.chipYoutube.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/c/D4rK7355608")))
        }
        binding.chipGithub.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/D4rK7355608/" + BuildConfig.APPLICATION_ID)))
        }
        binding.chipTwitter.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/D4rK7355608")))
        }
        binding.chipXda.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://forum.xda-developers.com/m/d4rk7355608.10095012")))
        }
        binding.chipMusic.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://sites.google.com/view/d4rk7355608/tracks")))
        }
        return binding.root
    }
}