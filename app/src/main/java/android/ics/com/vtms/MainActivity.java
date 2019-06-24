package android.ics.com.vtms;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ImageView imgg;
    TextView txtt;
    LinearLayout lin;
    private int SPLASH_TIME_OUT = 2000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgg = (ImageView) findViewById(R.id.imgg);
        Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_top);
        imgg.startAnimation(animation2);
        txtt = (TextView) findViewById(R.id.txtt);
       /* Animation animation3 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slide_left);
        txtt.startAnimation(animation3);*/
        lin = (LinearLayout) findViewById(R.id.lin);

        new Handler().postDelayed(new Runnable() {
            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent intent = new Intent(MainActivity.this , LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

      /*  lin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });*/


}
