package com.faberospina.cine;

import android.support.v7.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.VisibleForTesting;
import android.os.Bundle;

/**
 * Created by Faber on 23/11/2016.
 */

public class BaseActivity extends AppCompatActivity {

    @VisibleForTesting
    public ProgressDialog mProgressDialog;

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Loading....");//getString(R.string.loading)
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }
}
