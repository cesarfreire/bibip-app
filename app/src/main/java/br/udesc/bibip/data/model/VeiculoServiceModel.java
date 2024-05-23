package br.udesc.bibip.data.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.processing.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "placa",
        "modelo",
        "pendencias_financeiras",
        "valor",
        "cor",
        "multas"
})
@Generated("jsonschema2pojo")
public class VeiculoServiceModel {

    @JsonProperty("placa")
    public String placa;
    @JsonProperty("modelo")
    public String modelo;
    @JsonProperty("pendencias_financeiras")
    public float pendenciasFinanceiras;
    @JsonProperty("valor")
    public String valor;
    @JsonProperty("cor")
    public String cor;
    @JsonProperty("multas")
    public int multas;

    @JsonProperty("placa")
    public String getPlaca() {
        return placa;
    }

    @JsonProperty("placa")
    public void setPlaca(String placa) {
        this.placa = placa;
    }

    @JsonProperty("modelo")
    public String getModelo() {
        return modelo;
    }

    @JsonProperty("modelo")
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    @JsonProperty("pendencias_financeiras")
    public float getPendenciasFinanceiras() {
        return pendenciasFinanceiras;
    }

    @JsonProperty("pendencias_financeiras")
    public void setPendenciasFinanceiras(float pendenciasFinanceiras) {
        this.pendenciasFinanceiras = pendenciasFinanceiras;
    }

    @JsonProperty("valor")
    public String getValor() {
        return valor;
    }

    @JsonProperty("valor")
    public void setValor(String valor) {
        this.valor = valor;
    }

    @JsonProperty("cor")
    public String getCor() {
        return cor;
    }

    @JsonProperty("cor")
    public void setCor(String cor) {
        this.cor = cor;
    }

    @JsonProperty("multas")
    public int getMultas() {
        return multas;
    }

    @JsonProperty("multas")
    public void setMultas(int multas) {
        this.multas = multas;
    }


}