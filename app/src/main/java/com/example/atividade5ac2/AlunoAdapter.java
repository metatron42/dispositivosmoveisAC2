package com.example.atividade5ac2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AlunoAdapter extends RecyclerView.Adapter<AlunoAdapter.AlunoViewHolder> {

    private List<Aluno> alunoList;

    public AlunoAdapter(List<Aluno> alunoList) {
        this.alunoList = alunoList;
    }

    @NonNull
    @Override
    public AlunoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_aluno, parent, false);
        return new AlunoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlunoViewHolder holder, int position) {
        Aluno aluno = alunoList.get(position);
        holder.textViewRa.setText(String.valueOf(aluno.getRa()));
        holder.textViewNome.setText(aluno.getNome());
    }

    @Override
    public int getItemCount() {
        return alunoList.size();
    }

    static class AlunoViewHolder extends RecyclerView.ViewHolder {
        TextView textViewRa;
        TextView textViewNome;

        AlunoViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewRa = itemView.findViewById(R.id.textViewRa);
            textViewNome = itemView.findViewById(R.id.textViewNome);
        }
    }
}
