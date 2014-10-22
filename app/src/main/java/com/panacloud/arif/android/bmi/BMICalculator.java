package com.panacloud.arif.android.bmi;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.panacloud.arif.android.R;

import java.text.DecimalFormat;

/**
 * Created by Arif on 10/1/2014.
 */
public class BMICalculator {

    private static final String TAG = "BMI:BMICalculator";
    private SeekBar mHeightSeekBar, mWeightSeekBar;
    private TextView mHeightTextView, mWeightTextView, mBMIStatus, mBMIValue,
            heightScaleTextViewArray[], weightScaleTextViewArray[];

    FrameLayout.LayoutParams layoutParams;
    FrameLayout mScaleValueDisplayFrameLayout;
    boolean isMetric = true;


    private BMIActivity mBMIActivity;
    //MediaPlayer myMediaPlayer;
    ImageView bmiImageView;
    boolean doPlay = true;



    public BMICalculator(SeekBar mHeightSeekBar, SeekBar mWeightSeekBar, TextView mHeightTextView,
                         TextView mWeightTextView, TextView mBMIStatue, TextView mBMIValue,
                         TextView[] heightScaleTextViewArray, TextView[] weightScaleTextViewArray,
                         FrameLayout mScaleValueDisplayFrameLayout,
                         Activity activity)
    {
        //Log.v(TAG, "Creating BMICalculator Object...");
        this.mHeightSeekBar     = mHeightSeekBar;
        this.mWeightSeekBar     = mWeightSeekBar;
        this.mHeightTextView    = mHeightTextView;
        this.mWeightTextView    = mWeightTextView;
        this.mBMIStatus = mBMIStatue;
        this.mBMIValue          = mBMIValue;
        this.heightScaleTextViewArray = heightScaleTextViewArray;
        this.weightScaleTextViewArray = weightScaleTextViewArray;
        this.mBMIActivity = (BMIActivity) activity;
        this.mScaleValueDisplayFrameLayout = mScaleValueDisplayFrameLayout;

       // myMediaPlayer = MediaPlayer.create(mBMIActivity, R.raw.alarm);
        bmiImageView = (ImageView) mBMIActivity.findViewById(R.id.bmiImageView);

        init();

        //Log.v(TAG,"TEST = 5.9+1 = "+calculateFeet("5.9", 1));
        //Log.v(TAG,"TEST = 5.11+1 = "+calculateFeet("5.11", 1));
        //Log.v(TAG,"TEST = 5.0+1 = "+calculateFeet("5.0", 1));

        //Log.v(TAG,"TEST = 5.9-1 = "+calculateFeet("5.9", -1));
        //Log.v(TAG,"TEST = 5.11-1 = "+calculateFeet("5.11", -1));
        //Log.v(TAG,"TEST = 5.0-1 = "+calculateFeet("5.0", -1));



        //Log.v(TAG,"TEST = 5.9+2 = "+calculateFeet("5.9", 2));
        //Log.v(TAG,"TEST = 5.11+2 = "+calculateFeet("5.11", 2));
        //Log.v(TAG,"TEST = 5.0+2 = "+calculateFeet("5.0", 2));

        //Log.v(TAG,"TEST = 5.9-2 = "+calculateFeet("5.9", -2));
        //Log.v(TAG,"TEST = 5.11-2 = "+calculateFeet("5.11", -2));
        //Log.v(TAG,"TEST = 5.0-2 = "+calculateFeet("5.0", -2));

    }

