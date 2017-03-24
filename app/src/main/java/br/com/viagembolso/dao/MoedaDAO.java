package br.com.viagembolso.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.viagembolso.enumerador.TipoCategoriaDespesa;
import br.com.viagembolso.model.entity.Despesas;
import br.com.viagembolso.model.entity.Moedas;

/**
 * Created by Ruan Alves
 */
public class MoedaDAO extends GenericDAO<Moedas> {

    private static final String TAG = "MOEDA_DAO";
    private static final String NOME_TABELA = "MOEDAS";
    public static final String SCRIPT_CRIACAO_TABELA = "CREATE TABLE  IF NOT EXISTS " + NOME_TABELA + " ([SIGLA] TEXT PRIMARY KEY, [VALOR] REAL , [DESCRICAO] TEXT)";
    public static final String SCRIPT_DELECAO_TABELA = "DROP TABLE IF EXISTS " + NOME_TABELA;

    private static final String COLUNA_SIGLA = "SIGLA";
    private static final String COLUNA_VALOR = "VALOR";
    private static final String COLUNA_DESCRICAO = "DESCRICAO";

    public MoedaDAO(Context context) {
        super(context);
    }

    @Override
    public String getNomeColunaPrimaryKey() {
        return COLUNA_SIGLA;
    }

    @Override
    public String getNomeTabela() {
        return NOME_TABELA;
    }

    @Override
    public ContentValues entidadeParacontentValues(Moedas moeda) {
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUNA_SIGLA, moeda.getSigla());
        contentValues.put(COLUNA_VALOR, moeda.getValor());
        contentValues.put(COLUNA_DESCRICAO, moeda.getDescricao());

        return contentValues;
    }

    public Moedas getMoeda(String sigla) throws SQLException {

        Moedas m = null;
        String sql = "SELECT [SIGLA],[VALOR],[DESCRICAO] FROM " + NOME_TABELA + " WHERE [SIGLA] = '"+sigla+"'";
        Cursor cursor = null;

        try {

            cursor = dataBase.rawQuery(sql, null);

            if (cursor != null) {
                if (cursor.moveToNext()) {
                    m = new Moedas();
                    m.setSigla(cursor.getString(0));
                    m.setValor(cursor.getDouble(1));
                    m.setDescricao(cursor.getString(2));
                }
            }
        } finally {
            cursor.close();
        }

        if(m == null || m.getSigla() == null || m.getSigla().isEmpty()) return null;
        else return  m;

    }

    public boolean verificarMoeda() throws android.database.SQLException {

        String sql = "SELECT * FROM " + NOME_TABELA + " WHERE SIGLA <> 'BRL'";
        Cursor cursor = null;

        try {

            cursor = dataBase.rawQuery(sql, null);

            if(cursor.getCount() > 0) {
                return true;
            }

        } finally {
            cursor.close();
        }

        return false;
    }

    public void updateMoeda(Moedas moeda, String sigla) throws SQLException {

        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUNA_SIGLA, moeda.getSigla());
        contentValues.put(COLUNA_VALOR, moeda.getValor());
        contentValues.put(COLUNA_DESCRICAO, moeda.getDescricao());

        String[] args = {sigla};
        dataBase.update(NOME_TABELA, contentValues, "SIGLA = ?", args);
    }

    public void insertReal() {

        Moedas moeda = new Moedas();
        moeda.setSigla("BRL");
        moeda.setDescricao("Real");
        moeda.setValor(1);

        salvar(moeda);

    }

    @Override
    public Moedas contentValuesParaEntidade(ContentValues contentValues) {
        return null;
    }

}
