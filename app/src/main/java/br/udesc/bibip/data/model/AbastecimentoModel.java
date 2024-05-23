package br.udesc.bibip.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.Date;

@Entity(tableName = "abastecimento")
public class AbastecimentoModel {
    @PrimaryKey(autoGenerate = true)
    private int id = 0;

    private long quilometragem;
    private float litros;
    private float valor;
    private Date data;
    private String veiculo_placa;
    private long km_rodado;

    public AbastecimentoModel(long quilometragem, float litros, float valor, Date data, String veiculo_placa) {
        this.quilometragem = quilometragem;
        this.litros = litros;
        this.valor = valor;
        this.data = data;
        this.veiculo_placa = veiculo_placa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getQuilometragem() {
        return quilometragem;
    }

    public float getLitros() {
        return litros;
    }

    public float getValor() {
        return valor;
    }

    public Date getData() {
        return data;
    }

    public String getVeiculo_placa() {
        return veiculo_placa;
    }

    public long getKm_rodado() {
        return km_rodado;
    }

    public void setKm_rodado(long km_rodado) {
        this.km_rodado = km_rodado;
    }

    public long getMedia() {
        return km_rodado / (long) litros;
    }

    @Override
    public String toString() {
        return "AbastecimentoModel{" +
                "id=" + id +
                ", quilometragem=" + quilometragem +
                ", litros=" + litros +
                ", valor=" + valor +
                ", data=" + data +
                ", veiculo_placa='" + veiculo_placa + '\'' +
                ", km_rodado=" + km_rodado +
                '}';
    }
}
