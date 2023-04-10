package com.d4rk.englishwithlidia.plus.ui.lessons
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.d4rk.englishwithlidia.plus.R
import com.d4rk.englishwithlidia.plus.databinding.FragmentLessonsBinding
import com.d4rk.englishwithlidia.plus.ui.viewmodel.ViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import me.zhanghai.android.fastscroll.FastScrollerBuilder
class LessonsFragment : Fragment(R.layout.fragment_lessons) {
    private var _binding: FragmentLessonsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        ViewModelProvider(this)[ViewModel::class.java]
        _binding = FragmentLessonsBinding.inflate(inflater, container, false)
        MobileAds.initialize(requireContext())
        binding.adView.loadAd(AdRequest.Builder().build())
        FastScrollerBuilder(binding.scrollView).useMd2Style().build()
        binding.lessonsLesson1Card.setOnClickListener {
            startActivity(Intent(activity, Lesson1Activity::class.java))
        }
        binding.lessonsLesson1Text.setOnClickListener {
            startActivity(Intent(activity, Lesson1Activity::class.java))
        }
        binding.lesson2Card.setOnClickListener {
            startActivity(Intent(activity, Lesson2Activity::class.java))
        }
        binding.lesson2Text.setOnClickListener {
            startActivity(Intent(activity, Lesson2Activity::class.java))
        }
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}