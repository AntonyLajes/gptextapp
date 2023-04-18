package com.nomargin.gptextapp.ui.view.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nomargin.gptextapp.databinding.FragmentHelpBinding

class HelpFragment : Fragment(), View.OnClickListener {

    private var _binding: FragmentHelpBinding? = null
    private val binding: FragmentHelpBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHelpBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        initClicks()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(view: View) {
        when(view.id){
            binding.buttonLinkedin.id -> {
                openLink("https://www.linkedin.com/in/antonylajes/")
            }
            binding.buttonGithub.id -> {
                openLink("https://github.com/AntonyLajes")
            }
        }
    }

    private fun initClicks(){
        binding.buttonGithub.setOnClickListener(this)
        binding.buttonLinkedin.setOnClickListener(this)
    }

    private fun openLink(pathUrl: String){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(pathUrl))
        startActivity(intent)
    }
}