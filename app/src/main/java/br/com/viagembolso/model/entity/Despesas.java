package br.com.viagembolso.model.entity;

import java.io.Serializable;
import java.util.Date;

import br.com.viagembolso.enumerador.TipoCategoriaDespesa;
import br.com.viagembolso.enumerador.TipoMoeda;
import br.com.viagembolso.interfaces.EntidadePersistivel;

/**
 * Created by Ruan Alves
 */

public class Despesas implements Serializable, EntidadePersistivel {

    private static final long serialVersionUID = -221105128135301277L;

    private int codigo;
    private String descricao;
    private double valor;
    private TipoMoeda moeda;
    private TipoCategoriaDespesa tipoCategoriaDespesa;
    private String dataDespesa;

    public Despesas(int codigo, String descricao, double valor, TipoMoeda moeda, TipoCategoriaDespesa tipoCategoriaDespesa, String data){

        this.codigo = codigo;
        this.descricao = descricao;
        this.valor = valor;
        this.moeda = moeda;
        this.tipoCategoriaDespesa = tipoCategoriaDespesa;
        this.dataDespesa = data;

    }

    public Despesas(){

    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public TipoMoeda getMoeda() {
        return moeda;
    }

    public void setMoeda(TipoMoeda moeda) {
        this.moeda = moeda;
    }

    public TipoCategoriaDespesa getTipoCategoriaDespesa() {
        return tipoCategoriaDespesa;
    }

    public void setTipoCategoriaDespesa(TipoCategoriaDespesa tipoCategoriaDespesa) {
        this.tipoCategoriaDespesa = tipoCategoriaDespesa;
    }

    public String getDataDespesa() {
        return dataDespesa;
    }

    public void setDataDespesa(String dataDespesa) {
        this.dataDespesa = dataDespesa;
    }

    @Override
    public void setId(int id) {

    }

    @Override
    public int getId() {
        return 0;
    }
}
