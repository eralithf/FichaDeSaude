package com.example.fichadesaude.model;

public class FichaSaude {

    private long id;
    private String nome;
    private int idade;
    private double peso;
    private double altura;
    private String pressao;

    public FichaSaude(long id, String nome, int idade, double peso, double altura, String pressao) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.peso = peso;
        this.altura = altura;
        this.pressao = pressao;
    }

    public FichaSaude(String nome, int idade, double peso, double altura, String pressao) {
        this.nome = nome;
        this.idade = idade;
        this.peso = peso;
        this.altura = altura;
        this.pressao = pressao;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public String getPressao() {
        return pressao;
    }

    public void setPressao(String pressao) {
        this.pressao = pressao;
    }



    public double calcularIMC() {
        if (altura <= 0) return 0;
        return peso / (altura * altura);
    }

    public String interpretarIMC() {
        double imc = calcularIMC();
        if (imc < 18.5) return String.format("%.2f (Abaixo do peso)", imc);
        else if (imc < 25) return String.format("%.2f (Normal)", imc);
        else if (imc < 30) return String.format("%.2f (Sobrepeso)", imc);
        else return String.format("%.2f (Obesidade)", imc);
    }
}
