package com.panacloud.arif.android.bmi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.panacloud.arif.android.R;

import com.panacloud.arif.android.seekbar.CustomSeekBar;
import com.panacloud.arif.android.seekbar.ProgressItem;
import com.panacloud.arif.android.sqlite.BMIDataSource;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class BMIActivity extends Activity {

    private static final String TAG = "BMI:MainActivity";
    private SeekBar mHeightSeekBar, mWeightSeekBar;
    private TextView mHeightTextView, mWeightTextView, mBMIStatue, mBMIValue,
            mWeightUnitTextView, mHeightUnitTextView, mHeightDescription, mWeightDescription, mBMIDescription;
    private TextView[] heightScaleTextViewArray = new TextView[5];
    private TextView[] weightScaleTextViewArray = new TextView[5];
    private BMICalculator   mBMICalculator;
    private BMIDataSource datasource;
    private FrameLayout mScaleValueDisplayFrameLayout;
    String fontPath = "fonts/danielbd.ttf";
    private LinearLayout mProgressBarLinearLayout;
    private Button[] mProgressBarButtonArray = new Button[40];
    private String[] colors = new String[]{"#FFD801", "#348017", "#FBB117", "#FF0000"};

    private float totalSpan = 40;
    private float redSpan = 12;
    private float blueSpan = 4;
    private float greenSpan = 6;
    private float yellowSpan = 19;
    private float darkGreySpan;

    public void setSeekbarProgress(double bmiValue) {
         seekbar.setProgress((int)bmiValue);
    }

    private CustomSeekBar seekbar;
    private ArrayList<ProgressItem> progressItemList;
    private ProgressItem mProgressItem;



    private Button mProgressBarButton;

    public Button[] getProgressBarButtonArray() {
        return mProgressBarButtonArray;
    }



    private void changeFonts()
    {
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);


        mHeightTextView.setTypeface(tf);
        mWeightTextView.setTypeface(tf);
        mBMIStatue.setTypeface(tf);
        mBMIValue.setTypeface(tf);
        heightScaleTextViewArray[0].setTypeface(tf);
        heightScaleTextViewArray[1].setTypeface(tf);
        heightScaleTextViewArray[2].setTypeface(tf);
        heightScaleTextViewArray[3].setTypeface(tf);
        heightScaleTextViewArray[4].setTypeface(tf);
        weightScaleTextViewArray[0].setTypeface(tf);
        weightScaleTextViewArray[1].setTypeface(tf);
        weightScaleTextViewArray[2].setTypeface(tf);
        weightScaleTextViewArray[3].setTypeface(tf);
        weightScaleTextViewArray[4].setTypeface(tf);
        mWeightUnitTextView.setTypeface(tf);
        mHeightUnitTextView.setTypeface(tf);
        mHeightDescription.setTypeface(tf);
        mWeightDescription.setTypeface(tf);
        mBMIDescription.setTypeface(tf);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);
        getActionBar().setSubtitle(getString(R.string.version));

        mHeightSeekBar      = (SeekBar)     findViewById(R.id.heightSeekBar);
        mWeightSeekBar      = (SeekBar)     findViewById(R.id.weightSeekBar);
        mHeightTextView     = (TextView)    findViewById(R.id.heightTextView);
        mWeightTextView     = (TextView)    findViewById(R.id.weightTextView);
        mBMIStatue          = (TextView)    findViewById(R.id.bmiStatusTextView);
        mBMIValue           = (TextView)    findViewById(R.id.bmiValueTextView);
        heightScaleTextViewArray[0] = (TextView) findViewById(R.id.heightScale1TextView);
        heightScaleTextViewArray[1] = (TextView) findViewById(R.id.heightScale2TextView);
        heightScaleTextViewArray[2] = (TextView) findViewById(R.id.heightScale3TextView);
        heightScaleTextViewArray[3] = (TextView) findViewById(R.id.heightScale4TextView);
        heightScaleTextViewArray[4] = (TextView) findViewById(R.id.heightScale5TextView);

        weightScaleTextViewArray[0] = (TextView) findViewById(R.id.weightScale1TextView);
        weightScaleTextViewArray[1] = (TextView) findViewById(R.id.weightScale2TextView);
        weightScaleTextViewArray[2] = (TextView) findViewById(R.id.weightScale3TextView);
        weightScaleTextViewArray[3] = (TextView) findViewById(R.id.weightScale4TextView);
        weightScaleTextViewArray[4] = (TextView) findViewById(R.id.weightScale5TextView);
        mScaleValueDisplayFrameLayout = (FrameLayout) findViewById(R.id.scaleValueDisplayFrameLayout);

        mWeightUnitTextView = (TextView) findViewById(R.id.weightUnitTextView);
        mHeightUnitTextView = (TextView) findViewById(R.id.heightUnitTextView);

        mHeightDescription = (TextView) findViewById(R.id.heightDescription);
        mWeightDescription = (TextView) findViewById(R.id.weightDescription);
        mBMIDescription = (TextView) findViewById(R.id.bmiDescription);
        mProgressBarLinearLayout = (LinearLayout) findViewById(R.id.progressBarLinearLayout);

        seekbar = ((CustomSeekBar) findViewById(R.id.seekBar0));
        initDataToSeekbar();

       /*
        mProgressBarButton = (Button) findViewById(R.id.b1);

        mProgressBarButtonArray[0] = mProgressBarButton;

        for(int i=0; i<mProgressBarButtonArray.length; i++) {
            Button button = new Button(getApplicationContext() );

            if(i<=18)
                button.setBackgroundColor(Color.parseColor(colors[0]));
            else if(i>=19 && i<=24)
                button.setBackgroundColor(Color.parseColor(colors[1]));
            else if(i>=25 && i<=28)
                button.setBackgroundColor(Color.parseColor(colors[2]));
            else if(i>=29 && i<=40)
                button.setBackgroundColor(Color.parseColor(colors[3]));

            button.setId(i + 2);
            button.setText("1" );

            button.setTextSize(7);
            button.setTextColor(Color.parseColor("#FF0000"));
            button.setAlpha(.05f);

            mProgressBarLinearLayout.addView(button, mProgressBarButton.getLayoutParams());
            if(i == mProgressBarButtonArray.length)
                mProgressBarButtonArray[mProgressBarButtonArray.length-1] = button;
            else
                mProgressBarButtonArray[i] = button;
        }
        */



        changeFonts();
        mBMICalculator = new BMICalculator(mHeightSeekBar, mWeightSeekBar, mHeightTextView,
                mWeightTextView, mBMIStatue, mBMIValue, heightScaleTextViewArray,
                weightScaleTextViewArray, mScaleValueDisplayFrameLayout, this);





        /*
        String fontPath = "fonts/1942.ttf";
        String fontPath2 = "fonts/daniel.ttf";
        String fontPath3 = "fonts/danielbd.ttf";


        TextView tv = (TextView) findViewById(R.id.heightTextView);
        TextView tv2 = (TextView) findViewById(R.id.weightTextView);
        TextView tv3 = (TextView) findViewById(R.id.bmiValueTextView);
        TextView tv4 = (TextView) findViewById(R.id.bmiStatusTextView);

        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath3);
        //Typeface tf2 = Typeface.createFromAsset(getAssets(), fontPath3);
        tv.setTypeface(tf);
        tv2.setTypeface(tf);
        tv3.setTypeface(tf);
        tv4.setTypeface(tf);

        LinearLayout ll = (LinearLayout) findViewById(R.id.weightScalelinearLayout);
*/
   /*
        ProgressBar pb = (ProgressBar) findViewById(R.id.progressBarToday);
        pb.setProgress(25);

     Animation an = new RotateAnimation(0.0f, 90.0f, 250f, 273f);
        an.setFillAfter(true);
        pb.startAnimation(an);*/



    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
