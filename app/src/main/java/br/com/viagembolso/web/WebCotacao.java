package br.com.viagembolso.web;

import android.content.Context;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import br.com.viagembolso.bo.MoedaBo;
import br.com.viagembolso.model.entity.Moedas;
import okhttp3.Response;

/**
 * Created by Ruan Alves
 */
public class WebCotacao extends WebConnection {

    private Context mContext;

    public WebCotacao(Context context) {
        super("");
        this.mContext = context;
    }

    @Override
    String getRequestContent() {
        return  "";
    }

    @Override
    void handleResponse(Response response) {

        String responseBody = null;
        MoedaBo moedaBo = new MoedaBo(mContext);

        try {

            responseBody = response.body().string();
            JSONObject object = new JSONObject(responseBody);
            JSONObject valores = object.getJSONObject("valores");
            JSONArray names = valores.names();
            String status = "FAILED";

            for(int i =0; i < names.length(); i++){

                JSONObject moeda = valores.getJSONObject(names.getString(i));
                Moedas m = moedaBo.getMoeda(names.getString(i));

                if(m == null) {
                    m = new Moedas();
                    m.setSigla(names.getString(i));
                    m.setValor(moeda.getDouble("valor"));
                    m.setDescricao(moeda.getString("nome"));
                    moedaBo.salvar(m);
                }else{
                    m.setSigla(names.getString(i));
                    m.setValor(moeda.getDouble("valor"));
                    m.setDescricao(moeda.getString("nome"));
                    moedaBo.updateMoeda(m, m.getSigla());
                }

                status = "SUCESS";

            }

            EventBus.getDefault().post(status);

        } catch (IOException e) {
            EventBus.getDefault().post(e);
        } catch (JSONException e) {
            EventBus.getDefault().post(new Exception("A resposta do servidor não é válida"));
        } catch (SQLException e){
            EventBus.getDefault().post(e);
        }

    }
}
