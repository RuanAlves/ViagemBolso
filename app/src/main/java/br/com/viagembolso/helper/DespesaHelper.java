package br.com.viagembolso.helper;

import android.content.Context;
import android.os.Vibrator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Spinner;

import br.com.viagembolso.R;
import br.com.viagembolso.activity.ManterDespesaActivity;
import br.com.viagembolso.enumerador.TipoCategoriaDespesa;
import br.com.viagembolso.enumerador.TipoMoeda;
import br.com.viagembolso.model.entity.Despesas;
import br.com.viagembolso.utilis.Utils;

/**
 * Created by Ruan Alves
 */

public class DespesaHelper {

    private EditText mValor;
    private Spinner mCategoria;
    private Spinner mMoeda;
    private EditText mData;
    private EditText mDescripcion;
    private Context mContext;
    private Despesas mDepesas;

    public DespesaHelper(ManterDespesaActivity activity) {

        activityEditText(activity);
        mContext = activity;
        mDepesas = new Despesas();
    }

    private void activityEditText(ManterDespesaActivity activity) {

        mValor = (EditText) activity.findViewById(R.id.despesa_valor);
        mCategoria = (Spinner) activity.findViewById(R.id.despesa_categoria);
        mData = (EditText) activity.findViewById(R.id.despesa_data);
        mDescripcion = (EditText) activity.findViewById(R.id.despesa_descricao);
        mMoeda = (Spinner) activity.findViewById(R.id.despesa_moeda);

    }


    public Despesas getDepesas() {

        mDepesas.setDescricao(mDescripcion.getText().toString());
        mDepesas.setValor(Double.valueOf(mValor.getText().toString()));
        mDepesas.setDataDespesa(mData.getText().toString());

        String categoriaAux = mCategoria.getSelectedItem().toString();
        if(categoriaAux.equalsIgnoreCase("Alimentação")){
            mDepesas.setTipoCategoriaDespesa(TipoCategoriaDespesa.ALIMENTACAO);
        }else if(categoriaAux.equalsIgnoreCase("Transporte")){
            mDepesas.setTipoCategoriaDespesa(TipoCategoriaDespesa.TRANSPORTE);
        }else if(categoriaAux.equalsIgnoreCase("Hospedagem")){
            mDepesas.setTipoCategoriaDespesa(TipoCategoriaDespesa.HOSPEDAGEM);
        }else{
            mDepesas.setTipoCategoriaDespesa(TipoCategoriaDespesa.OUTROS);
        }

        String moedaAux = mMoeda.getSelectedItem().toString();
        if(moedaAux.equalsIgnoreCase("Real")){
            mDepesas.setMoeda(TipoMoeda.BRL);
        } else if(moedaAux.equalsIgnoreCase("Euro")){
            mDepesas.setMoeda(TipoMoeda.EUR);
        } else if(moedaAux.equalsIgnoreCase("Dolar")){
            mDepesas.setMoeda(TipoMoeda.USD);
        }

        return mDepesas;
    }

    public void setDepesas(Despesas depesas) {

        mDescripcion.setText(depesas.getDescricao());
        mValor.setText(String.valueOf(Utils.converterDoubleDoisDecimais(depesas.getValor())));
        mData.setText(depesas.getDataDespesa());

        if(depesas.getTipoCategoriaDespesa().equals(TipoCategoriaDespesa.ALIMENTACAO)){
            mCategoria.setSelection(0);
        } else if(depesas.getTipoCategoriaDespesa().equals(TipoCategoriaDespesa.TRANSPORTE)){
            mCategoria.setSelection(1);
        } else if(depesas.getTipoCategoriaDespesa().equals(TipoCategoriaDespesa.HOSPEDAGEM)){
            mCategoria.setSelection(2);
        } else if(depesas.getTipoCategoriaDespesa().equals(TipoCategoriaDespesa.OUTROS)){
            mCategoria.setSelection(3);
        }

        if(depesas.getMoeda().equals(TipoMoeda.BRL)){
            mMoeda.setSelection(0);
        } else if(depesas.getMoeda().equals(TipoMoeda.EUR)){
            mMoeda.setSelection(1);
        } else if(depesas.getMoeda().equals(TipoMoeda.USD)){
            mMoeda.setSelection(2);
        }

        this.mDepesas = depesas;

    }

    public Boolean validacao() {

        Animation animShake = AnimationUtils.loadAnimation(mContext, R.anim.shake);
        Vibrator vib = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);

        if (!Utils.validateNotNull(mValor, "Preencha o campo Valor!")){
            mValor.setAnimation(animShake);
            mValor.startAnimation(animShake);
            vib.vibrate(120);
            return false;
        }

        if (!Utils.validateNotNull(mData, "Informe a data da despesa!")){
            mData.setAnimation(animShake);
            mData.startAnimation(animShake);
            vib.vibrate(120);
            return false;
        }

        if (!Utils.validateNotNull(mDescripcion, "Preencha o campo Descrição!")){
            mDescripcion.setAnimation(animShake);
            mDescripcion.startAnimation(animShake);
            vib.vibrate(120);
            return false;
        }

        return true;
    }

    public void setarDate(String date) {
        mData.setText(date);
    }
}
