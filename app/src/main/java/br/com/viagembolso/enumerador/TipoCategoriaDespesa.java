package br.com.viagembolso.enumerador;


/**
 * Created by marco.
 */
public enum TipoCategoriaDespesa {

    TRANSPORTE("Transporte"),
    ALIMENTACAO("Alimentação"),
    HOSPEDAGEM("Hospedagem"),
    OUTROS("Outros");

    private String descricao;

    TipoCategoriaDespesa(String descricao) {
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
