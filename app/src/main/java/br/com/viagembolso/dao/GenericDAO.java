package br.com.viagembolso.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.com.viagembolso.interfaces.EntidadePersistivel;

/**
 * Created by Ruan Alves
 */
public abstract class GenericDAO<T extends EntidadePersistivel> {

    protected SQLiteDatabase dataBase = null;

    public GenericDAO(Context context) {
        DataBaseHelper persistenceHelper = DataBaseHelper.getInstance(context);
        dataBase = persistenceHelper.getWritableDatabase();
    }

    public abstract String getNomeColunaPrimaryKey();

    //Pegara o nome da tabela de cada entidade e usaremos nos metos que necessitam dela
    public abstract String getNomeTabela();

    /**
     * método é utilizado na hora de salvar e editar um registro
     * ele amontara os valores, para a inserção e edição
     */
    public abstract ContentValues entidadeParacontentValues(T entidade);

    /**
     * Este método é utilizado na hora de recuperar dados, e é o inverso do método entidadeParacontentValues
     * Neste caso, receberemos como parâmetro um ContentValues e devemos transforma-lo em uma entidade.
     */
    public abstract T contentValuesParaEntidade(ContentValues contentValues);

    /**
     * @author Ruan Alves
     * @param entidade
     * metodo que salva as entidades
     */
    public void salvar(T entidade) {
        ContentValues values = entidadeParacontentValues(entidade);
        dataBase.insert(getNomeTabela(), null, values);
    }

    /**
     * @author Ruan Alves
     * @param entidade
     * metodo que salva as entidades
     */
    public long salvarRetornID(T entidade) {

        long id = 0;

        ContentValues values = entidadeParacontentValues(entidade);
        id = dataBase.insert(getNomeTabela(), null, values);

        return id;
    }

    public void deletar(T t) {

        String[] valoresParaSubstituir = {
                String.valueOf(t.getId())
        };

        dataBase.delete(getNomeTabela(), getNomeColunaPrimaryKey() + " =  ?", valoresParaSubstituir);
    }

    public void editar(T t) {
        ContentValues valores = entidadeParacontentValues(t);

        String[] valoresParaSubstituir = {
                String.valueOf(t.getId())
        };

        dataBase.update(getNomeTabela(), valores, getNomeColunaPrimaryKey() + " = ?", valoresParaSubstituir);
    }

    public void editar(T t, String codigo) {
        ContentValues valores = entidadeParacontentValues(t);

        String[] valoresParaSubstituir = {codigo};

        dataBase.update(getNomeTabela(), valores, getNomeColunaPrimaryKey() + " = ?", valoresParaSubstituir);
    }

    /**
     * @author Ruan Alves
     * metodo que deleta todos os dados na tabela
     */
    public void deletarTodos() {
        dataBase.execSQL("DELETE FROM " + getNomeTabela());
    }

    /**
     * metodo que lista todos os dados da tabela
     * @author Ruan Alves
     * @return
     */
    public List<T> recuperarTodos() {
        String queryReturnAll = "SELECT * FROM " + getNomeTabela();
        List<T> result = recuperarPorQuery(queryReturnAll);

        return result;
    }

    /**
     * metodo que recupera algum dado pelo ID
     * @param id
     * @return
     */
    public T recuperarPorID(int id) {
        String queryOne = "SELECT * FROM " + getNomeTabela() + " WHERE " + getNomeColunaPrimaryKey() + " = " + id;
        List<T> result = recuperarPorQuery(queryOne);
        if(result.isEmpty()) {
            return null;
        } else {
            return result.get(0);
        }
    }

    /**
     * metodo que recupera por uma query especifica
     * @author Ruan Alves
     * @param query
     * @return
     */
    public List<T> recuperarPorQuery(String query) {
        Cursor cursor = dataBase.rawQuery(query, null);

        List<T> result = new ArrayList<T>();
        if (cursor.moveToFirst()) {
            do {
                ContentValues contentValues = new ContentValues();
                DatabaseUtils.cursorRowToContentValues(cursor, contentValues);
                T t = contentValuesParaEntidade(contentValues);
                result.add(t);
            } while (cursor.moveToNext());
        }
        return result;
    }

    /**
     * metodo que fecha a conexão, e libera a memoria
     * @author Ruan Alves
     */
    public void fecharConexao() {
        if(dataBase != null && dataBase.isOpen())
            dataBase.close();
    }


}