/*
        HorizontalScrollView hsv = (HorizontalScrollView) findViewById(R.id.horizontalScrollView);
        TextView tv12 = (TextView) findViewById(R.id.ws_tv_12);
        TextView  tv1 = (TextView) findViewById(R.id.ws_tv_1);
        TextView  tv2 = (TextView) findViewById(R.id.ws_tv_2);

        TextView tv21 = (TextView) findViewById(R.id.ws_tv_21);


        int x, y;
        //tv.get()
        x = tv12.getLeft();
        y = tv12.getRight();
        //hsv.smoothScrollTo(91*10, y);
/*
        Log.v(TAG, "tv12 x = " + x);
        Log.v(TAG, "tv12 y = " + y);

        Log.v(TAG, "getWidth = " + hsv.getWidth());
        Log.v(TAG, "getMaxScrollAmount = " + hsv.getMaxScrollAmount());

        Log.v(TAG, "tv1 = " + tv1.getLeft());
        Log.v(TAG, "tv1 = " + tv1.getTop());
        Log.v(TAG, "tv1 = " + tv1.getBottom());

        Log.v(TAG, "tv2 = " + tv2.getLeft());
        Log.v(TAG, "tv2 = " + tv2.getTop());

        Log.v(TAG, "tv21 = " + tv21.getLeft());
        Log.v(TAG, "tv21 = " + tv21.getTop());

        Log.v(TAG, "getScrollX = " + hsv.getScrollX());
        Log.v(TAG, "getScrollY = " + hsv.getScrollY());

        Rect rectf = new Rect();
        tv12.getLocalVisibleRect(rectf);
        Log.v(TAG, "tv12 Left: "+ String.valueOf(rectf.left));

        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
        hsv.smoothScrollTo(ll.getChildAt(9).getLeft(), ll.getChildAt(9).getRight());
        */



    }


    protected void onScrollChanged(int i, int j, int k, int l)
    {
        Log.v(TAG, "i = " + i + "j = " + j + "k = " + k+ "l = " + l);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bmi, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Log.v(TAG, "item.getTitle().toString() = "+ item.getTitle().toString());
        Log.v(TAG, "id = "+ id);

        Toast.makeText(getBaseContext(), item.getTitle().toString(), Toast.LENGTH_SHORT).show();



        if (id == R.id.save) {
            Log.v(TAG, "Save Button Clicked" );
            datasource = new BMIDataSource(this);
            datasource.open();
            datasource.createBMIResult(new SimpleDateFormat("EEE, dd MMM yyyy HH:mm").format(new Date())+": My BMI=" + mBMIValue.getText());
            datasource.close();


        }
        else if (id == R.id.share) {
            Log.v(TAG, "Share Button Clicked" );
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "Hi, My BMI is "+ mBMIValue.getText()
                    + ", what's yours? check it out with this great app \""
                    + getString(R.string.app_name) + " "+ getString(R.string.version) + "\"");
            intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Check out this app!");
            startActivity(Intent.createChooser(intent, "Share"));
        }
        else if (id == R.id.history) {
            Log.v(TAG, "History Button Clicked" );
            startActivity(new Intent(this, BMIHistory.class));
        }
        else if (id == R.id.location) {
            Log.v(TAG, "location Button Clicked MapYourLocation" );
            startActivity(new Intent(this, YourLocation.class));
        }
        return super.onOptionsItemSelected(item);
    }

    public  void toast(String msg)
    {
        Toast.makeText(getBaseContext(), msg , Toast.LENGTH_SHORT).show();
    }


    private void initDataToSeekbar() {
        progressItemList = new ArrayList<ProgressItem>();

        // yellow span
        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = (yellowSpan / totalSpan) * 100;
        mProgressItem.color = R.color.yellow;
        progressItemList.add(mProgressItem);
        // green span
        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = (greenSpan / totalSpan) * 100;
        mProgressItem.color = R.color.green;
        progressItemList.add(mProgressItem);
        // blue span
        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = (blueSpan / totalSpan) * 100;
        mProgressItem.color = R.color.orange;
        progressItemList.add(mProgressItem);
        // red span
        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = ((redSpan / totalSpan) * 100);
        Log.i("Mainactivity", mProgressItem.progressItemPercentage + "");
        mProgressItem.color = R.color.red;
        progressItemList.add(mProgressItem);



        // greyspan
        mProgressItem = new ProgressItem();
        mProgressItem.progressItemPercentage = (darkGreySpan / totalSpan) * 100;
        mProgressItem.color = R.color.grey;
        progressItemList.add(mProgressItem);

        seekbar.initData(progressItemList);
        seekbar.invalidate();
    }



}
