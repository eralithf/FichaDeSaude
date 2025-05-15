package com.example.fichadesaude.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.fichadesaude.database.FichaDbHelper;
import com.example.fichadesaude.model.FichaSaude;
import com.example.fichadesaude.R;

import java.util.List;

public class HistoricoActivity extends Activity {

    private ListView listViewFichas;
    private FichaDbHelper dbHelper;
    private List<FichaSaude> listaFichas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

        listViewFichas = findViewById(R.id.listViewFichas);
        dbHelper = new FichaDbHelper(this);

        carregarFichas();

        listViewFichas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                FichaSaude ficha = listaFichas.get(position);
                Intent intent = new Intent(HistoricoActivity.this, VisualizacaoActivity.class);
                intent.putExtra("fichaId", ficha.getId());
                startActivity(intent);
            }
        });
    }

    private void carregarFichas() {
        listaFichas = dbHelper.getTodasFichas();


        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1);

        for (FichaSaude f : listaFichas) {
            String item = String.format("Nome: %s | Idade: %d | IMC: %.2f", f.getNome(), f.getIdade(), f.calcularIMC());
            adapter.add(item);
        }

        listViewFichas.setAdapter(adapter);
    }
}
