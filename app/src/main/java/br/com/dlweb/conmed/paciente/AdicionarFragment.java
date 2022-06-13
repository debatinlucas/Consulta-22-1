package br.com.dlweb.conmed.paciente;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import br.com.dlweb.conmed.R;
import br.com.dlweb.conmed.paciente.conexao.PostHttpService;

public class AdicionarFragment extends Fragment {

    EditText etNome;
    Spinner spGrpSan;
    EditText etLogradouro;
    EditText etNumero;
    EditText etCidade;
    Spinner spUf;
    EditText etCelular;
    EditText etFixo;

    public AdicionarFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.paciente_fragment_adicionar, container, false);

        etNome = v.findViewById(R.id.etNome);
        spGrpSan = v.findViewById(R.id.spGrpSan);
        etLogradouro = v.findViewById(R.id.etLogradouro);
        etNumero = v.findViewById(R.id.etNumero);
        etCidade = v.findViewById(R.id.etCidade);
        spUf = v.findViewById(R.id.spUf);
        etCelular = v.findViewById(R.id.etCelular);
        etFixo = v.findViewById(R.id.etFixo);

        String[]  tipos_sangue = new String[] { "O+", "A+", "B+", "AB+", "O-", "A-", "B-", "AB-" };
        ArrayAdapter<String> spTSArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, tipos_sangue);
        spGrpSan.setAdapter(spTSArrayAdapter);

        String[]  ufs = new String[] { "RS", "SC", "PR" };
        ArrayAdapter<String> spUFArrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, ufs);
        spUf.setAdapter(spUFArrayAdapter);

        Button clickCriar = v.findViewById(R.id.btnCriar);
        clickCriar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                criarWS();
            }
        });

        return v;
    }

    private void criarWS() {
        String nome = etNome.getText().toString().trim();
        String logradouro = etLogradouro.getText().toString().trim();
        String numero = etNumero.getText().toString().trim();
        String cidade = etCidade.getText().toString().trim();
        String celular = etCelular.getText().toString().trim();
        String fixo = etFixo.getText().toString().trim();
        if(nome.equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o nome!", Toast.LENGTH_LONG).show();
        } else if (logradouro.equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o logradouro!", Toast.LENGTH_LONG).show();
        } else if (numero.equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o número!", Toast.LENGTH_LONG).show();
        } else if (cidade.equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe a cidade!", Toast.LENGTH_LONG).show();
        } else if (celular.equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o celular!", Toast.LENGTH_LONG).show();
        } else if (fixo.equals("")) {
            Toast.makeText(getActivity(), "Por favor, informe o fixo!", Toast.LENGTH_LONG).show();
        } else {
            try {
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("nome", nome);
                jsonParam.put("grp_sanguineo", spGrpSan.getSelectedItemPosition());
                jsonParam.put("logradouro", logradouro);
                jsonParam.put("numero", numero);
                jsonParam.put("cidade", cidade);
                jsonParam.put("uf", spUf.getSelectedItem().toString());
                jsonParam.put("celular", celular);
                jsonParam.put("fixo", fixo);
                String retorno = new PostHttpService(jsonParam.toString()).execute().get();
                if (!retorno.equals("false")) {
                    Toast.makeText(getActivity(), "Paciente adicionado!", Toast.LENGTH_LONG).show();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framePaciente, new ListarFragment()).commit();
                }
            } catch (JSONException | InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}