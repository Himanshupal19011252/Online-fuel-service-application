package com.example.ofsa;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


public class Drawer extends AppCompatActivity {

    private Button button1;

    //initialize variable
    DrawerLayout drawerLayout;
    @Override


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        //assign variable
        drawerLayout=findViewById(R.id.drawer_layout);
        button1=findViewById(R.id.order);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Drawer.this,order.class);
                startActivity(intent);
            }
        });

    }



    public  void ClickMenu(View view){
        //open drawer
        openDrawer(drawerLayout);
    }



    public static void openDrawer(DrawerLayout drawerLayout) {
        //Open drawer Layout
        drawerLayout.openDrawer(GravityCompat.START);
    }




    public void ClickLogo(View view){
        //closeDrawer
        closeDrawer(drawerLayout);
    }




    public static void closeDrawer(DrawerLayout drawerLayout) {
        //Close drawer Layout
        //check condition
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            //when drawer is open
            //Close drawer
            drawerLayout.closeDrawer(GravityCompat.START);

        }
    }



    public void ClickHome(View view){
        //Recreate activity
        redirectActivity(this,Drawer.class);
    }



    public  void ClickDashboard(View view){
        //Redirect activity to deshboard
        redirectActivity(this,Dashboard.class);
    }




    public void ClickLogout(View view){
        //Rclose app
        logout(this);
    }



    public void logout(Activity activity) {
        //Initialize alert dialog
        AlertDialog.Builder builder= new AlertDialog.Builder(activity);
        //Set title
        builder.setTitle("Logout");
        //set message
        builder.setMessage("Are you sure you want to logout ?");
        //positive yes button
        builder.setPositiveButton("Yes",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Finish activity
                activity.finishAffinity();
                //Exit app
               // System.exit(0);
                Intent intent=new Intent(Drawer.this,Login.class);
                startActivity(intent);
            }
        });
        //Negative button
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Dismiss dialog
                dialog.dismiss();
            }
        });
        //Show
        builder.show();
    }




    public static void redirectActivity(Activity activity,Class aClass) {

        //Initialize intent
        Intent intent=new Intent(activity,aClass);
        //set flag
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //start activity
        activity.startActivity(intent);
    }




    //@Override
    protected  void noPause() {
        super.onPause();
        //Close drawer
        closeDrawer(drawerLayout);
    }



}
