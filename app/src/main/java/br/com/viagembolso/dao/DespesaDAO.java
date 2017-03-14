package br.com.viagembolso.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import java.util.ArrayList;
import java.util.List;

import br.com.viagembolso.enumerador.TipoCategoriaDespesa;
import br.com.viagembolso.enumerador.TipoMoeda;
import br.com.viagembolso.model.entity.Despesas;
import br.com.viagembolso.model.entity.Moedas;
import br.com.viagembolso.utilis.Utils;

/**
 * Created by Ruan Alves
 */
public class DespesaDAO extends GenericDAO<Despesas> {

    private static final String TAG = "DESPESA_DAO";
    private static final String NOME_TABELA = "DESPESA";
    public static final String SCRIPT_CRIACAO_TABELA = "CREATE TABLE  IF NOT EXISTS " + NOME_TABELA + " ([CODIGO] INTEGER PRIMARY KEY AUTOINCREMENT, [DESCRICAO] TEXT , [VALOR] REAL," +
            "[MOEDA] TEXT, [CATEGORIA] TEXT, [DATEDESPESA] TEXT)";
    public static final String SCRIPT_DELECAO_TABELA = "DROP TABLE IF EXISTS " + NOME_TABELA;

    private static final String COLUNA_CODIGO = "CODIGO";
    private static final String COLUNA_DESCRICAO = "DESCRICAO";
    private static final String COLUNA_VALOR = "VALOR";
    private static final String COLUNA_MOEDA = "MOEDA";
    private static final String COLUNA_CATEGORIA = "CATEGORIA";
    private static final String COLUNA_DATEDESPESA = "DATEDESPESA";

    public DespesaDAO(Context context) {
        super(context);
    }

    @Override
    public String getNomeColunaPrimaryKey() {
        return COLUNA_CODIGO;
    }

    @Override
    public String getNomeTabela() {
        return NOME_TABELA;
    }

    @Override
    public ContentValues entidadeParacontentValues(Despesas despesa) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUNA_DESCRICAO, despesa.getDescricao());
        contentValues.put(COLUNA_VALOR, despesa.getValor());
        contentValues.put(COLUNA_MOEDA, despesa.getMoeda().toString());
        contentValues.put(COLUNA_CATEGORIA, despesa.getTipoCategoriaDespesa().toString());
        contentValues.put(COLUNA_DATEDESPESA, despesa.getDataDespesa());

        return contentValues;
    }

    @Override
    public Despesas contentValuesParaEntidade(ContentValues contentValues) {
        return null;
    }

    public List<Despesas> buscarDespesas() throws SQLException {

        String sql = "SELECT [CODIGO],[DESCRICAO],[VALOR],[CATEGORIA],[DATEDESPESA], [MOEDA] FROM " + NOME_TABELA + "";
        Cursor cursor = null;
        List<Despesas> resultados = new ArrayList<>();

        try {

            cursor = dataBase.rawQuery(sql, null);

            while (cursor.moveToNext()) {
                Despesas result = new Despesas();
                result.setId(cursor.getInt(0));
                result.setDescricao(cursor.getString(1));
                result.setValor(cursor.getDouble(2));
                result.setDataDespesa(cursor.getString(4));

                if(cursor.getString(3).equalsIgnoreCase(TipoCategoriaDespesa.ALIMENTACAO.toString())) {
                    result.setTipoCategoriaDespesa(TipoCategoriaDespesa.ALIMENTACAO);
                } else if(cursor.getString(3).equalsIgnoreCase(TipoCategoriaDespesa.HOSPEDAGEM.toString())) {
                    result.setTipoCategoriaDespesa(TipoCategoriaDespesa.HOSPEDAGEM);
                } else if(cursor.getString(3).equalsIgnoreCase(TipoCategoriaDespesa.TRANSPORTE.toString())) {
                    result.setTipoCategoriaDespesa(TipoCategoriaDespesa.TRANSPORTE);
                } else {
                    result.setTipoCategoriaDespesa(TipoCategoriaDespesa.OUTROS);
                }

                if(cursor.getString(5).equalsIgnoreCase(TipoMoeda.BRL.toString())) {
                    result.setMoeda(TipoMoeda.BRL);
                } else if(cursor.getString(5).equalsIgnoreCase(TipoMoeda.EUR.toString())) {
                    result.setMoeda(TipoMoeda.EUR);
                } else if(cursor.getString(5).equalsIgnoreCase(TipoMoeda.USD.toString())) {
                    result.setMoeda(TipoMoeda.USD);
                }

                resultados.add(result);
            }

        } finally {
            cursor.close();
        }

        return resultados;
    }

    public List<Despesas> buscarDespesaGroupCategoria() throws SQLException {

        String sql = "SELECT SUM([VALOR]),[CATEGORIA] FROM " + NOME_TABELA + " GROUP BY [CATEGORIA]";
        Cursor cursor = null;
        List<Despesas> resultados = new ArrayList<>();

        try {

            cursor = dataBase.rawQuery(sql, null);

            while (cursor.moveToNext()) {
                Despesas result = new Despesas();
                result.setValor(cursor.getDouble(0));

                if(cursor.getString(1).equalsIgnoreCase(TipoCategoriaDespesa.ALIMENTACAO.toString())) {
                    result.setTipoCategoriaDespesa(TipoCategoriaDespesa.ALIMENTACAO);
                } else if(cursor.getString(1).equalsIgnoreCase(TipoCategoriaDespesa.HOSPEDAGEM.toString())) {
                    result.setTipoCategoriaDespesa(TipoCategoriaDespesa.HOSPEDAGEM);
                } else if(cursor.getString(1).equalsIgnoreCase(TipoCategoriaDespesa.TRANSPORTE.toString())) {
                    result.setTipoCategoriaDespesa(TipoCategoriaDespesa.TRANSPORTE);
                } else {
                    result.setTipoCategoriaDespesa(TipoCategoriaDespesa.OUTROS);
                }

                resultados.add(result);
            }

        } finally {
            cursor.close();
        }

        return resultados;
    }

    public List<Despesas> buscarDespesasGroupMoeda() throws SQLException {

        String sql = "SELECT SUM([VALOR]), [MOEDA] FROM " + NOME_TABELA + " GROUP BY [CATEGORIA]";
        Cursor cursor = null;
        List<Despesas> resultados = new ArrayList<>();

        try {

            cursor = dataBase.rawQuery(sql, null);

            while (cursor.moveToNext()) {

                Despesas result = new Despesas();
                result.setValor(cursor.getDouble(0));

                if(cursor.getString(1).equalsIgnoreCase(TipoMoeda.BRL.toString())) {
                    result.setMoeda(TipoMoeda.BRL);
                } else if(cursor.getString(1).equalsIgnoreCase(TipoMoeda.EUR.toString())) {
                    result.setMoeda(TipoMoeda.EUR);
                } else if(cursor.getString(1).equalsIgnoreCase(TipoMoeda.USD.toString())) {
                    result.setMoeda(TipoMoeda.USD);
                }

                resultados.add(result);
            }

        } finally {
            cursor.close();
        }

        return resultados;
    }

}
