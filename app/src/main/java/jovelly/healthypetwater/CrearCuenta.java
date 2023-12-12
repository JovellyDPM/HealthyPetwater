package jovelly.healthypetwater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CrearCuenta extends AppCompatActivity {

    EditText editTextEmail, editTextPassword;
    Button registrar_button;
    TextView login;
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);

        editTextEmail= findViewById(R.id.correo);
        editTextPassword = findViewById(R.id.pass);
        registrar_button = findViewById(R.id.registrar_button);
        login = findViewById(R.id.login);
        progressBar = findViewById(R.id.progressBar);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CrearCuenta.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        registrar_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email, password;
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(CrearCuenta.this, "Ingrese un correo", Toast.LENGTH_SHORT).show();
                    guardarTeclado();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(CrearCuenta.this, "Ingrese una contraseña", Toast.LENGTH_SHORT).show();
                    guardarTeclado();
                    return;
                }
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.VISIBLE);
                                if (task.isSuccessful()) {
                                    Toast.makeText(CrearCuenta.this, "Su cuenta se ha creado",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(CrearCuenta.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();


                                } else {

                                    Toast.makeText(CrearCuenta.this, "Error de autenticación ",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

            }
        });
    }
private void guardarTeclado (){
        View view = this.getCurrentFocus();
        if (view != null){
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
}
}