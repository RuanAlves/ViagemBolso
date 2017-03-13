package br.com.viagembolso.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import br.com.viagembolso.interfaces.IBancoDeDados;

/**
 * Created by Ruan Alves
 */
public class DataBaseHelper extends SQLiteOpenHelper {

    private static final int VERSAO_BANCO_DADOS = 2;

    private static DataBaseHelper instance;

    public static DataBaseHelper getInstance(Context context) {
        if(instance == null)instance = new DataBaseHelper(context);

        return instance;
    }

    public DataBaseHelper(Context context) {
        super(context, IBancoDeDados.NOME_BANCO_DADOS_FV, null, VERSAO_BANCO_DADOS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(MoedaDAO.SCRIPT_CRIACAO_TABELA);
        db.execSQL(DespesaDAO.SCRIPT_CRIACAO_TABELA);
        Log.i("DATABASE", "CRIANDO TABELA DO BANDO DE DADOS");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("DATABASE", "ATUALIZANDO TABELA");

        db.execSQL(MoedaDAO.SCRIPT_DELECAO_TABELA);

        onCreate(db);
    }
}