    private void init()
    {




        //Log.v(TAG, "Initializing BMICalculator Object...");
        mHeightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            private int lastHeightScaleValue = 50;
            int lastProgress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

               /* //int increment = (progress > lastHeightScaleValue)? progress - lastHeightScaleValue: lastHeightScaleValue - progress;
                //int increment = progress - lastHeightScaleValue;
                int increment = lastHeightScaleValue - progress;

                //Log.v(TAG, Integer.parseInt("777") + "onProgressChanged - progress=" + progress + " - fromUser=" + fromUser + " - increment=" + increment);
                //Log.d(TAG, "mTextView.getText()="+(Integer.parseInt(mHeightTextView.getText().toString())+ progress)+ " --> " + (5-9) + " --> " +((progress > lastHeightScaleValue)? progress - lastHeightScaleValue: lastHeightScaleValue - progress));
                //Log.v(TAG, "lastHeightScaleValue="+lastHeightScaleValue);
                */

                int increment = 0;

                if(lastProgress==0)
                {
                    lastProgress = progress;
                    //Log.d(TAG, "lastProgress set to --> "+lastProgress);
                }

                //Log.d(TAG, "lastProgress = "+lastProgress + " - progress = " + progress);
                if(lastProgress-progress > 0)
                {
                    increment = 1;
                    //Log.d(TAG, "Direction: decreasing...");
                }
                else
                {
                    increment = -1;
                    //Log.d(TAG, "Direction: increasing...");
                }

                lastProgress = progress;

                //Log.d(TAG, "FFF = " + mHeightTextView.getText().toString());

                //byte bmi = calculateBMIMetric(Integer.parseInt(mHeightTextView.getText().toString())+ increment, Integer.parseInt(mWeightTextView.getText().toString()));
                double bmi;
                if(isMetric)
                    bmi = calculateBMIMetric(Double.parseDouble(mHeightTextView.getText().toString())+ increment, Double.parseDouble(mWeightTextView.getText().toString()));
                else
                    bmi = calculateBMIImperial(Double.valueOf(calculateFeet((mHeightTextView.getText().toString()), increment)), Double.parseDouble(mWeightTextView.getText().toString()));

                //Log.v(TAG,"X bmi = " +bmi);

                //if(bmi<0 || bmi > 40  || (Integer.parseInt(mHeightTextView.getText().toString())+ increment)<0)
                if(bmi<0 || bmi > 40  || (Double.valueOf(mHeightTextView.getText().toString())+ increment)<0)
                {
                    //Log.v(TAG, "Skip these BMI values....");
                    Toast.makeText(mBMIActivity.getBaseContext(), "BMI value is not in range (0-40)", Toast.LENGTH_SHORT).show();
                    return;
                }



                //Log.v(TAG," isMetric = " +isMetric);


                if(isMetric) {
                    //Log.v(TAG, "cmToFt = " + cmToFt(Integer.parseInt(mHeightTextView.getText().toString()) + increment + ""));
                    mHeightTextView.setText((Integer.parseInt(mHeightTextView.getText().toString()) + increment + ""));

                    for(int i=0; i<heightScaleTextViewArray.length; i++)
                    {
                        heightScaleTextViewArray[i].setText(Integer.parseInt(heightScaleTextViewArray[i].getText().toString()) + increment + "" );
                    }

                }
                else {

                    mHeightTextView.setText( calculateFeet(mHeightTextView.getText().toString(), increment));

                    for(int i=0; i<heightScaleTextViewArray.length; i++)
                    {
                        heightScaleTextViewArray[i].setText(calculateFeet(heightScaleTextViewArray[i].getText().toString(), increment ) );
                    }
                }

                // mHeightTextView.setText( (Integer.parseInt(mHeightTextView.getText().toString()) + increment + "") );




                /*
                heightScaleTextViewArray[0].setText(Integer.parseInt(heightScaleTextViewArray[0].getText().toString()) + increment + "" );
                heightScaleTextViewArray[1].setText(Integer.parseInt(heightScaleTextViewArray[1].getText().toString()) + increment + "" );
                heightScaleTextViewArray[2].setText(Integer.parseInt(heightScaleTextViewArray[2].getText().toString()) + increment + "" );
                heightScaleTextViewArray[3].setText(Integer.parseInt(heightScaleTextViewArray[3].getText().toString()) + increment + "" );
                heightScaleTextViewArray[4].setText(Integer.parseInt(heightScaleTextViewArray[4].getText().toString()) + increment + "" );
                */



                //byte bmi = calculateBMIMetric(Integer.parseInt(mHeightTextView.getText().toString()), Integer.parseInt(mWeightTextView.getText().toString()));
                //mBMIValue.setText(bmi+"");

                //Log.v(TAG, "mBMIValue = "+mBMIValue);

                lastHeightScaleValue = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                //Log.v(TAG, "onStartTrackingTouch - seekBar="+seekBar);

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                //Log.v(TAG, "onStopTrackingTouch - seekBar="+seekBar);

            }
        });


        mWeightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            private int lastWeightScaleValue = 50;
            int lastIncrementValue = 0;
            int lastProgress = 0;
            //  String addSpace = "";

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {



                //int increment = progress - lastWeightScaleValue;
                //int increment = progress > lastWeightScaleValue? lastWeightScaleValue - progress : progress - lastWeightScaleValue;
/*
                int increment = lastWeightScaleValue - progress;

                //Log.v(TAG, Integer.parseInt("777") + "onProgressChanged - progress=" + progress
                        + " - increment=" + increment  + " - lastWeightScaleValue=" + lastWeightScaleValue
                        + " - lastIncrementValue"+ lastIncrementValue);

                //if(increment > 3 || -3 > increment)
                if( (lastIncrementValue - increment) > 3 || -3 > (lastIncrementValue - increment))
                {
                    //Log.d(TAG, "Skiping...(-1>-2)="+ (-1>-2));
                    //lastWeightScaleValue = progress;
                    lastIncrementValue = increment;
                    return;
                }
                else
                {
                    increment = lastIncrementValue - increment;
                    //Log.d(TAG, "New Increment Value = "+ increment);
                }
                */

                int increment = 0;

                if(lastProgress==0)
                {
                    lastProgress = progress;
                    //Log.d(TAG, "lastProgress set to --> "+lastProgress);
                }

                //Log.d(TAG, "lastProgress = "+lastProgress + " - progress = " + progress);
                if(lastProgress-progress > 0)
                {
                    increment = 1;
                    //Log.d(TAG, "Direction: decreasing...");
                }
                else
                {
                    increment = -1;
                    //Log.d(TAG, "Direction: increasing...");
                }

                lastProgress = progress;

                //byte bmi = calculateBMIMetric(Integer.parseInt(mHeightTextView.getText().toString()), Integer.parseInt(mWeightTextView.getText().toString())+ increment);

                double bmi;
                if(isMetric)
                    bmi = calculateBMIMetric(Double.parseDouble(mHeightTextView.getText().toString()), Double.parseDouble(mWeightTextView.getText().toString())+ increment);
                else
                    bmi = calculateBMIImperial(Double.parseDouble(mHeightTextView.getText().toString()), Double.parseDouble(mWeightTextView.getText().toString())+ increment);


                //Log.v(TAG,"Y bmi = " +bmi);


                if(bmi<0 || bmi > 40 || (Double.parseDouble(mWeightTextView.getText().toString())+ increment)<0)
                {
                    //Log.v(TAG, "Skip these BMI values....");
                    Toast.makeText(mBMIActivity.getBaseContext(), "BMI value is not in range (0-40)", Toast.LENGTH_SHORT).show();
                    return;
                }



                //Log.v(TAG, "mWeightTextView.getText()="+mWeightTextView.getText());


                /*
                if(Integer.parseInt(mWeightTextView.getText().toString()) < 100)
                    addSpace = " ";*/


                mWeightTextView.setText(Integer.parseInt(mWeightTextView.getText().toString()) + increment + "");

                for(int i=0; i<weightScaleTextViewArray.length; i++)
                {
                    weightScaleTextViewArray[i].setText(Integer.parseInt(weightScaleTextViewArray[i].getText().toString()) + increment  + "");
                }




                /*
                weightScaleTextViewArray[0].setText(Integer.parseInt(weightScaleTextViewArray[0].getText().toString()) + increment  + "");
                weightScaleTextViewArray[1].setText(Integer.parseInt(weightScaleTextViewArray[1].getText().toString()) + increment  + "");
                weightScaleTextViewArray[2].setText(Integer.parseInt(weightScaleTextViewArray[2].getText().toString()) + increment  + "");
                weightScaleTextViewArray[3].setText(Integer.parseInt(weightScaleTextViewArray[3].getText().toString()) + increment  + "");
                weightScaleTextViewArray[4].setText(Integer.parseInt(weightScaleTextViewArray[4].getText().toString()) + increment  + "");*/


                //byte bmi = calculateBMIMetric(Integer.parseInt(mHeightTextView.getText().toString()), Integer.parseInt(mWeightTextView.getText().toString()));
                //mBMIValue.setText(bmi+"");

                lastWeightScaleValue = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mScaleValueDisplayFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                //Log.v(TAG, "ScaleValueDisplayFrameLayout Clicked...");

                if(isMetric){
                    ((TextView) mBMIActivity.findViewById(R.id.heightUnitTextView)).setText("ft");
                    ((TextView) mBMIActivity.findViewById(R.id.weightUnitTextView)).setText("lbs");

                    mHeightTextView.setText(cmToFt(mHeightTextView.getText().toString()));
                    mWeightTextView.setText(kgToLb(mWeightTextView.getText().toString())+"");

                    for(int i=0; i<heightScaleTextViewArray.length; i++)
                    {
                        // heightScaleTextViewArray[i].setText(cmToFt(heightScaleTextViewArray[i].getText().toString()) );
                        heightScaleTextViewArray[i].setText(calculateFeet(mHeightTextView.getText().toString(), i+ -2) );
                        //Log.v(TAG, "vvv  = " + heightScaleTextViewArray[i].getText().toString());
                        weightScaleTextViewArray[i].setText( (Integer.valueOf(mWeightTextView.getText().toString())+ i+ -2) + "");
                    }


                    //decimal point problem which calculate different bmi on toggle
                    calculateBMIImperial(Double.parseDouble(calculateFeet((mHeightTextView.getText().toString()), 0)), Double.parseDouble(mWeightTextView.getText().toString()));

                    isMetric=false;
                }
                else
                {
                    ((TextView) mBMIActivity.findViewById(R.id.heightUnitTextView)).setText("cm");
                    ((TextView) mBMIActivity.findViewById(R.id.weightUnitTextView)).setText("kg");

                    mHeightTextView.setText(ftToCm(mHeightTextView.getText().toString()));
                    mWeightTextView.setText(lbToKg(mWeightTextView.getText().toString())+"");

                    //Log.v(TAG, "mHeightTextView.getText().toString() = " + mHeightTextView.getText().toString());

                    for(int i=0; i<heightScaleTextViewArray.length; i++)
                    {
                        //heightScaleTextViewArray[i].setText(ftToCm(heightScaleTextViewArray[i].getText().toString()) );
                        heightScaleTextViewArray[i].setText(   Integer.valueOf(mHeightTextView.getText().toString()) + (i+ -2) +"");
                        weightScaleTextViewArray[i].setText( (Integer.valueOf(mWeightTextView.getText().toString())+ i+ -2) + "");
                    }

                    //decimal point problem which calculate different bmi on toggle
                    calculateBMIMetric(Double.parseDouble(mHeightTextView.getText().toString()), Double.parseDouble(mWeightTextView.getText().toString()));
                    isMetric=true;
                }



            }
        });
    }



    private byte calculateBMIImperial(double height, double weight)
    {
        //Log.v(TAG, "calculateBMIImperial : height="+height +" - weight="+weight );

        String ftStr = height+"";
        String whole = ftStr.substring(0, ftStr.indexOf("."));
        String friction = ftStr.substring(ftStr.indexOf(".")+1, ftStr.length());
/*
        System.out.println("whole = " + whole);
        System.out.println("friction = " + friction + "\n\n");*/

        int wholeInteger = Integer.parseInt(whole);
        int frictionInteger = Integer.parseInt(friction);

        height = (wholeInteger*12) + frictionInteger;


        //Log.v(TAG, "calculateBMIImperial : height in Inch ="+ height );

        //height = height*12;

        final double KILOGRAMS_PER_POUND = 0.453;
        final double METERS_PER_INCH = 0.026;

        double weightInKilogram = weight * KILOGRAMS_PER_POUND;
        double heightInMeters = height * METERS_PER_INCH;
        double bmi = weightInKilogram / (heightInMeters * heightInMeters);

        bmi = weight / (height*height) * 703;


        //Log.v(TAG, "calculateBMIImperial: BMI = "+ bmi);

        bmi = Math.round(bmi);
        //Log.v(TAG, "Math.round: calculateBMIImperial: BMI = "+ bmi);


        setBMIStatus((int)bmi);
        setBMIValue((int)bmi);


        return (byte) bmi;


    }


    private double calculateBMIMetric(double height, double weight)
    {
        //Log.v(TAG, "calculateBMIMetric : height="+height +" - weight="+weight );

        double bmi = weight / (height * height) * 10000;



        //Log.v(TAG, "calculateBMIMetric : BMI = "+ bmi);
        //bmi = Math.round(bmi);
        //Log.v(TAG, "Math.round: calculateBMIMetric: BMI = "+ bmi);



        setBMIStatus(bmi);
        setBMIValue(bmi);

        //Log.v(TAG, "calculateBMIMetric : Now returning BMI Value" );


        /*
        DecimalFormat df = new DecimalFormat("##.#");
        bmi = Double.valueOf(df.format(bmi));

*/
        return   bmi;


    }

    private void setBMIStatus(double bmiValue)
    {
        if(bmiValue < 19) {
            mBMIStatus.setText("UnderWeight");
            mBMIStatus.setTextColor(mBMIActivity.getResources().getColor(R.color.yellow) );
        }
        else if(bmiValue > 18 && bmiValue < 26) {
            mBMIStatus.setText("Good Weight");
            mBMIStatus.setTextColor(mBMIActivity.getResources().getColor(R.color.green) );
        }
        else if(bmiValue > 25 && bmiValue < 30) {
            mBMIStatus.setText("Over Weight");
            mBMIStatus.setTextColor(mBMIActivity.getResources().getColor(R.color.orange) );
        }
        else if(bmiValue > 29 ) {
            mBMIStatus.setText("Obese");
            mBMIStatus.setTextColor(mBMIActivity.getResources().getColor(R.color.red) );
        }
    }

    private void setBMIValue(double bmiValue)
    {
        DecimalFormat df = new DecimalFormat("##.#");
        bmiValue = Double.valueOf(df.format(bmiValue));

        if(bmiValue < 0 || bmiValue > 40)
            return;

        if(bmiValue < 19) {
            mBMIValue.setText(bmiValue+"");
            //mBMIValue.setTextColor(Color.parseColor("#FFBB33"));D7DF01
            mBMIValue.setTextColor(mBMIActivity.getResources().getColor(R.color.yellow) );
        }
        else if(bmiValue > 18 && bmiValue < 26) {
            mBMIValue.setText(bmiValue+"");
            mBMIValue.setTextColor(mBMIActivity.getResources().getColor(R.color.green) );
        }
        else if(bmiValue > 25 && bmiValue < 30) {
            mBMIValue.setText(bmiValue+"");
            mBMIValue.setTextColor(mBMIActivity.getResources().getColor(R.color.orange));
        }
        else if(bmiValue > 29 ) {
            mBMIValue.setText(bmiValue+"");
            mBMIValue.setTextColor(mBMIActivity.getResources().getColor(R.color.red));
        }
        //changeBMIMeterColor(bmiValue);
        changeBMIImage(bmiValue);
    }

    private int lbToKg(String lb)
    {
        return (int) Math.round(Integer.valueOf(lb) * 0.45359237);
    }

    private int kgToLb(String kg)
    {
        return (int) Math.round(Integer.valueOf(kg) * 2.20462262);
    }



    private String cmToFt(String cmStr)
    {
        //Log.v(TAG, "cmStr = " + cmStr);

        int cm = Integer.parseInt(cmStr);
        return format(cm * 0.032808399 )+"";

    }
    private String ftToCm(String ftStr) {

        //Log.v(TAG, "ftStr = " + ftStr);
        double ft = Double.parseDouble(ftStr);
        return (int)format(ft * 30.48 )+"";
    }

    private double format ( double value) {
        if ( value != 0){
            DecimalFormat df = new DecimalFormat("###.#");
            return Double.valueOf(df.format(value));
        } else {
            return -1;
        }
    }


    private String calculateFeet(String ftStr, int increment)
    {

        String whole = ftStr.substring(0, ftStr.indexOf("."));
        String friction = ftStr.substring(ftStr.indexOf(".")+1, ftStr.length());
/*
        System.out.println("whole = " + whole);
        System.out.println("friction = " + friction + "\n\n");*/

        int frictionInteger = Integer.parseInt(friction);
        int wholeInteger = Integer.parseInt(whole);

        System.out.println("wholeInteger = " + wholeInteger);
        System.out.println("frictionInteger = " + frictionInteger + "\n\n");



        frictionInteger = (frictionInteger + increment);
        System.out.println("xx frictionInteger = " + frictionInteger + "\n\n");

        if(frictionInteger > 11)
        {
            wholeInteger = wholeInteger+1;

            if(frictionInteger > 12)
                frictionInteger=1;
            else if(frictionInteger > 13)
                frictionInteger=2;
            else
                frictionInteger=0;



        }
        else if(frictionInteger < 0)
        {
            wholeInteger = wholeInteger-1;

            if(frictionInteger < -1)
                frictionInteger=10;
            else if(frictionInteger < -2)
                frictionInteger=9;
            else
                frictionInteger=11;
        }


        System.out.println("qqq   wholeInteger = " + wholeInteger + " frictionInteger = " + frictionInteger);
        System.out.println(wholeInteger+"."+frictionInteger);


        return  wholeInteger+"."+frictionInteger;


      /*  double d = Double.parseDouble(ftStr);
        double i = increment/10.0;

        //Log.v("calculateFeet ", "i = " + i);

/*
        double x = d+i;
        //Log.v("calculateFeet ", "d = " + d);

        int a = (int) d; //whole value
        //Log.v("calculateFeet ", "whole value  a = " + a);

        double b = d-a; //friction value
        //Log.v("calculateFeet ", "friction value  b = " + b);

        String wholeStr = "";
        String frictionStr = "";



        if(b == 0.9) {
            frictionStr = "0.10";
        }
        else if(b == 0.10) {
            frictionStr = "0.11";
        }


/*
        if(b == 0.12)
        {
            return a+1.0 +"";
        }
        else if(b == 0.9)
        {
            return a+0.11 +"";
        }

        return x+"";*/



    }

    private void changeBMIMeterColor(double bmiValue)
    {
        Button[] buttons = mBMIActivity.getProgressBarButtonArray();


        for(int i=0; i<buttons.length; i++)
        {
            if(i<=bmiValue)
                buttons[i].setAlpha(1);
            else
                buttons[i].setAlpha(0.05f);

        }
        changeBMIImage(bmiValue);
    }

    private void changeBMIImage(double bmiValue)
    {





        if(bmiValue<19) {
            //myMediaPlayer.stop();

            //myMediaPlayer.release(); //>>> free myMediaPlayer
            bmiImageView.setImageResource(R.drawable.under_weight_32x32);
        }
        else if(bmiValue>=19 && bmiValue<=25) {
            //myMediaPlayer.stop();

            //myMediaPlayer.release(); //>>> free myMediaPlayer
            bmiImageView.setImageResource(R.drawable.ok_weight_32x32);
        }
        else if(bmiValue>=26 && bmiValue<=29) {
            //myMediaPlayer.stop();

            if(!doPlay) {
               // myMediaPlayer.stop();
                //myMediaPlayer.release(); //>>> free myMediaPlayer
            }

            bmiImageView.setImageResource(R.drawable.think_32x32);
            doPlay = true;
        }
        else if(bmiValue>=30 && bmiValue<40) {
            bmiImageView.setImageResource(R.drawable.obese_32x32);
            //myMediaPlayer.pause();

            if(doPlay) {
               // myMediaPlayer.start();
                //myMediaPlayer.setLooping(true);
            }



            doPlay = false;

        }

        mBMIActivity.setSeekbarProgress(bmiValue);
    }






}
