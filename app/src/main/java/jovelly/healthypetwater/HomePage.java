package jovelly.healthypetwater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.media.RouteListingPreference;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomePage extends AppCompatActivity {

    Button incrementButton;
    Button decrementButton;
    Button backBtn;
    TextView countTextView;
    int count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        incrementButton = findViewById(R.id.suma_button);
        decrementButton = findViewById(R.id.resta_button);
        countTextView = findViewById(R.id.count_txt);
        backBtn = findViewById(R.id.back_button);

        count = 0;

        incrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                countTextView.setText(String.valueOf(count));
            }
        });

        decrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count--;
                countTextView.setText(String.valueOf(count));
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this,SuscripcionActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}