package com.example.agecalculator

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var pickDateText: TextView? = null
    private var resultText: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn = findViewById<Button>(R.id.select_date_button)
        pickDateText = findViewById(R.id.pick_date_text)
        resultText = findViewById(R.id.result_text)

        btn.setOnClickListener {
            showDatePicker()
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance() // Javaのカレンダクラスのインスタンス取得
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // ダイアログ
        val dialog = DatePickerDialog(this, { _, _year, _month, dayOfMonth ->

            val selectedData = "$_year/${_month + 1}/$dayOfMonth"

            pickDateText?.text = selectedData

            val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.JAPANESE)
            val tDate = dateFormat.parse(selectedData)

            // ダイアログで指定したデータを分単位に変換
            // 先にnullの確認
            tDate?.let {

                // Date.timeプロパティは1970/1/1から経過した時間をミリ秒で格納している
                // 分に直すには、ミリ秒→秒に変換(/1000)、60秒→1分(/60)なので、60000で割る
                val selectedDataInMinutes = tDate.time / 60000

                // 現在の時間
                val currentDate = dateFormat.parse(dateFormat.format(System.currentTimeMillis()))
                currentDate?.let {
                    val currentDateInMinutes = currentDate.time / 60000

                    // 差分を計算
                    val deltaDateInMinutes = currentDateInMinutes - selectedDataInMinutes

                    // 値を更新
                    resultText?.text = deltaDateInMinutes.toString()
                }
            }
        }, 1990, 0, 1)

        // 設定できる日付の最大値を昨日にする
        dialog.datePicker.maxDate = System.currentTimeMillis() - 86400000 // 今日　-　1日の時間（ミリ秒）
        dialog.show()
    }
}