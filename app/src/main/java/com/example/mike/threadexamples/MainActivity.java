package com.example.mike.threadexamples;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

//thread is telling something to happen in the background
//while the main function is still operational and running

//A handler is used to update a screen (do not do it within a thread!)

public class MainActivity extends AppCompatActivity {

    //used to update the screen
    //part of threaded application that sits on the main thread
    Handler handle = new Handler() {
        @Override
    public void handleMessage(Message msg) {
            //this connects the textView to the xml
            TextView textView = (TextView)findViewById(R.id.wonder_text);
            //sets the text of the textView
            textView.setText("somthing just happened");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //method button is looking for
    public void doSomething(View v) {

        //what code do you want to stick inside your thread
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //gets the future time then adds 10 seconds
                long futureTime = System.currentTimeMillis()+10000;
                //tells it to keep going while the time less than the variable above
                while(System.currentTimeMillis() < futureTime){
                    //this keyword prevents multiple threads from bumping into each other
                    //or trying to do the same thing
                    synchronized (this){
                        try{
                            wait(futureTime-System.currentTimeMillis());
                        }catch(Exception e){

                        }
                    }
                }
                //calls the handler
                handle.sendEmptyMessage(0);
            }
        };
        //creates the thread
        Thread myThread = new Thread(runnable);
        //code to start the thread
        myThread.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
