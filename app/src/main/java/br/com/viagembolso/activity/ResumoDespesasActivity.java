package br.com.viagembolso.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.viagembolso.R;
import br.com.viagembolso.bo.DespesasBO;
import br.com.viagembolso.helper.ResumoDespesaHelper;
import br.com.viagembolso.model.entity.Despesas;
import br.com.viagembolso.model.entity.Moedas;
import br.com.viagembolso.web.WebCotacao;

public class ResumoDespesasActivity extends BaseActivity {

    private Toolbar mToolbar;
    private Spinner mMoedaFilter;
    private DespesasBO mDespesaBo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumo_despesas);

        setmToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        init();

    }

    private void init(){

        if(mDespesaBo == null)mDespesaBo = new DespesasBO(this);

        mMoedaFilter = (Spinner) findViewById(R.id.toolbar_spinner);
        mMoedaFilter.setSelection(0);
        mMoedaFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String status = parent.getItemAtPosition(position).toString();
                showResumo(status);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void setmToolbar() {

        mToolbar = (Toolbar) findViewById(R.id.tb_main);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);

    }

    public void showResumo(String moeda){

        try {

            List<Despesas> despesas = new ArrayList<>();
            despesas = mDespesaBo.buscarResumoDespesas();

            ResumoDespesaHelper helper = new ResumoDespesaHelper(this,moeda);
            helper.setmResumoDespesas(despesas);

        }catch (SQLException e){
            showAlert("Ocorreu um erro ao apresentar informações...");
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_resumo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        } else if (id == R.id.action_update_cotacao) {
            showBasic();
        }

        return true;

    }

    public void showBasic() {
        new MaterialDialog.Builder(this)
                .title("Atulização de Cotação")
                .content("Deseja Atualizar a Cotação das Moedas?")
                .positiveText("Sim")
                .negativeText("Cancelar")
                .canceledOnTouchOutside(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        atualizarCotacaoMoeda();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        showAlert("Usuário cancelou a operação!");
                    }
                })
                .show();
    }

    private void atualizarCotacaoMoeda(){

        showDialogWithMessage(getString(R.string.aguarde_progress));
        WebCotacao webConnection = new WebCotacao(this);
        webConnection.call();

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Exception exception) {
        dismissDialog();
        showAlert(exception.getMessage());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(Moedas m) {
        dismissDialog();
        showAlert("Cotação de moedas atualizada com sucesso!");
    }

}
