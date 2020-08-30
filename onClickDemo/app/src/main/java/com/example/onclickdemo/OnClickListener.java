package com.example.onclickdemo;

import android.view.View;
import android.widget.Toast;

public class OnClickListener implements View.OnClickListener {
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn3:
                Toast.makeText(v.getContext(), R.string.btn3_text, Toast.LENGTH_SHORT).show();
        }
    }
}
