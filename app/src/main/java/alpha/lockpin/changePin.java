package alpha.lockpin;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class changePin extends Activity {


    Button btn0,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9;
    SharedPreferences sharedpreferences;
    ImageView img1,img2,img3,img4,erase;
    String pin = "",temp = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pin);
        btn0 = (Button)findViewById(R.id.btn0);
        btn1 = (Button)findViewById(R.id.btn1);
        btn2 = (Button)findViewById(R.id.btn2);
        btn3 = (Button)findViewById(R.id.btn3);
        btn4 = (Button)findViewById(R.id.btn4);
        btn5 = (Button)findViewById(R.id.btn5);
        btn6 = (Button)findViewById(R.id.btn6);
        btn7 = (Button)findViewById(R.id.btn7);
        btn8 = (Button)findViewById(R.id.btn8);
        btn9 = (Button)findViewById(R.id.btn9);
        erase = (ImageView) findViewById(R.id.erase);
        img1 = (ImageView)findViewById(R.id.img1);
        img2 = (ImageView)findViewById(R.id.img2);
        img3 = (ImageView)findViewById(R.id.img3);
        img4 = (ImageView)findViewById(R.id.img4);



        sharedpreferences = getApplicationContext().getSharedPreferences("MyPref", 0);


        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pin.length()<3){
                    entry("0");
                    setImage();
                }
                else
                    check(0);
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pin.length()<3){
                    entry("1");
                    setImage();
                }
                else
                    check(1);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pin.length()<3){
                    entry("2");
                    setImage();
                }
                else
                    check(2);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pin.length()<3){
                    entry("3");
                    setImage();
                }
                else
                    check(3);
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pin.length()<3){
                    entry("4");
                    setImage();
                }
                else
                    check(4);
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pin.length()<3){
                    entry("5");
                    setImage();
                }
                else
                    check(5);
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pin.length()<3){
                    entry("6");
                    setImage();
                }
                else
                    check(6);
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pin.length()<3){
                    entry("7");
                    setImage();
                }
                else
                    check(7);
            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pin.length()<3){
                    entry("8");
                    setImage();
                }
                else
                    check(8);
            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pin.length()<3){
                    entry("9");
                    setImage();
                }
                else
                    check(9);
            }
        });
        erase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pin.length()>=0) {
                    pin = pin.substring(0, pin.length() - 1);
                    setImage();
                }
            }
        });


    }

    public void setImage(){
        switch (pin.length()){
            case 0:
                img1.setImageResource(R.drawable.circle_white);
                img2.setImageResource(R.drawable.circle_white);
                img3.setImageResource(R.drawable.circle_white);
                img4.setImageResource(R.drawable.circle_white);
                break;
            case 1:
                img1.setImageResource(R.drawable.circle_blue);
                img2.setImageResource(R.drawable.circle_white);
                img3.setImageResource(R.drawable.circle_white);
                img4.setImageResource(R.drawable.circle_white);
                break;
            case 2:
                img1.setImageResource(R.drawable.circle_blue);
                img2.setImageResource(R.drawable.circle_blue);
                img3.setImageResource(R.drawable.circle_white);
                img4.setImageResource(R.drawable.circle_white);
                break;
            case 3:
                img1.setImageResource(R.drawable.circle_blue);
                img2.setImageResource(R.drawable.circle_blue);
                img3.setImageResource(R.drawable.circle_blue);
                img4.setImageResource(R.drawable.circle_white);
                break;
            case 4:
                img1.setImageResource(R.drawable.circle_blue);
                img2.setImageResource(R.drawable.circle_blue);
                img3.setImageResource(R.drawable.circle_blue);
                img4.setImageResource(R.drawable.circle_blue);
                break;
        }
    }

    public void entry(String input){
        pin = pin+input;
    }

    public void check(int lastDigit){
        entry(String.valueOf(lastDigit));

        if(temp.equals("")){
            temp = pin;
            pin = "";
            TextView top = (TextView)findViewById(R.id.top);
            top.setText("Confirm your 4-digit passcode");
        }
        else if(temp.equals(pin)){
            setImage();
            setPin(pin);
            Toast.makeText(getApplicationContext(), "PIN Created", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(changePin.this,MainActivity.class);
            startActivity(i);
            finish();
        }
        else{
            pin = "";
            setImage();
            Toast.makeText(getApplicationContext(), "PIN Mismatch", Toast.LENGTH_SHORT).show();
        }
        setImage();
    }

    public void setPin(String pin){
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("PIN", pin);
        editor.commit();
    }


}
