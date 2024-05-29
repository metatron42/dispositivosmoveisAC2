package com.example.atividade5ac2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private EditText editTextRa, editTextNome, editTextCep, editTextLogradouro, editTextComplemento, editTextBairro, editTextCidade, editTextUf;
    private Button buttonSave, buttonListAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextRa = findViewById(R.id.editTextRa);
        editTextNome = findViewById(R.id.editTextNome);
        editTextCep = findViewById(R.id.editTextCep);
        editTextLogradouro = findViewById(R.id.editTextLogradouro);
        editTextComplemento = findViewById(R.id.editTextComplemento);
        editTextBairro = findViewById(R.id.editTextBairro);
        editTextCidade = findViewById(R.id.editTextCidade);
        editTextUf = findViewById(R.id.editTextUf);
        buttonSave = findViewById(R.id.buttonSave);
        buttonListAlunos = findViewById(R.id.buttonListAlunos);

        editTextCep.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String cep = editTextCep.getText().toString();
                    if (!TextUtils.isEmpty(cep)) {
                        buscarCep(cep);
                    }
                }
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarAluno();
            }
        });

        buttonListAlunos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListAlunosActivity.class);
                startActivity(intent);
            }
        });
    }

    private void buscarCep(String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    editTextLogradouro.setText(response.getString("logradouro"));
                    editTextComplemento.setText(response.getString("complemento"));
                    editTextBairro.setText(response.getString("bairro"));
                    editTextCidade.setText(response.getString("localidade"));
                    editTextUf.setText(response.getString("uf"));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Erro ao buscar CEP", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Erro ao buscar CEP", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }

    private void salvarAluno() {
        String ra = editTextRa.getText().toString();
        String nome = editTextNome.getText().toString();
        String cep = editTextCep.getText().toString();
        String logradouro = editTextLogradouro.getText().toString();
        String complemento = editTextComplemento.getText().toString();
        String bairro = editTextBairro.getText().toString();
        String cidade = editTextCidade.getText().toString();
        String uf = editTextUf.getText().toString();

        if (TextUtils.isEmpty(ra) || TextUtils.isEmpty(nome) || TextUtils.isEmpty(cep)) {
            Toast.makeText(this, "Por favor, preencha todos os campos obrigatórios.", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "https://64ee8a15219b3e2873c34afc.mockapi.io/api/v1/aluno";

        RequestQueue queue = Volley.newRequestQueue(this);

        JSONObject aluno = new JSONObject();
        try {
            aluno.put("ra", ra);
            aluno.put("nome", nome);
            aluno.put("cep", cep);
            aluno.put("logradouro", logradouro);
            aluno.put("complemento", complemento);
            aluno.put("bairro", bairro);
            aluno.put("cidade", cidade);
            aluno.put("uf", uf);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, aluno, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(MainActivity.this, "Aluno salvo com sucesso!", Toast.LENGTH_SHORT).show();
                limparCampos();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Erro ao salvar aluno", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }

    private void limparCampos() {
        editTextRa.setText("");
        editTextNome.setText("");
        editTextCep.setText("");
        editTextLogradouro.setText("");
        editTextComplemento.setText("");
        editTextBairro.setText("");
        editTextCidade.setText("");
        editTextUf.setText("");
    }
}
