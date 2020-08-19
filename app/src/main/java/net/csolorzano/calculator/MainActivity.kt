package net.csolorzano.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var result : String = ""
    var lastOperator : String = ""
    var operand : Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.result = "0"
    }

    fun btnClicked(view: View) {
        val btnSource = view as Button
        val operation = btnSource.text.toString()


        when(operation){
            "AC" -> doClear()
            "+","-","×","÷","±","%","=" -> doOperator(operation)
            else -> doNumbers(operation)
        }
        txtResult.text = result
    }

    private fun doNumbers(number: String) {
        if(txtResult.text == "Error")
            return
        if(result == "0")
            result = number
        else if(number == "." && !result.contains("."))
            result += "."
        else if(number != ".")
            result += number
    }

    private fun doOperator(operator: String) {
        if(txtResult.text == "Error")
            return
        if(operator == "±"){
            result = (-result.toDouble()).toString()
            return
        }

        if(lastOperator != "") {
            doCalculation()
            if(result == "Error")
                return
        }
        else
            operand = result.toDouble();

        if(operator == "=")
        {
            lastOperator = ""
            result = "%.6f".format(operand)
        }else{
            lastOperator = operator
            result = "0"
        }

    }

    private fun doCalculation() {
        val newOperand = result.toDouble()
        when(lastOperator){
            "+"-> operand = operand + newOperand
            "-"-> operand = operand - newOperand
            "×" -> operand = operand * newOperand
            "÷" -> if (newOperand==0.0) {
                        result = "Error"
                        operand = 0.0
                    }
                    else
                        operand = operand / newOperand
            "±" -> operand = -newOperand
            "%" -> operand = operand * (newOperand/100.0)
        }
    }

    private fun doClear() {
        result = "0"
        operand = 0.0
        lastOperator = ""
    }
}