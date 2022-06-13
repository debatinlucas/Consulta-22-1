package br.com.dlweb.conmed.paciente;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import br.com.dlweb.conmed.R;

public class PacienteAdapter extends RecyclerView.Adapter<PacienteAdapter.PacienteViewHolder>{

    private DadosPaciente[] pacientes;

    PacienteAdapter(DadosPaciente[] pacientes){
        this.pacientes = pacientes;
    }

    static class PacienteViewHolder extends RecyclerView.ViewHolder {
        TextView idView;
        TextView nomeView;
        TextView cidadeUfView;

        PacienteViewHolder(View itemView) {
            super(itemView);
            idView = itemView.findViewById(R.id.tvListId);
            nomeView = itemView.findViewById(R.id.tvListNome);
            cidadeUfView = itemView.findViewById(R.id.tvListCidadeUf);
        }
    }

    @NonNull
    @Override
    public PacienteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.paciente_item_list_view, parent, false);
        return new PacienteViewHolder(v);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(PacienteViewHolder viewHolder, int i) {
        viewHolder.idView.setText(pacientes[i].getId());
        viewHolder.nomeView.setText(pacientes[i].getNome());
        viewHolder.cidadeUfView.setText(pacientes[i].getCidade() + " - " + pacientes[i].getUf());
    }
    @Override
    public int getItemCount() {
        return pacientes.length;
    }
}