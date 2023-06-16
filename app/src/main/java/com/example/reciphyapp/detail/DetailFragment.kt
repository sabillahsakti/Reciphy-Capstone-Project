package com.example.reciphyapp.detail

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.BulletSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reciphyapp.R
import com.example.reciphyapp.database.DetailResponse
import com.example.reciphyapp.database.StringData
import com.example.reciphyapp.databinding.FragmentDetailBinding
import com.example.reciphyapp.databinding.FragmentRecommendBinding

class DetailFragment : Fragment() {
    private val viewModel: DetailViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tvDetail = view.findViewById<TextView>(R.id.tv_detailFragment)

        viewModel.steps.observe(viewLifecycleOwner, Observer { data ->
            tvDetail.text = data
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =
        inflater.inflate(R.layout.fragment_detail, container, false)

}