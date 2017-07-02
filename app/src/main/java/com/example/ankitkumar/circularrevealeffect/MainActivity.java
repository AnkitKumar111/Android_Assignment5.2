package com.example.ankitkumar.circularrevealeffect;

import android.animation.Animator;

import android.animation.AnimatorListenerAdapter;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    AlertDialog alertDialog;
    ImageView imageView;

    Button showDialogButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final View dialogView = View.inflate(MainActivity.this,R.layout.custom_dialog_view,null);

        // getting views from xml based on ID
        showDialogButton = (Button) findViewById(R.id.buttonDialog);
        imageView = (ImageView) dialogView.findViewById(R.id.SuccessImage);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                revealShow(dialogView, false, null);
            }
        }); // Setting onClicklistener for ImageView on click of this image it will hide dialog with circular effect.

        AlertDialog.Builder builder = new  AlertDialog.Builder(MainActivity.this);
        builder.setView(dialogView); // Using builder to set View to alert dialog
        builder.setCancelable(false); // Making not to hide on click outside of Dialog
        alertDialog = builder.create();

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                revealShow(dialogView,true, null);
            }
        }); // Setting show listener for dialog whenever dialog opens it gets called
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // Making alertDialog window Transparent

        showDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.show();
                Toast.makeText(MainActivity.this,"Click on Tick to hide",Toast.LENGTH_SHORT).show();

            }
        }); // Showing alert dialog with animation


    }
    private void revealShow(View rootView, boolean reveal, final AlertDialog dialog) {
        final View view = rootView.findViewById(R.id.customAlertDialog);

        // TO OPEN FROM CENTER OF DIALOG
//        int w = view.getWidth();
//        int h = view.getHeight();


        // TO Open from top and center of Button
        int w = (showDialogButton.getLeft() + showDialogButton.getRight());
        int h = (showDialogButton.getTop() + showDialogButton.getBottom()) / 2;

        float maxRadius = (float) Math.sqrt(w * w/ 4 + h * h / 4);

        if (reveal) { // if yes grows the animation and makes view as VISIBLE
            Animator revealAnimator = ViewAnimationUtils.createCircularReveal(view,w/2,h/2,0,maxRadius);
            view.setVisibility(View.VISIBLE);
            revealAnimator.start();

        } else { // else No grows to inside of dialog
            Animator anim = ViewAnimationUtils.createCircularReveal(view,w/2,h/2,maxRadius,0);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    alertDialog.dismiss(); // Dismisses the Alert Dialog
                    view.setVisibility(View.INVISIBLE);
                }
            });
            anim.start();
        }
    }

}

