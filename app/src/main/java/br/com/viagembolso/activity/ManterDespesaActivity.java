package br.com.viagembolso.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.viagembolso.R;
import br.com.viagembolso.bo.DespesasBO;
import br.com.viagembolso.helper.DespesaHelper;
import br.com.viagembolso.model.entity.Despesas;
import br.com.viagembolso.utilis.DateDialog;

/**
 * @author Ruan Alves
 */
public class ManterDespesaActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener {

    private Toolbar mToolbar;
    private DespesaHelper helper;
    private EditText mDataDespesa;
    private Despesas mDespesas;
    private DespesasBO despesasBO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manter_despesa);

        setmToolbar();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(helper == null) helper = new DespesaHelper(this);

        Intent intent = getIntent();
        Despesas despesas = (Despesas) intent.getSerializableExtra("despesas");
        if (despesas != null){
            helper.setDepesas(despesas);
        }

        init();

    }

    private void init(){

        mDataDespesa  = (EditText) findViewById(R.id.despesa_data);
        mDataDespesa.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new DateDialog().show(getSupportFragmentManager(), "DatePicker");
                    }
                }
        );

        if(mDespesas == null)mDespesas = new Despesas();
        if(despesasBO == null)despesasBO = new DespesasBO(this);

    }

    private void setmToolbar() {

        mToolbar = (Toolbar) findViewById(R.id.tb_main);
        mToolbar.setTitle("Despesas");
        setSupportActionBar(mToolbar);

    }

    public void clickButtom(View v) {

        try {

            if(helper.validacao()){

                mDespesas = helper.getDepesas();

                if (mDespesas.getId() != 0){
                    despesasBO.editar(mDespesas);
                } else {
                    despesasBO.salvar(mDespesas);
                }

                showAlert("Operação realizada com sucesso!");
                finish();

            }

        } catch (Exception e) {
            showAlert("Erro ao realizar operação!");
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return true;

    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = dateFormat.parse(year + "-" + monthOfYear + "-" + dayOfMonth);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outDate = dateFormat.format(date);

        helper.setarDate(outDate);
    }
}
