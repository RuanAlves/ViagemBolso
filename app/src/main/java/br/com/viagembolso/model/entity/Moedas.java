package br.com.viagembolso.model.entity;

import java.io.Serializable;

import br.com.viagembolso.interfaces.EntidadePersistivel;

/**
 * Created by Ruan Alves
 */
public class Moedas implements Serializable, EntidadePersistivel {

    private static final long serialVersionUID = -221105128135301277L;

    private String sigla;
    private double valor;
    private String descricao;

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public void setId(int id) {

    }

    @Override
    public int getId() {
        return 0;
    }
}
