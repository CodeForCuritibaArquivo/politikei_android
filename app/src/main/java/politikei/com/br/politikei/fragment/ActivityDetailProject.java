package politikei.com.br.politikei.fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import politikei.com.br.politikei.R;
import politikei.com.br.politikei.utils.Utils;
import politikei.com.br.politikei.datatype.ProjetoLei;

public class ActivityDetailProject extends AppCompatActivity
{
    private TextView textViewStatusPlValue;
    private TextView textViewDescricaoPl;
    private TextView textViewNomePl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalhes_projeto);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        ProjetoLei projetoLei = (ProjetoLei) getIntent().getSerializableExtra(Utils.tagProjetoLeiSelecionado);

        this.textViewStatusPlValue = (TextView) findViewById(R.id.textViewStatusPlValue);
        this.textViewStatusPlValue.setText(projetoLei.getStatus());

        this.textViewDescricaoPl = (TextView) findViewById(R.id.textViewDescricaoProjetoLei);
        this.textViewDescricaoPl.setText(projetoLei.getDescricao());

        this.textViewNomePl = (TextView) findViewById(R.id.textViewPlName);
        this.textViewNomePl.setText(projetoLei.getTitulo());

    }
}
