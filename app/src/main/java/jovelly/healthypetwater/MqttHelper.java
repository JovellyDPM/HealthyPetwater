package jovelly.healthypetwater;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

    public class MqttHelper {

        private final String TAG = MqttHelper.class.getSimpleName();

        private MqttAndroidClient mqttAndroidClient;

        public MqttHelper(Context context) {
            String serverUri = "tcp://healthypet.cloud.shiftr.io:1883";
            String clientId = UUID.randomUUID().toString();
            // mqtt://healthypet:2uy2REykfYIOVm5h@healthypet.cloud.shiftr.io
            mqttAndroidClient = new MqttAndroidClient(context, serverUri, clientId);
            mqttAndroidClient.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable cause) {
                    Log.d(TAG, "Connection lost");
                }

                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    Log.d(TAG, "Message arrived: " + new String(message.getPayload()));
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    Log.d(TAG, "Delivery complete");
                }
            });

            connect();
        }

        public void connect() {
            MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
            mqttConnectOptions.setAutomaticReconnect(true);
            mqttConnectOptions.setCleanSession(false);

            try {
                mqttAndroidClient.connect(mqttConnectOptions, null, new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        Log.d(TAG, "Conexión exitosa");

                        // Subscribe to topic
                        String topic = "Recordatorio para tomar agua";
                        int qos = 1;
                        try {
                            mqttAndroidClient.subscribe(topic, qos);
                        } catch (MqttException e) {
                            Log.d(TAG, "Suscripción fallida");
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        Log.d(TAG, "Conexión falló");
                        exception.printStackTrace();
                    }
                });
            } catch (MqttException e) {
                Log.d(TAG, "Connect exception");
                e.printStackTrace();
            }
        }

        public void publishMessage(String message) {
            String topic = "¡Toma awa! uwu";
            int qos = 1;
            byte[] payload = new byte[0];
            try {
                payload = message.getBytes("UTF-8");
                MqttMessage mqttMessage = new MqttMessage();
                mqttAndroidClient.publish(topic, mqttMessage);
            } catch (MqttException e) {
                Log.d(TAG, "Publish exception");
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                Log.d(TAG, "Unsupported encoding");
                e.printStackTrace();
            }
        }

        public void disconnect() {
            try {
                mqttAndroidClient.disconnect();
            } catch (MqttException e) {
                Log.d(TAG, "Disconnect exception");
                e.printStackTrace();
            }
        }
    }