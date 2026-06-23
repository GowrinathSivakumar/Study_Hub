package com.studyhub.app

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GeneralKnowledgeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subject)

        findViewById<TextView>(R.id.tvSubjectName).text = getString(R.string.btn_general_knowledge)

        findViewById<ImageButton>(R.id.btnBack).setOnClickListener {
            finish()
        }

        val rvLessons = findViewById<RecyclerView>(R.id.rvLessons)
        rvLessons.layoutManager = LinearLayoutManager(this)

        val lessons = listOf(
            Lesson(getString(R.string.gk_l1), R.raw.gk_l1, R.drawable.subject_icon),
            Lesson(getString(R.string.gk_l2), R.raw.gk_l2, R.drawable.subject_icon),
            Lesson(getString(R.string.gk_l3), R.raw.gk_l3, R.drawable.subject_icon),
            Lesson(getString(R.string.gk_l4), R.raw.gk_l4, R.drawable.subject_icon),
            Lesson(getString(R.string.gk_l5), R.raw.gk_l5, R.drawable.subject_icon)
        )
        rvLessons.adapter = LessonAdapter(lessons)
    }
}
