
package com.example.atividade5ac2;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListAlunosActivity extends AppCompatActivity {

    private RecyclerView recyclerViewAlunos;
    private AlunoAdapter alunoAdapter;
    private List<Aluno> alunoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_alunos);

        recyclerViewAlunos = findViewById(R.id.recyclerViewAlunos);
        recyclerViewAlunos.setLayoutManager(new LinearLayoutManager(this));

        alunoAdapter = new AlunoAdapter(alunoList);
        recyclerViewAlunos.setAdapter(alunoAdapter);

        carregarAlunos();
    }

    private void carregarAlunos() {
        String url = "https://64ee8a15219b3e2873c34afc.mockapi.io/api/v1/aluno";

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    alunoList.clear();
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        Aluno aluno = new Aluno();
                        aluno.setRa(jsonObject.getInt("ra"));
                        aluno.setNome(jsonObject.getString("nome"));
                        aluno.setCep(jsonObject.getString("cep"));
                        aluno.setLogradouro(jsonObject.getString("logradouro"));
                        aluno.setComplemento(jsonObject.getString("complemento"));
                        aluno.setBairro(jsonObject.getString("bairro"));
                        aluno.setCidade(jsonObject.getString("cidade"));
                        aluno.setUf(jsonObject.getString("uf"));
                        alunoList.add(aluno);
                    }
                    alunoAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error
            }
        });

        queue.add(request);
    }
}
