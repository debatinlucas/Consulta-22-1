package br.com.dlweb.conmed.paciente.conexao;

import android.os.AsyncTask;
import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class PutHttpService extends AsyncTask<Void, Void, String> {

    private String id, json;
    public PutHttpService(String id, String json) {
        this.id = id;
        this.json = json;
    }

    @Override
    protected String doInBackground(Void... voids) {
        String jsonDeResposta = "false";
        try {
            String s_url = "http://webserver.dlweb.com.br/api-v1/paciente?id=" + this.id;
            URL url = new URL(s_url);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(true);
            connection.setDoInput(true);
            PrintStream printStream = new PrintStream(connection.getOutputStream());
            printStream.println(json);
            connection.setConnectTimeout(5000);
            connection.connect();

            jsonDeResposta = new Scanner(connection.getInputStream()).next();
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonDeResposta;
    }
}