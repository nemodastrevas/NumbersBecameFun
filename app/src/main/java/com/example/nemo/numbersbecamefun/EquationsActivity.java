package com.example.nemo.numbersbecamefun;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class EquationsActivity extends AppCompatActivity {

    private TextView tvTitulo;
    private TextView tvEquacao;
    private EditText edtResposta;
    private Button btnConfirmar;
    private Integer jogadas = 0, acertos = 0, num1, num2;
    private Integer respCorreta;
    private String equacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equations);
        MainActivity.jogoAtual = 2;

        this.tvTitulo     = (TextView)findViewById(R.id.tvTitulo);
        this.tvEquacao    = (TextView)findViewById(R.id.tvEquacao);
        this.edtResposta  = (EditText) findViewById(R.id.edtResposta);
        this.btnConfirmar = (Button)findViewById(R.id.btnConfirmar);

        Intent it = getIntent();
        if (it != null) {
            Bundle paramsRetorno = it.getExtras();
            if (paramsRetorno != null) {
                jogadas = paramsRetorno.getInt("jogadas");
                acertos = paramsRetorno.getInt("acertos");

                //Valida o número total de jogadas para saber quando encerrar o jogo
                if(jogadas == 5) {
                    Bundle paramsAcabou = new Bundle();
                    paramsAcabou.putString("Percentual", ((100/5)*(acertos))+"");
                    Intent itAcabou = new Intent(EquationsActivity.this, ResultadoActivity.class);
                    itAcabou.putExtras(paramsAcabou);
                    startActivity(itAcabou);
                }
            }
        }

        this.tvTitulo.setText("\tQual é o resultado desta equação?");

        //Define a equação e a respectiva respCorreta
        for(int i = 0; i < 5; i++) {
            Random r = new Random();
            String operando;
            this.num1 = r.nextInt(10);
            this. num2 = r.nextInt(10);
            int opr = r.nextInt(2);

            //Valida para que os dois números não sejam 0
            if(this.num1 == 0 && this.num2 == 0)
                this.num1++;

            if(opr > 0) {
                this.respCorreta = this.num1 + this.num2;
                operando = "+";
            }
            else {
                //Valida para que não tenha resposta com números negativos
                if(this.num1 < this.num2)
                    this.respCorreta = this.num2 - this.num1;
                else
                    this.respCorreta = this.num1 - this.num2;

                operando = "-";
            }

            //Reordena exibição quando o resulta era para ser negativo
            if(this.num1 < this.num2)
                this.equacao  = String.valueOf(this.num2) + " " + operando + " " + String.valueOf(this.num1);
            else
                this.equacao  = String.valueOf(this.num1) + " " + operando + " " + String.valueOf(this.num2);
        }

        this.tvEquacao.setText(this.equacao);
    }

    public void validaRespota(View view) {
        try {
            int respInformada = Integer.parseInt(this.edtResposta.getText().toString());
            AlertDialog.Builder builder = new AlertDialog.Builder(EquationsActivity.this);

            if (respInformada == this.respCorreta) {
                builder.setMessage(R.string.msgAcerto)
                        .setTitle(R.string.titleAcerto)
                        .setPositiveButton(R.string.btnOk, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent it = new Intent(EquationsActivity.this, EquationsActivity.class);
                                Bundle paramsEntrada = new Bundle();
                                paramsEntrada.putInt("acertos", ++acertos);
                                paramsEntrada.putInt("jogadas", ++jogadas);
                                it.putExtras(paramsEntrada);
                                startActivity(it);
                            }
                        });
            } else {
                builder.setMessage("Poxa, você errou! \nA resposta correta era " + respCorreta) //CONCATENANDO UMA FRASE NO STRING.XML COM O ID DAVA ERRO
                        .setTitle(R.string.titleErro)
                        .setPositiveButton(R.string.btnOk, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent it = new Intent(EquationsActivity.this, EquationsActivity.class);
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
        catch (Exception e) {
            Toast.makeText(this, "Você deve informar algum número na resposta!", Toast.LENGTH_LONG).show();
        }
    }
}
