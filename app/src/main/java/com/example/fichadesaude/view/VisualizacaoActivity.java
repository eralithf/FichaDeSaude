package com.example.fichadesaude.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fichadesaude.R;
import com.example.fichadesaude.database.FichaDbHelper;
import com.example.fichadesaude.model.FichaSaude;

public class VisualizacaoActivity extends AppCompatActivity {

    private TextView txtNome, txtIdade, txtPeso, txtAltura, txtPressao, txtIMC, txtClassificacao;
    private Button btnEditar;

    private FichaDbHelper dbHelper;
    private long fichaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizacao);

        txtNome = findViewById(R.id.txtNome);
        txtIdade = findViewById(R.id.txtIdade);
        txtPeso = findViewById(R.id.txtPeso);
        txtAltura = findViewById(R.id.txtAltura);
        txtPressao = findViewById(R.id.txtPressao);
        txtIMC = findViewById(R.id.txtIMC);
        txtClassificacao = findViewById(R.id.txtClassificacao);
        btnEditar = findViewById(R.id.btnEditar);

        dbHelper = new FichaDbHelper(this);

        fichaId = getIntent().getLongExtra("fichaId", -1);

        if (fichaId != -1) {
            mostrarFicha(fichaId);
        } else {
            finish();
        }

        btnEditar.setOnClickListener(v -> {
            Intent intent = new Intent(VisualizacaoActivity.this, CadastroActivity.class);
            intent.putExtra("fichaId", fichaId);
            startActivity(intent);
            finish();
        });
    }

    private void mostrarFicha(long id) {
        FichaSaude ficha = dbHelper.getFichaById(id);
        if (ficha != null) {
            txtNome.setText("Nome: " + ficha.getNome());
            txtIdade.setText("Idade: " + ficha.getIdade());
            txtPeso.setText("Peso: " + ficha.getPeso() + " kg");
            txtAltura.setText("Altura: " + ficha.getAltura() + " m");
            txtPressao.setText("Pressão Arterial: " + ficha.getPressao());

            double imc = calcularIMC(ficha.getPeso(), ficha.getAltura());
            txtIMC.setText(String.format("IMC: %.2f", imc));
            txtClassificacao.setText("Classificação: " + classificarIMC(imc));
        }
    }

    private double calcularIMC(double peso, double altura) {
        if (altura <= 0) return 0;
        return peso / (altura * altura);
    }

    private String classificarIMC(double imc) {
        if (imc == 0) return "Inválido";
        if (imc < 18.5) return "Abaixo do peso";
        else if (imc < 25) return "Peso normal";
        else if (imc < 30) return "Sobrepeso";
        else return "Obesidade";
    }
}
