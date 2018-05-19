package com.example.nemo.numbersbecamefun;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class TelaBiggestNumberActivity extends AppCompatActivity {

    Button    btnOp1;
    EditText  ePergunta;
    Integer jogadas = 0, acertos = 0, respostaCorreta=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_biggest_number);

        MainActivity.jogoAtual = 3;
        btnOp1 =    (Button)    findViewById(R.id.btnOp1);
        ePergunta = (EditText)  findViewById(R.id.ePergunta);
        ePergunta.setText("Qual é o maior número que pode ser formado por estes três algarismos?");

        TextView alg1 = (TextView) findViewById(R.id.alg1);
        TextView alg2 = (TextView) findViewById(R.id.alg2);
        TextView alg3 = (TextView) findViewById(R.id.alg3);

        Intent it = getIntent();
        if (it != null) {
            Bundle paramsRetorno = it.getExtras();
            if (paramsRetorno != null) {
                jogadas = paramsRetorno.getInt("jogadas");
                acertos = paramsRetorno.getInt("acertos");
                if(jogadas == 5) {
                    Bundle paramsAcabou = new Bundle();
                    paramsAcabou.putString("Percentual", ((100/5)*(acertos))+"");
                    Intent itAcabou = new Intent(TelaBiggestNumberActivity.this, ResultadoActivity.class);
                    itAcabou.putExtras(paramsAcabou);
                    startActivity(itAcabou);
                }
            }
        }

        Random r = new Random();
        int num1 = r.nextInt(9);
        int num2 = r.nextInt(9);
        int num3 = r.nextInt(9);
        alg1.setText(String.valueOf(num1));
        alg2.setText(String.valueOf(num2));
        alg3.setText(String.valueOf(num3));

        int combinacoes[] = {
                Integer.parseInt(String.valueOf(num1) + String.valueOf(num2) + String.valueOf(num3)),
                Integer.parseInt(String.valueOf(num1) + String.valueOf(num3) + String.valueOf(num2)),
                Integer.parseInt(String.valueOf(num2) + String.valueOf(num1) + String.valueOf(num3)),
                Integer.parseInt(String.valueOf(num2) + String.valueOf(num3) + String.valueOf(num1)),
                Integer.parseInt(String.valueOf(num3) + String.valueOf(num1) + String.valueOf(num2)),
                Integer.parseInt(String.valueOf(num3) + String.valueOf(num2) + String.valueOf(num1))
        };

        respostaCorreta = combinacoes[0];
        for (int i = 1; i < combinacoes.length; i++) {
            if (combinacoes[i] > respostaCorreta) {
                respostaCorreta = combinacoes[i];
            }
        }

        btnOp1.setOnClickListener(click);
    }

    private Button.OnClickListener click = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(TelaBiggestNumberActivity.this);
            EditText resposta = (EditText)findViewById(R.id.resposta);
            if(resposta.getText().toString().length() != 0){
                int respostaUsuario = Integer.parseInt(resposta.getText().toString());

                if (respostaUsuario == respostaCorreta) {
                    builder.setMessage(R.string.msgAcerto)
                            .setTitle(R.string.titleAcerto)
                            .setPositiveButton(R.string.btnOk, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent it = new Intent(TelaBiggestNumberActivity.this, TelaBiggestNumberActivity.class);
                                    Bundle paramsEntrada = new Bundle();
                                    paramsEntrada.putInt("acertos", ++acertos);
                                    paramsEntrada.putInt("jogadas", ++jogadas);
                                    it.putExtras(paramsEntrada);
                                    startActivity(it);
                                }
                            });
                } else {
                    builder.setMessage("Poxa, você errou! \n A resposta correta era " + respostaCorreta) //CONCATENANDO UMA FRASE NO STRING.XML COM O ID DAVA ERRO
                            .setTitle(R.string.titleErro)
                            .setPositiveButton(R.string.btnOk, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    Intent it = new Intent(TelaBiggestNumberActivity.this, TelaBiggestNumberActivity.class);
                                    Bundle paramsEntrada = new Bundle();
                                    paramsEntrada.putInt("jogadas", ++jogadas);
                                    paramsEntrada.putInt("acertos", acertos);
                                    it.putExtras(paramsEntrada);
                                    startActivity(it);
                                }
                            });
                }
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
    };
}
