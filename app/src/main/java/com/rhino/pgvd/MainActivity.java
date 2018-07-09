package com.rhino.pgvd;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.rhino.pgv.view.PwdGestureView;
import com.rhino.pgvd.R;
import com.rhino.pgvd.databinding.ActivityMainBinding;
import com.rhino.pgv.utils.PwdGestureUtils;


/**
 * @author LuoLin
 * @since Create on 2018/7/6.
 */
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.btSetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PwdGestureUtils.create()
                        .setRequestCode(11)
                        .setRowCount(3)
                        .setColumnCount(3)
                        .setCircleColor(0xFFAAAAAA)
                        .setCircleLineWidth(1)
                        .setCircleRadius(38)
                        .setCircleSelectColor(0xFF1bbc9b)
                        .setCircleSelectRadius(10)
                        .setLineWidth(1)
                        .setLineColor(0xFF1bbc9b)
                        .setShowLine(true)
                        .setAutoMatch(false)
                        .show(MainActivity.this);
            }
        });
        mBinding.btInputPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PwdGestureUtils.input()
                        .setRequestCode(11)
                        .setRowCount(3)
                        .setColumnCount(3)
                        .setCircleColor(0xFFAAAAAA)
                        .setCircleLineWidth(1)
                        .setCircleRadius(38)
                        .setCircleSelectColor(0xFF1bbc9b)
                        .setCircleSelectRadius(10)
                        .setLineWidth(1)
                        .setLineColor(0xFF1bbc9b)
                        .setShowLine(true)
                        .setAutoMatch(true)
                        .setRightPassword(new int[]{7, 5, 2, 6, 9})
                        .show(MainActivity.this);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("RHINO", "requestCode = " + requestCode);
    }

    private void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
