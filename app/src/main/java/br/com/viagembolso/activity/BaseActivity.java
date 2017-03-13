package br.com.viagembolso.activity;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * @author Ruan Alves
 */
public class BaseActivity extends AppCompatActivity {

    MaterialDialog dialog;

    public void showAlert(String message){
        Snackbar.make(findViewById(android.R.id.content),message,Snackbar.LENGTH_LONG).show();
    }

    public void showFixedBottom(String message){
        Snackbar.make(findViewById(android.R.id.content),message,Snackbar.LENGTH_INDEFINITE).show();
    }

    public void showDialogWithMessage(String message){
        dialog = new MaterialDialog.Builder(this)
                .content(message)
                .progress(true,0)
                .show();
    }

    public void dismissDialog(){
        if(dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

}
