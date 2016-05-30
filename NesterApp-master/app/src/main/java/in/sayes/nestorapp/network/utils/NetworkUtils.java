package in.sayes.nestorapp.network.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

;import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @author Sourav
 */
public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();




    /**
     * Check if network is available or not
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = manager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                return true;
            }
        }
        return false;
    }

    public static JsonObject stringToJsonObj(String response) {
        return (new JsonParser()).parse(response).getAsJsonObject();
    }

    public static String getURLKeyParameters(HashMap<String, String> mKeys) {
        String query = "";
        String format;

        boolean first = true;

        if (mKeys != null && !mKeys.isEmpty()) {

            for (String key : mKeys.keySet()) {
                format = (first ? "?" : "&");
                String param = format + key + "=" + URLEncoder.encode(mKeys.get(key));
                query = query + param;
                first = false;
            }
        }

        return query;
    }
}
