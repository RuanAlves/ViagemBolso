package br.com.viagembolso.enumerador;


/**
 * Created by Ruan Alves
 */
public enum TipoMoeda {

    REAL("Real"),
    EURO("Euro"),
    DOLAR("Dolar");

    private String descricao;

    TipoMoeda(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return this.getDescricao();
    }

}
