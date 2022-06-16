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
class HomeFragment : Fragment(R.layout.fragment_lessons) {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        ViewModelProvider(this)[ViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.ourWebSiteBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://sites.google.com/view/englishwithlidia"))
            if (intent.resolveActivity(requireContext().packageManager) != null) {
                startActivity(intent)
            }
        }
        binding.findUsBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/lidia.melinte"))
            if (intent.resolveActivity(requireContext().packageManager) != null) {
                startActivity(intent)
            }
        }
        binding.lesson1Banner.setOnClickListener {
            val intent = Intent(activity, Lesson1Activity::class.java)
            startActivity(intent)
        }
        binding.lesson1Text.setOnClickListener {
            val intent = Intent(activity, Lesson1Activity::class.java)
            startActivity(intent)
        }
        binding.lesson2Card.setOnClickListener {
            val intent = Intent(activity, Lesson2Activity::class.java)
            startActivity(intent)
        }
        binding.lesson2Text.setOnClickListener {
            val intent = Intent(activity, Lesson2Activity::class.java)
            startActivity(intent)
        }
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}