package br.com.viagembolso.helper;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import br.com.viagembolso.R;
import br.com.viagembolso.activity.ResumoDespesasActivity;
import br.com.viagembolso.enumerador.TipoCategoriaDespesa;
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
    private LinearLayout mLinearLayout;
    private TextView mEmpty;
    private TextView mValorTotal;

    public ResumoDespesaHelper(ResumoDespesasActivity activity, String moeda) {

        activityEditText(activity);
        this.moeda = moeda;

    }

    private void activityEditText(ResumoDespesasActivity activity) {

        mValorAlimentacao = (TextView) activity.findViewById(R.id.resumo_text_alimentacao);
        mValorTransporte  = (TextView) activity.findViewById(R.id.resumo_text_transporte);
        mValorHospedagem  = (TextView) activity.findViewById(R.id.resumo_text_hospedagem);
        mValorOutras      = (TextView) activity.findViewById(R.id.resumo_text_outros);
        mLinearLayout     = (LinearLayout) activity.findViewById(R.id.linear_despesa);
        mEmpty            = (TextView) activity.findViewById(R.id.resumo_empty);
        mValorTotal       = (TextView) activity.findViewById(R.id.resumo_text_total);

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

    public void showResumo(){
        mEmpty.setVisibility(View.GONE);
        mLinearLayout.setVisibility(View.VISIBLE);
    }

    public void showNoResumo(){
        mEmpty.setVisibility(View.VISIBLE);
        mLinearLayout.setVisibility(View.GONE);
    }

    public void setarValorTotal(double valor){

        String simbolo = "";
        if (moeda.equals("Euro"))simbolo = "€";
        else if (moeda.equalsIgnoreCase("Dolar")) simbolo = "$";
        else simbolo = "R$";

        mValorTotal.setText(simbolo + " " + String.valueOf(Utils.converterDoubleDoisDecimais(valor)));
    }

}
