package adel.twitterclient.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class ActivitySwiping {

    public static void goTOAnotherActivity(Context c, Class<?> clas) {
        Intent i = new Intent(c, clas);
        c.startActivity(i);

    }

    public static void goTOAnotherActivityAndFinish(Activity c, Class<?> clas) {
        Intent i = new Intent(c, clas);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        c.startActivity(i);
        c.finish();
    }


    public static void goTOAnotherActivityWithBundle(Context c, Class<?> clas, Bundle bundle) {
        Intent i = new Intent(c, clas);
        i.putExtras(bundle);
        c.startActivity(i);

    }


    public static void startActivityForResult(Context context, Class<?> clas,Bundle bundle, int resultCode) {

        Intent i = new Intent(context, clas);
        if(bundle!=null)
            i.putExtras(bundle);
        ((Activity) context).startActivityForResult(i, resultCode);

    }

}
