package br.com.viagembolso.bo;

import android.content.Context;

import java.sql.SQLException;
import java.util.List;

import br.com.viagembolso.dao.DespesaDAO;
import br.com.viagembolso.model.entity.Despesas;

/**
 * Created by Ruan Alves
 */
public class DespesasBO {

    private DespesaDAO mDao;
    private Context mContext;

    public DespesasBO(Context context){
        mContext = context;
    }

    public void salvar(Despesas despesa) throws Exception {
        mDao = new DespesaDAO(mContext);
        mDao.salvar(despesa);
        mDao.fecharConexao();
        mDao = null;
    }

    public void editar(Despesas despesa)throws Exception {
        mDao = new DespesaDAO(mContext);
        mDao.editar(despesa);
        mDao.fecharConexao();
        mDao = null;
    }

    public List<Despesas> buscarDespesas() throws SQLException {
        mDao = new DespesaDAO(mContext);
        return mDao.buscarDespesas();
    }

    public List<Despesas> buscarResumoDespesas() throws SQLException{
        mDao = new DespesaDAO(mContext);
        return mDao.buscarResumoDespesa();
    }


    public void deletar(Despesas despesa) {
        mDao = new DespesaDAO(mContext);
        mDao.deletar(despesa);
        mDao.fecharConexao();
        mDao = null;
    }
}
