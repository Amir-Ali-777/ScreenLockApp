package com.example.gestures;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener {

    static final int RESULT_ENABLE = 1 ;
    DevicePolicyManager deviceManger ;
    ComponentName compName ;
    private boolean check = false;

    GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gestureDetector = new GestureDetector(MainActivity.this, MainActivity.this);

        deviceManger = (DevicePolicyManager)
                getSystemService(Context. DEVICE_POLICY_SERVICE) ;
        compName = new ComponentName( this, MyAdmin. class ) ;
        boolean active = deviceManger .isAdminActive( compName ) ;

        if(!active){
            Intent intent = new Intent(DevicePolicyManager. ACTION_ADD_DEVICE_ADMIN ) ;
            intent.putExtra(DevicePolicyManager. EXTRA_DEVICE_ADMIN , compName ) ;
            intent.putExtra(DevicePolicyManager. EXTRA_ADD_EXPLANATION , "You should enable the app!" ) ;
            startActivityForResult(intent , RESULT_ENABLE ) ;
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.gestureDetector.onTouchEvent(event);
        // Be sure to call the superclass implementation
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent event) {

        return true;
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {

        return false;
    }

    @Override
    public void onLongPress(MotionEvent event) {
        Toast.makeText(MainActivity.this,"Long Press",Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {
       // Toast.makeText(MainActivity.this,"Scroll",Toast.LENGTH_SHORT).show();

        return false;
    }

    @Override
    public void onShowPress(MotionEvent event) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {

        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent event) {

        //Toast.makeText(MainActivity.this, "Double Tap on Screen is Working.", Toast.LENGTH_LONG).show();
        //deviceManger .lockNow() ;
        check = true;
        onPause();

        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent event) {

        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent event) {

        return false;
    }

    @Override
    protected void onActivityResult ( int requestCode , int resultCode , @Nullable Intent
            data) {
        super .onActivityResult(requestCode , resultCode , data) ;
        switch (requestCode) {
            case RESULT_ENABLE :
                if (resultCode == Activity. RESULT_OK ) {
                    Toast.makeText(getApplicationContext(),"succeeded",Toast.LENGTH_SHORT).show();
                } else {
                    Toast. makeText (getApplicationContext() , "Failed!" ,
                            Toast. LENGTH_SHORT ).show() ;
                }
                return;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(check){
            deviceManger.lockNow();
            onDestroy();
        }
    }
}
