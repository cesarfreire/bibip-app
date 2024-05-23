package br.udesc.bibip.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "anotacao")
public class AnotacaoModel {
    @PrimaryKey(autoGenerate = true)
    @NotNull
    private int id;

    private String titulo;
    private String descricao;
    private String veiculoPlaca;

    public AnotacaoModel(String titulo, String descricao, String veiculoPlaca) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.veiculoPlaca = veiculoPlaca;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getVeiculoPlaca() {
        return veiculoPlaca;
    }

    @Override
    public String toString() {
        return "AnotacaoModel{" +
                "id='" + id + '\'' +
                ", titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
