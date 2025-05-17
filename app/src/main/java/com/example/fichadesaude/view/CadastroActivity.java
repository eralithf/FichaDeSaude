package com.example.fichadesaude.view;

import android.app.Activity;
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
    private long fichaId = -1; // Para editar, receber o id

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


        fichaId = getIntent().getLongExtra("fichaId", -1);
        if (fichaId != -1) {
            carregarFichaParaEdicao(fichaId);
        }

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

                if (fichaId == -1) {

                    long id = dbHelper.inserirFicha(ficha);
                    if (id > 0) {
                        Toast.makeText(CadastroActivity.this, "Ficha salva com sucesso!", Toast.LENGTH_SHORT).show();
                        limparCampos();
                    } else {
                        Toast.makeText(CadastroActivity.this, "Erro ao salvar ficha.", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    ficha.setId(fichaId); // importante para atualizar pelo id
                    int linhasAfetadas = dbHelper.atualizarFicha(ficha);
                    if (linhasAfetadas > 0) {
                        Toast.makeText(CadastroActivity.this, "Ficha atualizada com sucesso!", Toast.LENGTH_SHORT).show();
                        finish(); // volta para a tela anterior
                    } else {
                        Toast.makeText(CadastroActivity.this, "Erro ao atualizar ficha.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void carregarFichaParaEdicao(long id) {
        FichaSaude ficha = dbHelper.getFichaById(id);
        if (ficha != null) {
            edtNome.setText(ficha.getNome());
            edtIdade.setText(String.valueOf(ficha.getIdade()));
            edtPeso.setText(String.valueOf(ficha.getPeso()));
            edtAltura.setText(String.valueOf(ficha.getAltura()));
            edtPressao.setText(ficha.getPressao());
        }
    }

    private void limparCampos() {
        edtNome.setText("");
        edtIdade.setText("");
        edtPeso.setText("");
        edtAltura.setText("");
        edtPressao.setText("");
    }
}
