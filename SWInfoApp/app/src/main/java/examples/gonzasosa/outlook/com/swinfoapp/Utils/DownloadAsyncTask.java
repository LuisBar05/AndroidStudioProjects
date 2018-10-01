package examples.gonzasosa.outlook.com.swinfoapp.Utils;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import examples.gonzasosa.outlook.com.swinfoapp.Interfaces.OnDownloadFinishedListener;

/***
 * Tarea asíncrona que nos ayuda en la descarga del texto contenido en la url especificada.
 */
public class DownloadAsyncTask extends AsyncTask<String, Void, String> {
    //referencia al objeto que manejará el evento de finalización de la descarga
    private OnDownloadFinishedListener listener;

    public DownloadAsyncTask (OnDownloadFinishedListener l) {
        listener = l;
    }

    /***
     * método ejecutado en un hilo independiente de la interfaz de usuario, en donde se realiza la descarga de los bytes mediante clases Http*
     *
     * @param urls dirección de internet donde se aloja el recurso solicitado
     * @return texto plano conteniendo la información descargada
     */
    @Override
    protected String doInBackground(String... urls) {
        URL url;
        HttpsURLConnection connection;
        String response = "";
        String uri = urls [0];

        try {
            url = new URL (uri);
            connection = (HttpsURLConnection) url.openConnection ();
            connection.setRequestMethod ("GET");
            connection.connect ();

            if (connection.getResponseCode () == HttpURLConnection.HTTP_OK) {
                InputStreamReader inputStreamReader = new InputStreamReader (connection.getInputStream (), "utf8");
                BufferedReader bufferedReader = new BufferedReader (inputStreamReader);
                StringBuilder stringBuilder = new StringBuilder ();
                String line;

                while ((line = bufferedReader.readLine ()) != null) {
                    stringBuilder.append (line);
                }

                bufferedReader.close ();
                inputStreamReader.close ();

                response = stringBuilder.toString ();
            }

            connection.disconnect ();

        } catch (Exception ex) {
            Log.e (URLS.TAG, ex.getMessage ());
        }

        return response;
    }

    /***
     * Este método se ejecuta una vez que el procesamiento en segundo plano ha finalizado, a diferencia de tal procesamiento,
     * este es invocado en el mismo hilo que la interfaz de usuario.
     *
     * El resultado es notificado al objeto que inició la tarea, mediante la variable listener cuyo tipo es OnDownloadFinishedListener
     *
     * @param s La cadena de texto resultante de la descarga realizada en el método doInBackground
     */
    @Override
    protected void onPostExecute (String s) {
        super.onPostExecute (s);
        listener.finish (s);
    }
}
