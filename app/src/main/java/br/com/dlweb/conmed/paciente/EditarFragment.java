package br.com.dlweb.conmed.paciente;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import br.com.dlweb.conmed.paciente.conexao.DeleteHttpService;
import br.com.dlweb.conmed.paciente.conexao.GetHttpService;
import br.com.dlweb.conmed.paciente.conexao.PutHttpService;

public class EditarFragment extends Fragment {

    EditText etNome;
    Spinner spGrpSan;
    EditText etLogradouro;
    EditText etNumero;
    EditText etCidade;
    Spinner spUf;
    EditText etCelular;
    EditText etFixo;

    public EditarFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.paciente_fragment_editar, container, false);

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

        Bundle b = getArguments();
        final String id = b.getString("id");
        try {
            DadosPaciente paciente = new GetHttpService(id).execute().get();
            etNome.setText(paciente.getNome());
            etLogradouro.setText(paciente.getLogradouro());
            spGrpSan.setSelection(Integer.parseInt(paciente.getGrp_sanguineo()));
            etLogradouro.setText(paciente.getLogradouro());
            etNumero.setText(paciente.getNumero());
            etCidade.setText(paciente.getCidade());
            int sel = 0 ;
            for (String uf : ufs) {
                if (uf.equals(paciente.getUf())) {
                    break;
                }
                sel ++;
            }
            spUf.setSelection(sel);
            etCelular.setText(paciente.getCelular());
            etFixo.setText(paciente.getFixo());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        Button clickEditar = v.findViewById(R.id.btnEditar);
        clickEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editarWS(id);
            }
        });

        Button clickExcluir = v.findViewById(R.id.btnExcluir);
        clickExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                excluirWS(id);
            }
        });

        return v;
    }

    private void editarWS(String id) {
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
                String retorno = new PutHttpService(id, jsonParam.toString()).execute().get();
                if (!retorno.equals("false")) {
                    Toast.makeText(getActivity(), "Paciente editado!", Toast.LENGTH_LONG).show();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framePaciente, new ListarFragment()).commit();
                }
            } catch (JSONException | InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    private void excluirWS(final String id_paciente) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Deseja excluir o Paciente?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            String retorno = new DeleteHttpService(id_paciente).execute().get();
                            if (retorno.equals("true")) {
                                Toast.makeText(getActivity(), "Paciente excluído", Toast.LENGTH_LONG).show();
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framePaciente, new ListarFragment()).commit();
                            }
                        } catch (ExecutionException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        builder.show();
    }
}