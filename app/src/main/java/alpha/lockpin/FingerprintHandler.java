package alpha.lockpin;

/**
 * Created by loga on 9/7/2017.
 */

        import android.annotation.TargetApi;
        import android.app.Activity;
        import android.content.Context;
        import android.content.Intent;
        import android.content.pm.PackageManager;
        import android.hardware.fingerprint.FingerprintManager;
        import android.Manifest;
        import android.os.Build;
        import android.os.CancellationSignal;
        import android.os.Handler;
        import android.support.v4.app.ActivityCompat;
        import android.view.animation.Animation;
        import android.view.animation.AnimationUtils;
        import android.widget.ImageView;
        import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

    private CancellationSignal cancellationSignal;
    private Context context;


    public FingerprintHandler(Context mContext) {
        context = mContext;
    }

    public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {

        cancellationSignal = new CancellationSignal();
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {

        //I’m going to display the results of fingerprint authentication as a series of toasts.
        //Here, I’m creating the message that’ll be displayed if an error occurs//

        Toast.makeText(context, "Authentication error\n" + errString, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationFailed() {
        //Toast.makeText(context, "Authentication failed", Toast.LENGTH_SHORT).show();
        final ImageView fingerprint = (ImageView) ((Activity)context).findViewById(R.id.fingerprint);
        fingerprint.setImageResource(R.drawable.ic_fingerprint_red_24dp);
        fingerprint.clearAnimation();
        Animation vibrate = AnimationUtils.loadAnimation(context, R.anim.vibrate);
        fingerprint.setAnimation(vibrate);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                fingerprint.setImageResource(R.drawable.ic_fingerprint_black_24dp);
            }
        }, 1000);

    }

    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        Toast.makeText(context, "Authentication help\n" + helpString, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationSucceeded(
            FingerprintManager.AuthenticationResult result) {

        Toast.makeText(context, "Success!", Toast.LENGTH_LONG).show();
        Intent i = new Intent(context,MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        context.startActivity(i);
    }

}

