package alpha.lockpin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button create,change,set,remove,enableFingerprint,disableFingerprint;
    EditText pin;
    TextView currentPin;

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        create = (Button)findViewById(R.id.create);
        change = (Button)findViewById(R.id.change);
        remove = (Button)findViewById(R.id.remove);
        set = (Button)findViewById(R.id.set);
        pin = (EditText)findViewById(R.id.pin);
        currentPin = (TextView)findViewById(R.id.currentPin);
        enableFingerprint = (Button)findViewById(R.id.enableFingerprint);
        disableFingerprint =(Button)findViewById(R.id.diableFingerprint);

        sharedpreferences = getApplicationContext().getSharedPreferences("MyPref", 0);

        if(getPin().length()==4){
            change.setVisibility(View.VISIBLE);
            create.setVisibility(View.GONE);
            remove.setVisibility(View.VISIBLE);
            currentPin.setText("Current Pin: "+getPin());
        }
        else{
            currentPin.setText("No pin has been assigned");
            create.setVisibility(View.VISIBLE);
            change.setVisibility(View.GONE);
            remove.setVisibility(View.GONE);
        }

        if(getFingerprintState()==1){
            enableFingerprint.setVisibility(View.GONE);
            disableFingerprint.setVisibility(View.VISIBLE);
        }
        else{
            enableFingerprint.setVisibility(View.VISIBLE);
            disableFingerprint.setVisibility(View.GONE);
        }

        enableFingerprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFingerprintState(1);
                enableFingerprint.setVisibility(View.GONE);
                disableFingerprint.setVisibility(View.VISIBLE);
            }
        });

        disableFingerprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFingerprintState(0);
                enableFingerprint.setVisibility(View.VISIBLE);
                disableFingerprint.setVisibility(View.GONE);
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,changePin.class);
                startActivity(i);
                finish();
            }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,changePin.class);
                startActivity(i);
                finish();
            }
        });

        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String PIN = pin.getText().toString();
                setPin(PIN);
                Toast.makeText(MainActivity.this,"Pin has been set - "+PIN,Toast.LENGTH_LONG).show();
                if(getPin().length()==4){
                    create.setVisibility(View.VISIBLE);
                    change.setVisibility(View.GONE);
                }
                else {
                    create.setVisibility(View.GONE);
                    change.setVisibility(View.VISIBLE);
                }

                set.setVisibility(View.GONE);

                Intent i = new Intent(MainActivity.this,lock.class);
                startActivity(i);
                finish();
            }
        });

        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                create.setVisibility(View.VISIBLE);
                change.setVisibility(View.GONE);
                set.setVisibility(View.GONE);
                pin.setVisibility(View.GONE);
                remove.setVisibility(View.GONE);
                setPin("");
                currentPin.setText("No pin has been assigned");
            }
        });


    }

    public void setPin(String pin){
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("PIN", pin);
        editor.commit();
    }
    public String getPin(){
        try{
            return sharedpreferences.getString("PIN","");
        }
        catch (Exception e){
            return "";
        }
    }

    public void setFingerprintState(int dec){
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt("fingerprint",dec);
        editor.commit();
    }
    public int getFingerprintState(){
        try{
            return sharedpreferences.getInt("fingerprint",0);
        }
        catch (Exception e){
            return 0;
        }
    }
}
