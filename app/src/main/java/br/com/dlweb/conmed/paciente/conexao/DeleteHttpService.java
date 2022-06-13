package br.com.dlweb.conmed.paciente.conexao;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class DeleteHttpService extends AsyncTask<Void, Void, String> {

    private String id;
    public DeleteHttpService(String id) {
        this.id = id;
    }

    @Override
    protected String doInBackground(Void... voids) {
        String jsonDeResposta = "false";
        try {
            String s_url = "http://webserver.dlweb.com.br/api-v1/paciente?id=" + this.id;
            URL url = new URL(s_url);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Content-type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(false);
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