package com.example.fichadesaude.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.fichadesaude.database.FichaDbHelper;
import com.example.fichadesaude.model.FichaSaude;
import com.example.fichadesaude.R;

public class CadastroActivity extends Activity {

    private EditText edtNome, edtIdade, edtPeso, edtAltura, edtPressao;
    private Button btnSalvar;

    private FichaDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);


        edtNome = findViewById(R.id.edtNome);
        edtIdade = findViewById(R.id.edtIdade);
        edtPeso = findViewById(R.id.edtPeso);
        edtAltura = findViewById(R.id.edtAltura);
        edtPressao = findViewById(R.id.edtPressao);
        btnSalvar = findViewById(R.id.btnSalvar);

        dbHelper = new FichaDbHelper(this);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nome = edtNome.getText().toString().trim();
                String idadeStr = edtIdade.getText().toString().trim();
                String pesoStr = edtPeso.getText().toString().trim();
                String alturaStr = edtAltura.getText().toString().trim();
                String pressao = edtPressao.getText().toString().trim();

                if (nome.isEmpty() || idadeStr.isEmpty() || pesoStr.isEmpty() || alturaStr.isEmpty() || pressao.isEmpty()) {
                    Toast.makeText(CadastroActivity.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                    return;
                }

                int idade;
                double peso, altura;

                try {
                    idade = Integer.parseInt(idadeStr);
                    peso = Double.parseDouble(pesoStr);
                    altura = Double.parseDouble(alturaStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(CadastroActivity.this, "Informe valores numéricos válidos para idade, peso e altura!", Toast.LENGTH_SHORT).show();
                    return;
                }


                FichaSaude ficha = new FichaSaude(nome, idade, peso, altura, pressao);


                long id = dbHelper.inserirFicha(ficha);

                if (id > 0) {
                    Toast.makeText(CadastroActivity.this, "Ficha salva com sucesso!", Toast.LENGTH_SHORT).show();


                    edtNome.setText("");
                    edtIdade.setText("");
                    edtPeso.setText("");
                    edtAltura.setText("");
                    edtPressao.setText("");
                } else {
                    Toast.makeText(CadastroActivity.this, "Erro ao salvar ficha.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
