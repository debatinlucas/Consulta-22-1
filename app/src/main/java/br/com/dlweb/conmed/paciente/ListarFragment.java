package br.com.dlweb.conmed.paciente;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.concurrent.ExecutionException;

import br.com.dlweb.conmed.R;
import br.com.dlweb.conmed.RecyclerItemClickListener;
import br.com.dlweb.conmed.paciente.conexao.GetAllHttpService;

public class ListarFragment extends Fragment {

    public ListarFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.paciente_fragment_listar, container, false);

        final RecyclerView rvPacientes = v.findViewById(R.id.rvPacientes);
        rvPacientes.setHasFixedSize(true);
        rvPacientes.setLayoutManager(new LinearLayoutManager(getActivity()));
        try {
            DadosPaciente[] pacientes = new GetAllHttpService("1", "15").execute().get();
            PacienteAdapter adapter = new PacienteAdapter(pacientes);
            rvPacientes.setAdapter(adapter);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        rvPacientes.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), rvPacientes ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        View v = rvPacientes.getChildAt(position);
                        TextView tvListId = v.findViewById(R.id.tvListId);

                        Bundle b = new Bundle();
                        b.putString("id", tvListId.getText().toString());

                        br.com.dlweb.conmed.paciente.EditarFragment editar = new br.com.dlweb.conmed.paciente.EditarFragment();
                        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                        editar.setArguments(b);
                        ft.replace(R.id.framePaciente, editar).commit();
                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                })
        );

        return v;
    }
}