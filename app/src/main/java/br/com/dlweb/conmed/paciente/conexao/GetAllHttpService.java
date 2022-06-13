package br.com.dlweb.conmed.paciente.conexao;

import android.os.AsyncTask;
import com.google.gson.Gson;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import br.com.dlweb.conmed.paciente.DadosPaciente;

public class GetAllHttpService extends AsyncTask<Void, Void, DadosPaciente[]> {

    private String pagina, numreg;
    public GetAllHttpService(String pg, String nr) {
        this.pagina = pg;
        this.numreg = nr;
    }

    @Override
    protected DadosPaciente[] doInBackground(Void... voids) {
        StringBuilder resposta = new StringBuilder();
        try {
            String s_url = "http://webserver.dlweb.com.br/api-v1/paciente?pagina=" + this.pagina + "&numreg=" + this.numreg;
            URL url = new URL(s_url);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoOutput(false);
            connection.setConnectTimeout(5000);
            connection.connect();

            Scanner scanner = new Scanner(url.openStream());
            while (scanner.hasNext()) {
                resposta.append(scanner.nextLine());
            }
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        return gson.fromJson(resposta.toString(), DadosPaciente[].class);
    }
}