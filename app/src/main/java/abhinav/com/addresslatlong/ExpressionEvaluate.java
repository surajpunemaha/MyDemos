package abhinav.com.addresslatlong;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class ExpressionEvaluate extends AppCompatActivity implements View.OnClickListener
{
    EditText edit_expression;
    Button btn_evaluate;
    TextView txtv_result;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expression_evaluate);

        edit_expression = (EditText) findViewById(R.id.edit_expression);
        txtv_result = (TextView) findViewById(R.id.txtv_result);

        btn_evaluate = (Button) findViewById(R.id.btn_evaluate);
        btn_evaluate.setOnClickListener(this);

    }

    @Override
    public void onClick(View view)
    {
        if(view.getId()==R.id.btn_evaluate)
        {
            String expression = edit_expression.getText().toString().trim();
            if(expression.equals(""))
            {
                Toast.makeText(ExpressionEvaluate.this,
                        "Enter arithmetic expression",
                        Toast.LENGTH_SHORT).show();
            }
            else
            {
                try{
                    Expression e = new ExpressionBuilder(expression).build();
                    double result = e.evaluate();
                    txtv_result.setText(""+result);
                }catch (Exception e)
                {

                }
            }
        }
    }
}
