package sg.edu.rp.c346.id22001027.thebillcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;
import android.text.InputType;


public class MainActivity extends AppCompatActivity {

    EditText amountDisplay;
    EditText paxDisplay;
    ToggleButton toggleButtonsvsDisplay;
    ToggleButton toggleButtongstDisplay;
    TextView totalDisplay;
    TextView eachDisplay;
    Button buttonSplitDisplay;
    Button buttonResetDisplay;
    EditText discountDisplay;

    RadioGroup rg;
    RadioButton cashRadioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        amountDisplay = findViewById(R.id.amountDisplay);
        paxDisplay = findViewById(R.id.paxDisplay);
        toggleButtonsvsDisplay = findViewById(R.id.toggleButtonsvsDisplay);
        toggleButtongstDisplay = findViewById(R.id.toggleButtongstDisplay);
        discountDisplay = findViewById(R.id.discountDisplay);
        rg = findViewById(R.id.RadioGroup1);
        cashRadioButton = findViewById(R.id.radioButtonCashDisplay);
        buttonSplitDisplay = findViewById(R.id.buttonSplitDisplay);
        buttonResetDisplay = findViewById(R.id.buttonResetDisplay);
        totalDisplay = findViewById(R.id.totalDisplay);
        eachDisplay = findViewById(R.id.eachDisplay);

        // Set numerical keyboard for amount and pax input
        amountDisplay.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        paxDisplay.setInputType(InputType.TYPE_CLASS_NUMBER);


        // Set Cash RadioButton as default checked state
        cashRadioButton.setChecked(true);

        buttonSplitDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the user input values
                String userBill = amountDisplay.getText().toString();
                String userNOP = paxDisplay.getText().toString();
                String userDisc = discountDisplay.getText().toString();

                // Check if any input is empty
                if (userBill.isEmpty() || userNOP.isEmpty()) {
                    totalDisplay.setText("Please enter both the amount and number of people.");
                    totalDisplay.setTextColor(getResources().getColor(R.color.red));
                    eachDisplay.setText("");
                    return;
                }

                double bill = Double.parseDouble(userBill);
                double NOP = Double.parseDouble(userNOP);

                // Check if any input is invalid
                if (bill <= 0 || NOP <= 0) {
                    totalDisplay.setText("Invalid input. Amount and number of people must be greater than 0.");
                    totalDisplay.setTextColor(getResources().getColor(R.color.red));
                    eachDisplay.setText("");
                    return;
                }

                // Calculate the total bill
                double total = 0.0;
                if (toggleButtonsvsDisplay.isChecked() && toggleButtongstDisplay.isChecked()) {
                    total = bill * 1.17;
                } else if (!toggleButtonsvsDisplay.isChecked() && toggleButtongstDisplay.isChecked()) {
                    total = bill * 1.07;
                } else if (toggleButtonsvsDisplay.isChecked() && !toggleButtongstDisplay.isChecked()) {
                    total = bill * 1.1;
                } else {
                    total = bill;
                }
                // Calculate discount
                double discount = 0.0;
                if (!userDisc.isEmpty()) {
                    discount = Double.parseDouble(userDisc);
                }
                if (discount > 0) {
                    total = total * ((100 - discount) / 100);
                }

                // Calculate total and per person amount
                String paymentOption = cashRadioButton.isChecked() ? "Cash" : "PayNow";
                double perPersonAmount = total / NOP;
                String totalText = String.format("Total bill (%s): $%.2f", paymentOption, total);
                String eachText = String.format("Each person pays: $%.2f", perPersonAmount);

                // Display the results
                totalDisplay.setText(totalText);
                totalDisplay.setTextColor(getResources().getColor(R.color.green));
                eachDisplay.setText(eachText);
            }
        });

        buttonResetDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amountDisplay.setText("");
                paxDisplay.setText("");
                toggleButtonsvsDisplay.setChecked(false);
                toggleButtongstDisplay.setChecked(false);
                discountDisplay.setText("");
                cashRadioButton.setChecked(true);
                totalDisplay.setText("");
                eachDisplay.setText("");
            }
        });
    }
}
