package com.d4rk.englishwithlidia.plus.ui.home
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.d4rk.englishwithlidia.plus.R
import com.d4rk.englishwithlidia.plus.databinding.FragmentHomeBinding
import com.d4rk.englishwithlidia.plus.ui.lessons.Lesson1Activity
import com.d4rk.englishwithlidia.plus.ui.lessons.Lesson2Activity
import com.d4rk.englishwithlidia.plus.ui.viewmodel.ViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import me.zhanghai.android.fastscroll.FastScrollerBuilder
class HomeFragment : Fragment(R.layout.fragment_lessons) {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        ViewModelProvider(this)[ViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        MobileAds.initialize(requireContext())
        binding.adView.loadAd(AdRequest.Builder().build())
        FastScrollerBuilder(binding.scrollView).useMd2Style().build()
        binding.buttonWebSite.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://sites.google.com/view/englishwithlidia")))
        }
        binding.buttonFacebook.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/lidia.melinte")))
        }
        binding.cardViewFirstLesson.setOnClickListener {
            startActivity(Intent(activity, Lesson1Activity::class.java))
        }
        binding.textViewFirstLessonTitle.setOnClickListener {
            startActivity(Intent(activity, Lesson1Activity::class.java))
        }
        binding.cardViewSecondLesson.setOnClickListener {
            startActivity(Intent(activity, Lesson2Activity::class.java))
        }
        binding.textViewSecondLessonTitle.setOnClickListener {
            startActivity(Intent(activity, Lesson2Activity::class.java))
        }
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}