package com.studyhub.app

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ComputerSkillsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subject)

        findViewById<TextView>(R.id.tvSubjectName).text = getString(R.string.btn_computer_skills)

        findViewById<ImageButton>(R.id.btnBack).setOnClickListener {
            finish()
        }

        val rvLessons = findViewById<RecyclerView>(R.id.rvLessons)
        rvLessons.layoutManager = LinearLayoutManager(this)

        val lessons = listOf(
            Lesson(getString(R.string.comp_l1), R.raw.comp_l1, R.drawable.subject_icon),
            Lesson(getString(R.string.comp_l2), R.raw.comp_l2, R.drawable.subject_icon),
            Lesson(getString(R.string.comp_l3), R.raw.comp_l3, R.drawable.subject_icon),
            Lesson(getString(R.string.comp_l4), R.raw.comp_l4, R.drawable.subject_icon),
            Lesson(getString(R.string.comp_l5), R.raw.comp_l5, R.drawable.subject_icon)
        )
        rvLessons.adapter = LessonAdapter(lessons)
    }
}
