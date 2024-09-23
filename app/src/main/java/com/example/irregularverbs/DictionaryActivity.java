package com.example.irregularverbs;

import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DictionaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dictionary);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        LinearLayout dictLayout = findViewById(R.id.dict);

        for(int i = 0; i < TestActivity1.verbs.length; i++){

            ConstraintLayout row = new ConstraintLayout(getApplicationContext());
            row.setId(View.generateViewId());
            row.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            ConstraintSet set = new ConstraintSet();

            int padding = (int) (4 * getResources().getDisplayMetrics().density);

            TextView n = new TextView(getApplicationContext());
            n.setId(View.generateViewId());
            n.setText((i+1)+"");
            n.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            n.setPadding(padding, padding, padding, padding);
            row.addView(n, 0);

            TextView inf = new TextView(getApplicationContext());
            inf.setId(View.generateViewId());
            inf.setText(TestActivity1.verbs[i].getFirst_form());
            inf.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            inf.setPadding(padding, padding, padding, padding);
            row.addView(inf, 1);
            inf.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT));

            TextView ndForm = new TextView(getApplicationContext());
            ndForm.setId(View.generateViewId());
            ndForm.setText(TestActivity1.verbs[i].getSecond_form());
            ndForm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            ndForm.setPadding(padding, padding, padding, padding);
            row.addView(ndForm, 2);
            ndForm.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT));


            TextView rdForm = new TextView(getApplicationContext());
            rdForm.setId(View.generateViewId());
            rdForm.setText(TestActivity1.verbs[i].getThird_form());
            rdForm.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            rdForm.setPadding(padding, padding, padding, padding);
            row.addView(rdForm, 3);
            rdForm.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, ConstraintLayout.LayoutParams.WRAP_CONTENT));

            set.clone(row);

            set.connect(n.getId(), ConstraintSet.START, row.getId(), ConstraintSet.START);
            set.connect(n.getId(), ConstraintSet.END, inf.getId(), ConstraintSet.START);
            set.connect(n.getId(), ConstraintSet.TOP, row.getId(), ConstraintSet.TOP);
            set.connect(n.getId(), ConstraintSet.BOTTOM, row.getId(), ConstraintSet.BOTTOM);

            set.connect(inf.getId(), ConstraintSet.START, n.getId(), ConstraintSet.END);
            set.connect(inf.getId(), ConstraintSet.END, ndForm.getId(), ConstraintSet.START);
            set.connect(inf.getId(), ConstraintSet.TOP, row.getId(), ConstraintSet.TOP);
            set.connect(inf.getId(), ConstraintSet.BOTTOM, row.getId(), ConstraintSet.BOTTOM);

            set.connect(ndForm.getId(), ConstraintSet.START, inf.getId(), ConstraintSet.END);
            set.connect(ndForm.getId(), ConstraintSet.END, rdForm.getId(), ConstraintSet.START);
            set.connect(ndForm.getId(), ConstraintSet.TOP, row.getId(), ConstraintSet.TOP);
            set.connect(ndForm.getId(), ConstraintSet.BOTTOM, row.getId(), ConstraintSet.BOTTOM);

            set.connect(rdForm.getId(), ConstraintSet.START, ndForm.getId(), ConstraintSet.END);
            set.connect(rdForm.getId(), ConstraintSet.END, row.getId(), ConstraintSet.END);
            set.connect(rdForm.getId(), ConstraintSet.TOP, row.getId(), ConstraintSet.TOP);
            set.connect(rdForm.getId(), ConstraintSet.BOTTOM, row.getId(), ConstraintSet.BOTTOM);

            set.applyTo(row);

            int widthInPx = (int)(getResources().getDisplayMetrics().density*40);
            n.setLayoutParams(new ConstraintLayout.LayoutParams(widthInPx, ConstraintLayout.LayoutParams.WRAP_CONTENT));

            dictLayout.addView(row);
        }
    }
}