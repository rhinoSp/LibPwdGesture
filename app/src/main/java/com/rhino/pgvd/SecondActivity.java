package com.rhino.pgvd;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.rhino.pgvd.databinding.ActivitySecondBinding;

/**
 * @author LuoLin
 * @since Create on 2018/7/11.
 */
public class SecondActivity extends AppCompatActivity {

    ActivitySecondBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_second);
    }

}
