package stricklen.consulting.retailcalculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    val statesTaxMap = hashMapOf("UT" to 1.0685, "NV" to 1.08,
            "TX" to 1.0625, "AL" to 1.04, "CA" to 1.0825)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = ArrayAdapter( this,
                android.R.layout.simple_spinner_dropdown_item, statesTaxMap.keys.toTypedArray())
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        stateSelector.adapter = adapter
    }

    fun runCalculation(view: View) {
        val itemCountValue : Int = itemCount.text.toString().toInt()
        val itemCostValue: Double = itemCost.text.toString().toDouble()
        val orderValue: Double = itemCostValue * itemCountValue
        val orderValueWithTax = calculateTax(calculateDiscount(orderValue))
        val orderValueFormatted = String.format("%.2f", args = orderValueWithTax)
        orderTotalView.text = orderValueFormatted
    }

    fun calculateTax(pretaxValue: Double): Double =
            pretaxValue * statesTaxMap[stateSelector.selectedItem.toString()]!!

    fun calculateDiscount(prediscountValue: Double ): Double = when {

        (prediscountValue >= 50000) -> prediscountValue * 0.85
        (prediscountValue >= 10000) -> prediscountValue * 0.90
        (prediscountValue >= 7000 ) -> prediscountValue * 0.93
        (prediscountValue >= 5000 ) -> prediscountValue * 0.95
        (prediscountValue >= 1000 ) -> prediscountValue * 0.97
        else ->  prediscountValue
    }
}
