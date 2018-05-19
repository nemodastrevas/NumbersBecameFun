package com.example.nemo.numbersbecamefun;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ResultadoActivity extends AppCompatActivity {

    TextView eResultado;
    Button btnMenu;
    Button btnJogar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        eResultado = findViewById(R.id.eResultado);
        btnJogar = findViewById(R.id.btnJogar);
        btnMenu = findViewById(R.id.btnMenu);

        Intent it = getIntent();
        if (it != null) {
            Bundle paramsRetorno = it.getExtras();
            if (paramsRetorno != null) {
                String texto = "Sua nota foi: " + paramsRetorno.getString("Percentual");
                if(Integer.parseInt(paramsRetorno.getString("Percentual")) <= 40)
                    texto = texto + "\nNão desista! tente mais uma vez!";
                else if (Integer.parseInt(paramsRetorno.getString("Percentual")) <= 80)
                    texto = texto + "\nFoi por pouco!";
                else
                    texto = texto + "\nMuito bem! você já dominou os números";
                eResultado.setText(texto);
            }
        }

        btnMenu.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(ResultadoActivity.this, MainActivity.class);
                startActivity(it);
            }
        });

        btnJogar.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it;
                if(MainActivity.jogoAtual == 1){
                    it = new Intent(ResultadoActivity.this, TelaZooActivity.class);
                }else if(MainActivity.jogoAtual == 2){
                    it = new Intent(ResultadoActivity.this, EquationsActivity.class);
                }else{
                    it = new Intent(ResultadoActivity.this, TelaBiggestNumberActivity.class);
                }

                startActivity(it);
            }
        });
    }
}
