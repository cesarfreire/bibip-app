package br.udesc.bibip.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "veiculo")
public class VeiculoModel {
    @PrimaryKey
    @NotNull
    private String placa;
    private String apelido;
    private String modelo;
    private String tipo;
    private String usuario_id;
    private long quilometragem;

    public VeiculoModel(@NotNull String placa, String apelido, String modelo, String tipo, String usuario_id) {
        this.placa = placa;
        this.apelido = apelido;
        this.modelo = modelo;
        this.tipo = tipo;
        this.usuario_id = usuario_id;
    }

    public String getPlaca() {
        return placa;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUsuario_id() {
        return usuario_id;
    }

    public long getQuilometragem() {
        return quilometragem;
    }

    public void setQuilometragem(long quilometragem) {
        this.quilometragem = quilometragem;
    }

    @Override
    public String toString() {
        return "VeiculoModel{" +
                "placa='" + placa + '\'' +
                ", apelido='" + apelido + '\'' +
                ", modelo='" + modelo + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}

