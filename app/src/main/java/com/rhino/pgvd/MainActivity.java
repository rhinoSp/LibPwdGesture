package com.rhino.pgvd;

import android.app.Activity;
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

    private static final int REQUEST_CODE_CREATE_PWD = 1;
    private static final int REQUEST_CODE_INPUT_PWD = 2;

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mBinding.btSetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PwdGestureUtils.create()
                        .setRequestCode(REQUEST_CODE_CREATE_PWD)
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
                        .setRequestCode(REQUEST_CODE_INPUT_PWD)
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
        switch (requestCode) {
            case REQUEST_CODE_CREATE_PWD:
                if (resultCode == Activity.RESULT_OK) {
                    showToast("密码设置成功");
                }
                break;
            case REQUEST_CODE_INPUT_PWD:
                if (resultCode == Activity.RESULT_OK) {
                    showToast("密码正确");
                }
                break;
            default:
                break;
        }
    }

    private void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
