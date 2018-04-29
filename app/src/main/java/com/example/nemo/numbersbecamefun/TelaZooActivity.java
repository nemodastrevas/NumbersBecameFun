package com.example.nemo.numbersbecamefun;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TelaZooActivity extends AppCompatActivity {


    ArrayList<TelaZoo> mapa = new ArrayList<TelaZoo>();
    Button    btnOp1;
    Button    btnOp2;
    Button    btnOp3;
    EditText  ePergunta;
    ImageView imgNum;
    Integer jogadas = 0, acertos = 0, escolhido = 0, btnResposta = 0, btnClicado = 0;
    ArrayList<Integer> idTelasPassadas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_telazoo);
        btnOp1 =    (Button)    findViewById(R.id.btnOp1);
        btnOp2 =    (Button)    findViewById(R.id.btnOp2);
        btnOp3 =    (Button)    findViewById(R.id.btnOp3);
        ePergunta = (EditText)  findViewById(R.id.ePergunta);
        imgNum =    (ImageView) findViewById(R.id.imgNum);

        mapa.add(new TelaZoo(1, "Quantos leões há nessa imagem?",       R.drawable.a1));
        mapa.add(new TelaZoo(2, "Quantas zebras há nessa imagem?",      R.drawable.a2));
        mapa.add(new TelaZoo(3, "Quantas girafas há nessa imagem?",     R.drawable.a3));
        mapa.add(new TelaZoo(4, "Quantos macacos há nessa imagem?",     R.drawable.a4));
        mapa.add(new TelaZoo(5, "Quantos tigres há nessa imagem?",      R.drawable.a5));
        mapa.add(new TelaZoo(6, "Quantos camelos há nessa imagem?",     R.drawable.a6));
        mapa.add(new TelaZoo(7, "Quantos elefantes há nessa imagem?",   R.drawable.a7));
        mapa.add(new TelaZoo(8, "Quantos pinguins há nessa imagem?",    R.drawable.a8));
        mapa.add(new TelaZoo(9, "Quantos hipopótamos há nessa imagem?", R.drawable.a9));
        mapa.add(new TelaZoo(10,"Quantos suricatos há nessa imagem?",   R.drawable.a10));

        Intent it = getIntent();
        if (it != null) {
            Bundle paramsRetorno = it.getExtras();
            if (paramsRetorno != null) {
                jogadas = paramsRetorno.getInt("jogadas");
                acertos = paramsRetorno.getInt("acertos");
                if(jogadas == 5) {
                    Bundle paramsAcabou = new Bundle();
                    paramsAcabou.putString("Percentual", ((100/5)*(acertos))+"");
                    Intent itAcabou = new Intent(TelaZooActivity.this, ResultadoActivity.class);
                    itAcabou.putExtras(paramsAcabou);
                    startActivity(itAcabou);
                }
                idTelasPassadas =  paramsRetorno.getIntegerArrayList("idTelasPassadas");

                //Auxiliar para não causar uma ConcurrentModificationException
                ArrayList<TelaZoo> telasRemover = (ArrayList<TelaZoo>) mapa.clone();;

                for(Integer id : idTelasPassadas){
                    for(TelaZoo tela : telasRemover){
                        if(tela.getId() == id)
                            mapa.remove(tela);
                    }
                }
            }
        }

        Random r = new Random();
        escolhido = r.nextInt(mapa.size());
        imgNum.setImageResource(mapa.get(escolhido).getImg());
        ePergunta.setText(mapa.get(escolhido).getPergunta());

        btnResposta = r.nextInt(3);

        if(btnResposta == 0){
            while(btnOp1.getText().toString().equals(btnOp2.getText().toString()) || btnOp1.getText().toString().equals(btnOp3.getText().toString()) || btnOp2.getText().toString().equals(btnOp3.getText().toString())){
                btnOp1.setText(mapa.get(escolhido).getId()+"");
                btnOp2.setText(r.nextInt(mapa.size())+"");
                btnOp3.setText(r.nextInt(mapa.size())+"");
            }
        }
        else if(btnResposta == 1){
            while(btnOp1.getText().toString().equals(btnOp2.getText().toString()) || btnOp1.getText().toString().equals(btnOp3.getText().toString()) || btnOp2.getText().toString().equals(btnOp3.getText().toString())) {
                btnOp1.setText(r.nextInt(mapa.size()) + "");
                btnOp2.setText(mapa.get(escolhido).getId() + "");
                btnOp3.setText(r.nextInt(mapa.size()) + "");
            }
        }
        else{
            while(btnOp1.getText().toString().equals(btnOp2.getText().toString()) || btnOp1.getText().toString().equals(btnOp3.getText().toString()) || btnOp2.getText().toString().equals(btnOp3.getText().toString())) {
                btnOp1.setText(r.nextInt(mapa.size()) + "");
                btnOp2.setText(r.nextInt(mapa.size()) + "");
                btnOp3.setText(mapa.get(escolhido).getId() + "");
            }
        }
        btnOp1.setOnClickListener(click);
        btnOp2.setOnClickListener(click);
        btnOp3.setOnClickListener(click);
    }

    private Button.OnClickListener click = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            int id = view.getId();
            if (view.getId() == R.id.btnOp1)
                btnClicado = 0;
            else if (view.getId() == R.id.btnOp2)
                btnClicado = 1;
            else
                btnClicado = 2;
            AlertDialog.Builder builder = new AlertDialog.Builder(TelaZooActivity.this);
            if(btnResposta == btnClicado){
                builder.setMessage(R.string.msgAcerto)
                        .setTitle(R.string.titleAcerto)
                        .setPositiveButton(R.string.btnOk, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent it = new Intent(TelaZooActivity.this, TelaZooActivity.class);
                                Bundle paramsEntrada = new Bundle();
                                paramsEntrada.putInt("acertos", ++acertos);
                                paramsEntrada.putInt("jogadas", ++jogadas);
                                idTelasPassadas.add(mapa.get(escolhido).getId());
                                paramsEntrada.putIntegerArrayList("idTelasPassadas", idTelasPassadas);
                                it.putExtras(paramsEntrada);
                                startActivity(it);
                            }
                        });

            }else{
                builder.setMessage("Poxa, você errou! \n A opção correta era " + mapa.get(escolhido).getId()) //CONCATENANDO UMA FRASE NO STRING.XML COM O ID DAVA ERRO
                        .setTitle(R.string.titleErro)
                        .setPositiveButton(R.string.btnOk, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent it = new Intent(TelaZooActivity.this, TelaZooActivity.class);
                                Bundle paramsEntrada = new Bundle();
                                paramsEntrada.putInt("jogadas", ++jogadas);
                                paramsEntrada.putInt("acertos", acertos);
                                idTelasPassadas.add(mapa.get(escolhido).getId());
                                paramsEntrada.putIntegerArrayList("idTelasPassadas", idTelasPassadas);
                                it.putExtras(paramsEntrada);
                                startActivity(it);
                            }
                        });
            }
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    };
}
