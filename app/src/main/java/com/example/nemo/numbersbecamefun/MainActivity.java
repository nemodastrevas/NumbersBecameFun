package com.example.nemo.numbersbecamefun;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static int jogoAtual;
    ListView list;
    String[] jogo = {"NumberZoo", "Equations", "The Biggest Number"};
    Integer[] imageId = {R.drawable.numberzoo2,R.drawable.equations,R.drawable.thebiggestnumber};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListCell adapter = new ListCell(MainActivity.this,jogo,imageId);

        list = (ListView) findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle params = new Bundle();
                params.putString("Jogo", jogo[i]);
                params.putInt("image", imageId[i]);
                if (i == 0){
                    Intent it = new Intent(MainActivity.this, TelaZooActivity.class);
                    startActivity(it);
                }
                else if (i == 1){
                    Intent it = new Intent(MainActivity.this, EquationsActivity.class);
                    startActivity(it);
                }
                else if (i == 2){
                    Intent it = new Intent(MainActivity.this, TelaBiggestNumberActivity.class);
                    startActivity(it);
                }
                else
                    Toast.makeText(MainActivity.this,"Jogo não encontrado",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
