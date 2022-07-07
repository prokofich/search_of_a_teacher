package com.example.searchofateacher.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.searchofateacher.DayOfTheWeekActivity
import com.example.searchofateacher.R
import kotlinx.android.synthetic.main.fragment_timetable.*
/*
Фрагмент для расписания пользователя
 */

class TimetableFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timetable, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //ПЕРЕХОД НА ДЕНЬ НЕДЕЛИ/////////////////////////////////////////
        id_time_pn.setOnClickListener {
            val intent = Intent(context, DayOfTheWeekActivity::class.java)
            val flag = "monday"
            intent.putExtra("1", flag)
            startActivity(intent)
        }
        id_time_vt.setOnClickListener {
            val intent = Intent(context, DayOfTheWeekActivity::class.java)
            val flag = "tuesday"
            intent.putExtra("1", flag)
            startActivity(intent)
        }
        id_time_sr.setOnClickListener {
            val intent = Intent(context, DayOfTheWeekActivity::class.java)
            val flag = "wednesday"
            intent.putExtra("1", flag)
            startActivity(intent)
        }
        id_time_cht.setOnClickListener {
            val intent = Intent(context, DayOfTheWeekActivity::class.java)
            val flag = "thursday"
            intent.putExtra("1", flag)
            startActivity(intent)
        }
        id_time_pt.setOnClickListener {
            val intent = Intent(context, DayOfTheWeekActivity::class.java)
            val flag = "friday"
            intent.putExtra("1", flag)
            startActivity(intent)
        }
        id_time_sb.setOnClickListener {
            val intent = Intent(context, DayOfTheWeekActivity::class.java)
            val flag = "saturday"
            intent.putExtra("1", flag)
            startActivity(intent)
        }
        id_time_ws.setOnClickListener {
            val intent = Intent(context, DayOfTheWeekActivity::class.java)
            val flag = "sunday"
            intent.putExtra("1", flag)
            startActivity(intent)
        }
        ////////////////////////////////////////////////////////////////

    }
}