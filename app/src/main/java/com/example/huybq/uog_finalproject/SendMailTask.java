package com.example.huybq.uog_finalproject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class SendMailTask extends AsyncTask {

    private ProgressDialog statusDialog;
    private Activity sellActivity;

    public SendMailTask(Activity activity) {
        sellActivity = activity;
    }

    protected void onPreExecute() {
        statusDialog = new ProgressDialog(sellActivity);
        statusDialog.setMessage("Getting ready...");
        statusDialog.setIndeterminate(false);
        statusDialog.setCancelable(false);
        statusDialog.show();
    }

    @Override
    protected Object doInBackground(Object... args) {
        try {
            //Log.i("SendMailTask", "About to instantiate GMail...");
            //publishProgress("Processing input....");
            Gmail androidEmail = new Gmail(args[0].toString(),
                    args[1].toString(), (List) args[2], args[3].toString(),
                    args[4].toString());
            //publishProgress("Preparing mail message....");
            androidEmail.createEmailMessage();
            publishProgress("Please wait....");
            androidEmail.sendEmail();
            //publishProgress("Email Sent.");
            Log.i("SendMailTask", "Mail Sent.");
        } catch (Exception e) {
            publishProgress(e.getMessage());
            Log.e("SendMailTask", e.getMessage(), e);
        }
        return null;
    }

    @Override
    public void onProgressUpdate(Object... values) {
        statusDialog.setMessage(values[0].toString());
    }

    @Override
    public void onPostExecute(Object result) {
        statusDialog.dismiss();
        Toast.makeText(sellActivity, "successfully", Toast.LENGTH_SHORT).show();
        sellActivity.finish();
        sellActivity.startActivity(new Intent(sellActivity, MainActivity.class));
    }
}
