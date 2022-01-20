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
    private val averageLifeSpan: Int = 84

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
        // ダイアログ
        val dialog = DatePickerDialog(this, { _, _year, _month, dayOfMonth ->

            val selectedData = "$_year/${_month + 1}/$dayOfMonth"

            pickDateText?.text = selectedData

            val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.JAPANESE)
            val tDate = dateFormat.parse(selectedData)

            // ダイアログで指定したデータをhour単位に変換
            // 先にnullの確認
            tDate?.let {
                // 現在の時間
                val currentDate = dateFormat.parse(dateFormat.format(System.currentTimeMillis()))
                currentDate?.let {
                    val currentDateInMinutes = currentDate.time / 3600000

                    // 平均寿命を迎えるまでの時間
                    // 設定した日からちょうど84年後を寿命として計算
                    val aveLifeSpanDate =
                        dateFormat.parse("${_year + averageLifeSpan}/${_month + 1}/$dayOfMonth")
                    aveLifeSpanDate?.let {

                        // 寿命までの時間を変換
                        val aveLifeSpanDateInMinutes = aveLifeSpanDate.time / 3600000

                        // 差分を計算
                        val lifeSpanDateInMinutes = aveLifeSpanDateInMinutes - currentDateInMinutes
                        // 値を更新
                        resultText?.text =
                            getString(R.string.result_date, lifeSpanDateInMinutes.toString())
                    }
                }
            }
        }, 1990, 0, 1)

        // 設定できる日付の最大値を昨日にする
        dialog.datePicker.maxDate = System.currentTimeMillis() - 86400000 // 今日　-　1日の時間（ミリ秒）
        dialog.show()
    }
}