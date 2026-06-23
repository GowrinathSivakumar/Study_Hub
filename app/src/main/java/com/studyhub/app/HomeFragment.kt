package com.studyhub.app

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        view.findViewById<Button>(R.id.btnMathematics).setOnClickListener {
            startActivity(Intent(requireContext(), MathematicsActivity::class.java))
        }
        view.findViewById<Button>(R.id.btnScience).setOnClickListener {
            startActivity(Intent(requireContext(), ScienceActivity::class.java))
        }
        view.findViewById<Button>(R.id.btnEnglishCommunication).setOnClickListener {
            startActivity(Intent(requireContext(), EnglishCommunicationActivity::class.java))
        }
        view.findViewById<Button>(R.id.btnComputerSkills).setOnClickListener {
            startActivity(Intent(requireContext(), ComputerSkillsActivity::class.java))
        }
        view.findViewById<Button>(R.id.btnGeneralKnowledge).setOnClickListener {
            startActivity(Intent(requireContext(), GeneralKnowledgeActivity::class.java))
        }

        return view
    }
}
