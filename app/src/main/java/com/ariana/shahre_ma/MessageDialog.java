package com.ariana.shahre_ma;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by ariana on 9/2/2015.
 */
public class MessageDialog
{
    Context context;
    public MessageDialog(Context context)
    {
        this.context=context;
    }

    public void ShowMessage(String title,String message,String textbutton,String value)
    {
        AlertDialog alertDialog=new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(textbutton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();
    }

    public  void ShowMessage(String title,String message,String textbutton,String value1,String value2)
    {
        AlertDialog alertDialog=new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(textbutton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();
        alertDialog.setButton2("", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }



}


