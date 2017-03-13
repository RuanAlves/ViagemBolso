package br.com.viagembolso.helper;

import android.content.Context;
import android.os.Vibrator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.viagembolso.R;
import br.com.viagembolso.activity.ManterDespesaActivity;
import br.com.viagembolso.activity.ResumoDespesasActivity;
import br.com.viagembolso.enumerador.TipoCategoriaDespesa;
import br.com.viagembolso.enumerador.TipoMoeda;
import br.com.viagembolso.model.entity.Despesas;
import br.com.viagembolso.utilis.Utils;

/**
 * Created by Ruan Alves
 */

public class ResumoDespesaHelper {

    private TextView mValorAlimentacao;
    private TextView mValorTransporte;
    private TextView mValorHospedagem;
    private TextView mValorOutras;
    private String moeda = "";

    public ResumoDespesaHelper(ResumoDespesasActivity activity, String moeda) {

        activityEditText(activity);
        this.moeda = moeda;

    }

    private void activityEditText(ResumoDespesasActivity activity) {

        mValorAlimentacao = (TextView) activity.findViewById(R.id.resumo_text_alimentacao);
        mValorTransporte  = (TextView) activity.findViewById(R.id.resumo_text_transporte);
        mValorHospedagem  = (TextView) activity.findViewById(R.id.resumo_text_hospedagem);
        mValorOutras      = (TextView) activity.findViewById(R.id.resumo_text_outros);

    }

    public void setmResumoDespesas(List<Despesas> listResumo) {

        double vlAlimentacao = 0.0;
        double vlTransporte = 0.0;
        double vlHospedagem = 0.0;
        double vlOutros = 0.0;

        for(Despesas d : listResumo){

            if(d.getTipoCategoriaDespesa().equals(TipoCategoriaDespesa.ALIMENTACAO)){
                vlAlimentacao = d.getValor();
            } else if(d.getTipoCategoriaDespesa().equals(TipoCategoriaDespesa.TRANSPORTE)){
                vlTransporte = d.getValor();
            } else if(d.getTipoCategoriaDespesa().equals(TipoCategoriaDespesa.HOSPEDAGEM)){
                vlHospedagem = d.getValor();
            } else if(d.getTipoCategoriaDespesa().equals(TipoCategoriaDespesa.OUTROS)){
                vlOutros = d.getValor();
            }

        }

        String simbolo = "";
        if (moeda.equals("Euro"))simbolo = "€";
        else if (moeda.equalsIgnoreCase("Dolar")) simbolo = "$";
        else simbolo = "R$";

        mValorAlimentacao.setText(simbolo + " " + String.valueOf(Utils.converterDoubleDoisDecimais(vlAlimentacao)));
        mValorTransporte.setText(simbolo + " " + String.valueOf(Utils.converterDoubleDoisDecimais(vlTransporte)));
        mValorHospedagem.setText(simbolo + " " + String.valueOf(Utils.converterDoubleDoisDecimais(vlHospedagem)));
        mValorOutras.setText(simbolo + " " + String.valueOf(Utils.converterDoubleDoisDecimais(vlOutros)));

    }

}
