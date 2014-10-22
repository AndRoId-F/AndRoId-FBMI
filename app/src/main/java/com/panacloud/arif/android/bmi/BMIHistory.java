package com.panacloud.arif.android.bmi;

import android.app.ListActivity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import com.panacloud.arif.android.R;
import com.panacloud.arif.android.sqlite.BMIDataSource;
import com.panacloud.arif.android.sqlite.BMIResult;

import java.util.List;

/**
 * Created by Arif on 10/3/2014.
 */
public class BMIHistory extends ListActivity {
    private BMIDataSource datasource;
    String fontPath = "fonts/danielbd.ttf";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getActionBar().setSubtitle(getString(R.string.version));
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        //((ListView) findViewById(android.R.id.list))  if u want to get predefine list view

        datasource = new BMIDataSource(this);
        datasource.open();

        List<BMIResult> values = datasource.getAllBMIResults();

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        ArrayAdapter<BMIResult> adapter = new ArrayAdapter<BMIResult>(this,
                android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }

    public void onClick(View view) {
        @SuppressWarnings("unchecked")
        ArrayAdapter<BMIResult> adapter = (ArrayAdapter<BMIResult>) getListAdapter();
        BMIResult bmiResult = null;
        switch (view.getId()) {

            case R.id.delete:
                if (getListAdapter().getCount() > 0) {
                    bmiResult = (BMIResult) getListAdapter().getItem(0);
                    datasource.deleteBMIResult(bmiResult);
                    adapter.remove(bmiResult);
                }
                break;
        }
        adapter.notifyDataSetChanged();

    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }

}
