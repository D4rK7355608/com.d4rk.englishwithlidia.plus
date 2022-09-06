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
import me.zhanghai.android.fastscroll.FastScrollerBuilder

class LessonsFragment : Fragment(R.layout.fragment_lessons) {
    private var _binding: FragmentLessonsBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        ViewModelProvider(this)[ViewModel::class.java]
        _binding = FragmentLessonsBinding.inflate(inflater, container, false)
        FastScrollerBuilder(binding.lessonsScrollView).useMd2Style().build()
        binding.lessonsLesson1Card.setOnClickListener {
            val intent = Intent(activity, Lesson1Activity::class.java)
            startActivity(intent)
        }
        binding.lessonsLesson1Text.setOnClickListener {
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