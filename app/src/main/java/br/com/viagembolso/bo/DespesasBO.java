package br.com.viagembolso.bo;

import android.content.Context;

import java.sql.SQLException;
import java.util.List;

import br.com.viagembolso.dao.DespesaDAO;
import br.com.viagembolso.dao.MoedaDAO;
import br.com.viagembolso.enumerador.TipoCategoriaDespesa;
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

    public List<Despesas> retornarListDespesaCategoria(Moedas moeda) throws SQLException{

        Double total_alimentacao  = 0.0;
        Double total_hospedagem = 0.0;
        Double total_transporte  = 0.0;
        Double total_outros  = 0.0;
        MoedaDAO moedaDao  = new MoedaDAO(mContext);
        List<Despesas> listDespesaCategoria = mDao.buscarDespesaGroupCategoriaMoeda();

        for (Despesas desp : listDespesaCategoria){

            Moedas r = moedaDao.getMoeda(desp.getMoeda().name());
            Double real = desp.getValor() * r.getValor();
            Double total = (real/moeda.getValor());

            if(desp.getTipoCategoriaDespesa().equals(TipoCategoriaDespesa.ALIMENTACAO)){
                total_alimentacao += total;
            } else if(desp.getTipoCategoriaDespesa().equals(TipoCategoriaDespesa.HOSPEDAGEM)){
                total_hospedagem += total;
            } else if(desp.getTipoCategoriaDespesa().equals(TipoCategoriaDespesa.TRANSPORTE)){
                total_transporte += total;
            } else if(desp.getTipoCategoriaDespesa().equals(TipoCategoriaDespesa.OUTROS)){
                total_outros += total;
            }

        }

        List<Despesas> listResult = mDao.buscarDespesaGroupCategoria();
        for (Despesas desp : listResult){
            if(desp.getTipoCategoriaDespesa().equals(TipoCategoriaDespesa.ALIMENTACAO)){
                desp.setValor(total_alimentacao);
            } else if(desp.getTipoCategoriaDespesa().equals(TipoCategoriaDespesa.HOSPEDAGEM)){
                desp.setValor(total_hospedagem);
            } else if(desp.getTipoCategoriaDespesa().equals(TipoCategoriaDespesa.TRANSPORTE)){
                desp.setValor(total_transporte);
            } else if(desp.getTipoCategoriaDespesa().equals(TipoCategoriaDespesa.OUTROS)){
                desp.setValor(total_outros);
            }
        }

        return listResult;

    }


    public void deletar(Despesas despesa) {
        mDao = new DespesaDAO(mContext);
        mDao.deletar(despesa);
        mDao.fecharConexao();
        mDao = null;
    }
}
