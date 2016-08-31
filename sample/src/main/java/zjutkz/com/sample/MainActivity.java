package zjutkz.com.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import zjutkz.com.tracer.MethodTracer;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private TextView tv;
    private Button change;
    private Button delay;
    private Button recursion;

    private int recursionCount = 0;

    @Override
    @MethodTracer
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initEvent();
    }

    @MethodTracer
    private void initEvent() {
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeText();
            }
        });
        delay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delay();
            }
        });
        recursion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recursion();
            }
        });
    }

    @MethodTracer
    private void recursion() {
        if(recursionCount > 10){
            return;
        }

        Log.d(TAG, "execute recursion method: " + ++recursionCount);
        recursion();
    }

    @MethodTracer
    private void delay() {
        try {
            Thread.sleep(100);
            Log.d(TAG, "delay in thread!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @MethodTracer
    private void changeText() {
        tv.setText("change!");
    }

    @MethodTracer
    private void initView() {
        tv = (TextView)findViewById(R.id.tv);
        change = (Button)findViewById(R.id.change);
        delay = (Button)findViewById(R.id.delay);
        recursion = (Button)findViewById(R.id.recursion);
    }
}
