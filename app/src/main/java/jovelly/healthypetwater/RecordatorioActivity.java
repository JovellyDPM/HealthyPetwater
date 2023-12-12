package jovelly.healthypetwater;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RecordatorioActivity extends AppCompatActivity {

    private MqttHelper mqttHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mqttHelper = new MqttHelper(this);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mqttHelper.disconnect();
    }

    public void publishMessage(View view) {
        mqttHelper.publishMessage("¡Toma awa! uwu");
    }


}