package br.com.viagembolso.bo;

import android.content.Context;

import java.sql.SQLException;

import br.com.viagembolso.dao.MoedaDAO;
import br.com.viagembolso.model.entity.Moedas;

/**
 * Created by marco.
 */
public class MoedaBo {

    private MoedaDAO mDao;
    private Context mContext;

    public MoedaBo(Context context){
        mContext = context;
    }

    public void salvar(Moedas moeda) throws SQLException {
        mDao = new MoedaDAO(mContext);
        mDao.salvar(moeda);
        mDao.fecharConexao();
        mDao = null;
    }

    public Moedas getMoeda(String sigla) throws SQLException{
        mDao = new MoedaDAO(mContext);
        return mDao.getMoeda(sigla);
    }

    public void updateMoeda(Moedas moeda, String sigla) throws SQLException {
        mDao = new MoedaDAO(mContext);
        mDao.updateMoeda(moeda,sigla);
        mDao.fecharConexao();
        mDao = null;
    }

    public boolean verificarMoeda(){
        mDao = new MoedaDAO(mContext);
        return mDao.verificarMoeda();
    }

}
