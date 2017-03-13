package br.com.viagembolso.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

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

        if(m.getSigla() == null || m.getSigla().isEmpty()) return null;
        else return  m;

    }

    public void inserir (Moedas m) throws SQLException {


        ContentValues contentValues = new ContentValues();
        contentValues.put("SIGLA", m.getSigla());
        contentValues.put("VALOR", m.getValor());
        contentValues.put("DESCRICAO", m.getDescricao());

         dataBase.insert(NOME_TABELA, null, contentValues);


    }

    public void updateMoeda(Moedas moeda, String sigla) throws SQLException {

        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUNA_SIGLA, moeda.getSigla());
        contentValues.put(COLUNA_VALOR, moeda.getValor());
        contentValues.put(COLUNA_DESCRICAO, moeda.getDescricao());

        String[] args = {sigla};
        dataBase.update(NOME_TABELA, contentValues, "SIGLA = ?", args);
    }

    @Override
    public Moedas contentValuesParaEntidade(ContentValues contentValues) {
        return null;
    }

}
