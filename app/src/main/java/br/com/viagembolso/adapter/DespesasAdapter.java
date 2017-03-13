package br.com.viagembolso.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.viagembolso.R;
import br.com.viagembolso.enumerador.TipoCategoriaDespesa;
import br.com.viagembolso.enumerador.TipoMoeda;
import br.com.viagembolso.interfaces.ClickRecyclerView;
import br.com.viagembolso.model.entity.Despesas;
import br.com.viagembolso.utilis.Utils;

/**
 * Created by Ruan Alves
 */
public class DespesasAdapter extends RecyclerView.Adapter<DespesasAdapter.MyViewHolder> {

    private Context mContext;
    private List<Despesas> mLista;
    private LayoutInflater mLayoutInflater;

    public static ClickRecyclerView clickRecyclerView;

    public DespesasAdapter(Context c, List<Despesas> l, ClickRecyclerView click) {

        mContext = c;
        mLista = l;
        clickRecyclerView = click;
        mLayoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View v = mLayoutInflater.inflate(R.layout.desesas_row, viewGroup, false);
        MyViewHolder mhv = new MyViewHolder(v);

        return mhv;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Despesas despesa = mLista.get(position);

        String simbolo = "";
        if(despesa.getMoeda().equals(TipoMoeda.EURO)){
            simbolo = "€";
        } else if(despesa.getMoeda().equals(TipoMoeda.DOLAR)){
            simbolo = "$";
        } else {
            simbolo = "R$";
        }

        holder.textValor.setText(simbolo + " " + Utils.converterDoubleDoisDecimais(despesa.getValor()));
        holder.textDescricao.setText(despesa.getDescricao());
        holder.textData.setText(despesa.getDataDespesa());

        if(despesa.getTipoCategoriaDespesa().equals(TipoCategoriaDespesa.ALIMENTACAO)){
            holder.letter.setImageDrawable((ContextCompat.getDrawable(mContext, R.drawable.icon_despesa_roxo)));
        } else  if(despesa.getTipoCategoriaDespesa().equals(TipoCategoriaDespesa.HOSPEDAGEM)){
            holder.letter.setImageDrawable((ContextCompat.getDrawable(mContext, R.drawable.icon_despesa_green)));
        } else  if(despesa.getTipoCategoriaDespesa().equals(TipoCategoriaDespesa.TRANSPORTE)){
            holder.letter.setImageDrawable((ContextCompat.getDrawable(mContext, R.drawable.icon_despesa_blue)));
        } else {
            holder.letter.setImageDrawable((ContextCompat.getDrawable(mContext, R.drawable.icon_despesa_laranja)));
        }

    }

    @Override
    public int getItemCount() {
        return mLista.size();
    }

    public Object getItemAtPosition(int position) {
        return mLista.get(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textValor, textData, textDescricao;
        public ImageView letter;

        MyViewHolder(View itemView) {
            super(itemView);
            textValor = (TextView) itemView.findViewById(R.id.tv_despesa_valor);
            textDescricao = (TextView) itemView.findViewById(R.id.tv_despesa_descricao);
            textData = (TextView) itemView.findViewById(R.id.tv_despesa_data);
            letter = (ImageView) itemView.findViewById(R.id.icon_entry);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickRecyclerView.onCustomClick(mLista.get(getLayoutPosition()));
                }
            });
        }

    }

}
