package br.com.viagembolso.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.viagembolso.R;
import br.com.viagembolso.adapter.DespesasAdapter;
import br.com.viagembolso.bo.DespesasBO;
import br.com.viagembolso.interfaces.ClickRecyclerView;
import br.com.viagembolso.model.entity.Despesas;

/**
 * Created by Ruan Alves
 */

public class PrincipalActivity extends BaseActivity implements ClickRecyclerView {

    private Toolbar mToolbar;
    private FloatingActionMenu mFab;
    private DespesasAdapter mAdapter;
    private List<Despesas> mListDespesas;
    private RecyclerView mRecyclerView;
    private DespesasBO despesasBO;
    private ItemTouchHelper itemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        if(despesasBO == null) despesasBO = new DespesasBO(this);

        setmToolbar();
        setFloafButtomMenu();
        setmRecyclerView();
        itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }


    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();

            if (direction == ItemTouchHelper.LEFT) {

                AlertDialog.Builder builder = new AlertDialog.Builder(PrincipalActivity.this);
                builder.setMessage("Tem Certeza que deseja Excluir?");
                builder.setPositiveButton("EXCLUIR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAdapter.notifyItemRemoved(position);
                        Despesas despesa = (Despesas) mAdapter.getItemAtPosition(position);

                        despesasBO.deletar(despesa);
                        mListDespesas.remove(position);

                        return;
                    }
                }).setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAdapter.notifyItemRemoved(position + 1);
                        mAdapter.notifyItemRangeChanged(position, mAdapter.getItemCount());
                        return;
                    }
                }).show();
            }
        }
    };



    @Override
    protected void onPostResume() {
        super.onPostResume();

        setListDados();

    }

    private void setmRecyclerView() {

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_list_despesas);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llm);

    }

    public void setListDados(){

        if (mListDespesas == null) mListDespesas = new ArrayList<>();
        else mListDespesas.clear();

        mListDespesas.addAll(getListDespesas());

        if(mAdapter == null)mAdapter = new DespesasAdapter(this, mListDespesas, this);

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        if(mListDespesas.size() > 0){
            showDespesas();
        } else {
            showNoDespesas();
        }

    }

    private List<Despesas> getListDespesas(){

        try{
            return despesasBO.buscarDespesas();
        }catch (SQLException e){
            showAlert("OPS! Ocorreu um erro ao buscar despesas...");
            e.printStackTrace();
        }

        return null;

    }

    private void showDespesas(){
        TextView textEmpty = (TextView) findViewById(R.id.recyclerview_data_empty);
        textEmpty.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showNoDespesas(){
        TextView textEmpty = (TextView) findViewById(R.id.recyclerview_data_empty);
        textEmpty.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFab.showMenuButton(true);
    }

    private void setmToolbar() {

        mToolbar = (Toolbar) findViewById(R.id.tb_main);
        mToolbar.setTitle("Viagem no Bolso");
        setSupportActionBar(mToolbar);

    }

    private void setFloafButtomMenu(){

        mFab = (FloatingActionMenu) findViewById(R.id.fab);
        mFab.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean b) {
            }
        });
        mFab.showMenuButton(true);
        mFab.setClosedOnTouchOutside(true);

        FloatingActionButton inserir = (FloatingActionButton) findViewById(R.id.fab01);
        FloatingActionButton resumo = (FloatingActionButton) findViewById(R.id.fab02);

        inserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFab.hideMenuButton(true);
                startActivity(new Intent(PrincipalActivity.this, ManterDespesaActivity.class));
            }
        });

        resumo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListDespesas != null && mListDespesas.size() > 0) {
                    mFab.hideMenuButton(true);
                    startActivity(new Intent(PrincipalActivity.this, ResumoDespesasActivity.class));
                } else {
                    showAlert("Não há despesas cadastradas para gerar um resumo!");
                }
            }
        });

    }



    @Override
    public void onCustomClick(Object object) {
        Despesas despesas = (Despesas) object;
        Intent intentParaDespesa = new Intent(this, ManterDespesaActivity.class);
        intentParaDespesa.putExtra("despesas", despesas);
        startActivity(intentParaDespesa);

    }
}
