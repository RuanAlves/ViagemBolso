package br.com.viagembolso.enumerador;


/**
 * Created by marco.
 */
public enum TipoMoeda {

    BRL("Real"),
    EUR("Euro"),
    USD("Dolar");

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
