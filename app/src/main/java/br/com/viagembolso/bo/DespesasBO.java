package br.com.viagembolso.bo;

import android.content.Context;

import java.sql.SQLException;
import java.util.List;

import br.com.viagembolso.dao.DespesaDAO;
import br.com.viagembolso.dao.MoedaDAO;
import br.com.viagembolso.enumerador.TipoMoeda;
import br.com.viagembolso.model.entity.Despesas;
import br.com.viagembolso.model.entity.Moedas;

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

    /*public List<Despesas> buscarResumoDespesas() throws SQLException{
        mDao = new DespesaDAO(mContext);
        return mDao.buscarResumoDespesa();
    }*/

    public List<Despesas> buscarDespesaGroupCategoria() throws SQLException{
        mDao = new DespesaDAO(mContext);
        List<Despesas> result = mDao.buscarDespesaGroupCategoria();
        return result;
    }

    public List<Despesas> buscarDespesasGroupMoeda() {
        mDao = new DespesaDAO(mContext);
        return mDao.buscarDespesasGroupMoeda();
    }

    public double calcularValorTotal(List<Despesas> listDespesaMoeda, Moedas moeda) throws SQLException{

        Double total = 0.0;
        MoedaDAO moedaDao = new MoedaDAO(mContext);

        for (Despesas desp : listDespesaMoeda){
            Moedas r = moedaDao.getMoeda(desp.getMoeda().name());
            Double real = desp.getValor() * r.getValor();
            total = total + (real/moeda.getValor());
        }

        return total;

    }

    public List<Despesas> retornarListDespesaCategoria(List<Despesas> listDespesaMoeda , Moedas moeda) throws SQLException{

        Double total_real  = 0.0;
        Double total_dolar = 0.0;
        Double total_euro  = 0.0;
        MoedaDAO moedaDao  = new MoedaDAO(mContext);
        List<Despesas> listDespesaCategoria = mDao.buscarDespesaGroupCategoria();

        for (Despesas desp : listDespesaMoeda){

            Moedas r = moedaDao.getMoeda(desp.getMoeda().name());
            Double real = desp.getValor() * r.getValor();
            Double total = (real/moeda.getValor());

            if(desp.getMoeda().equals(TipoMoeda.BRL)){
                total_real += total;
            } else if(desp.getMoeda().equals(TipoMoeda.EUR)){
                total_euro += total;
            } else if(desp.getMoeda().equals(TipoMoeda.USD)){
                total_dolar += total;
            }

        }

        for (Despesas desp : listDespesaCategoria){

            if(desp.getMoeda().equals(TipoMoeda.BRL)){
                desp.setValor(total_real);
            } else if(desp.getMoeda().equals(TipoMoeda.EUR)){
                desp.setValor(total_euro);
            } else if(desp.getMoeda().equals(TipoMoeda.USD)){
                desp.setValor(total_dolar);
            }

        }

        return listDespesaCategoria;

    }


    public void deletar(Despesas despesa) {
        mDao = new DespesaDAO(mContext);
        mDao.deletar(despesa);
        mDao.fecharConexao();
        mDao = null;
    }
}
