package Modelos;

public class Pessoa {
    protected String nome;

    public Pessoa(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String exibirInfo() {
        return "Nome: " + nome;
    }

    public String toString() {
        return "Pessoa: " + nome;
    }
}