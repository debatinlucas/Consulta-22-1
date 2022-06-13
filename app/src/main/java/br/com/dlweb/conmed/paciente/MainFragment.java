package br.com.dlweb.conmed.paciente;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import br.com.dlweb.conmed.R;

public class MainFragment extends Fragment {

    public MainFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.paciente_fragment_main, container, false);

        if (savedInstanceState == null) {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framePaciente, new br.com.dlweb.conmed.paciente.ListarFragment()).commit();
        }

        Button btnAdicionar = v.findViewById(R.id.buttonAdicionar);
        Button btnListar = v.findViewById(R.id.buttonListar);

        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framePaciente, new br.com.dlweb.conmed.paciente.ListarFragment()).commit();
            }
        });

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framePaciente, new AdicionarFragment()).commit();
            }
        });


        return v;
    }
}