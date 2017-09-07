package alpha.lockpin;

import android.Manifest;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class lock extends Activity {


    Button btn0,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9;
    SharedPreferences sharedpreferences;
    ImageView img1,img2,img3,img4,erase;
    String pin = "";

    private static final String KEY_NAME = "yourKey";
    private Cipher cipher;
    private KeyStore keyStore;
    private KeyGenerator keyGenerator;
    private FingerprintManager.CryptoObject cryptoObject;
    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_lock);
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
        if(getPin().length() == 0){
            Intent i = new Intent(lock.this,MainActivity.class);
            startActivity(i);
            finish();
        }
        Toast.makeText(getApplicationContext(),getPin(),Toast.LENGTH_SHORT).show();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            keyguardManager =
                    (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
            fingerprintManager =
                    (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);

            if (!fingerprintManager.isHardwareDetected()) {
                toast("Your device doesn't support fingerprint authentication");
            }
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                toast("Please enable the fingerprint permission");
            }
            if (!fingerprintManager.hasEnrolledFingerprints()) {
                toast("No fingerprint configured. Please register at least one fingerprint in your device's Settings");
            }


            if (!keyguardManager.isKeyguardSecure()) {
                toast("Please enable lockscreen security in your device's Settings");
            } else {
                try {
                    generateKey();
                }
                catch (FingerprintException  e){
                    e.printStackTrace();
                }
                if (initCipher()) {
                    //If the cipher is initialized successfully, then create a CryptoObject instance//
                    cryptoObject = new FingerprintManager.CryptoObject(cipher);

                    // Here, I’m referencing the FingerprintHandler class that we’ll create in the next section. This class will be responsible
                    // for starting the authentication process (via the startAuth method) and processing the authentication process events//
                    FingerprintHandler helper = new FingerprintHandler(this);
                    helper.startAuth(fingerprintManager, cryptoObject);
                }
            }

        }





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
        setImage();
        if(getPin().equals(pin)){
            Intent i = new Intent(lock.this,MainActivity.class);
            startActivity(i);
            finish();
        }
        else{
            pin = "";
            setImage();
            Toast.makeText(getApplicationContext(), "Wrong PIN", Toast.LENGTH_SHORT).show();
        }
    }

    public String getPin(){
        try{
            return sharedpreferences.getString("PIN","");
        }
        catch (Exception e){
            return "";
        }
    }

    public void toast(String msg){
            Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
        }
    private void generateKey() throws FingerprintException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                // Obtain a reference to the Keystore using the standard Android keystore container identifier (“AndroidKeystore”)//
                keyStore = KeyStore.getInstance("AndroidKeyStore");

                //Generate the key//
                keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");

                //Initialize an empty KeyStore//
                keyStore.load(null);

                //Initialize the KeyGenerator//
                keyGenerator.init(new

                        //Specify the operation(s) this key can be used for//
                        KeyGenParameterSpec.Builder(KEY_NAME,
                        KeyProperties.PURPOSE_ENCRYPT |
                                KeyProperties.PURPOSE_DECRYPT)
                        .setBlockModes(KeyProperties.BLOCK_MODE_CBC)

                        //Configure this key so that the user has to confirm their identity with a fingerprint each time they want to use it//
                        .setUserAuthenticationRequired(true)
                        .setEncryptionPaddings(
                                KeyProperties.ENCRYPTION_PADDING_PKCS7)
                        .build());

                //Generate the key//
                keyGenerator.generateKey();

            } catch (KeyStoreException
                    | NoSuchAlgorithmException
                    | NoSuchProviderException
                    | InvalidAlgorithmParameterException
                    | CertificateException
                    | IOException exc) {
                exc.printStackTrace();
                throw new FingerprintException(exc);
            }
        }

    }

    //Create a new method that we’ll use to initialize our cipher//
    public boolean initCipher() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                //Obtain a cipher instance and configure it with the properties required for fingerprint authentication//
                cipher = Cipher.getInstance(
                        KeyProperties.KEY_ALGORITHM_AES + "/"
                                + KeyProperties.BLOCK_MODE_CBC + "/"
                                + KeyProperties.ENCRYPTION_PADDING_PKCS7);
            } catch (NoSuchAlgorithmException |
                    NoSuchPaddingException e) {
                throw new RuntimeException("Failed to get Cipher", e);
            }

            try {
                keyStore.load(null);
                SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                        null);
                cipher.init(Cipher.ENCRYPT_MODE, key);
                //Return true if the cipher has been initialized successfully//
                return true;
            } catch (KeyPermanentlyInvalidatedException e) {

                //Return false if cipher initialization failed//
                return false;
            } catch (KeyStoreException | CertificateException
                    | UnrecoverableKeyException | IOException
                    | NoSuchAlgorithmException | InvalidKeyException e) {
                throw new RuntimeException("Failed to init Cipher", e);
            }
        }
        return false;
    }

    private class FingerprintException extends Exception {
        public FingerprintException(Exception e) {
            super(e);
        }
    }


}
