package politikei.com.br.politikei;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class FragmentProjetosLei extends ListFragment  {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String ARG_ITEM_ID = "item_id";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private List<ProjetoLei> listProjetoLei;
    private AdapterListProjetoLei adapterListProjetoLei;
    private Callbacks mCallbacks;


    public static FragmentProjetosLei newInstance(String param1, String param2) {
        FragmentProjetosLei fragment = new FragmentProjetosLei();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentProjetosLei() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        this.listProjetoLei = new ArrayList<ProjetoLei>();
        ProjetoLei projetoLei = new ProjetoLei();
        projetoLei.setId(1L);
        projetoLei.setStatus("Aguardando aprovação");
        projetoLei.setTitulo("Projeto1");
        projetoLei.setDescricao("\nauhuhauhau au uah ai ai au ai ai au ahiudhfiua iuaf\n" +
                "auhuhauhau au uah ai ai au ai ai au ahiudhfiua iuaf\n" +
                "auhuhauhau au uah ai ai au ai ai au ahiudhfiua iuaf\n" +
                "auhuhauhau au uah ai ai au ai ai au ahiudhfiua iuaf ");

        ProjetoLei projetoLei2 = new ProjetoLei();
        projetoLei2.setId(2L);
        projetoLei2.setStatus("Aguardando aprovação");
        projetoLei2.setTitulo("Projeto2");
        projetoLei2.setDescricao("\nauhuhauhau au uah ai ai au ai ai au ahiudhfiua iuaf\n" +
                "auhuhauhau au uah ai ai au ai ai au ahiudhfiua iuaf\n" +
                "auhuhauhau au uah ai ai au ai ai au ahiudhfiua iuaf\n" +
                "auhuhauhau au uah ai ai au ai ai au ahiudhfiua iuaf ");

        ProjetoLei projetoLei3 = new ProjetoLei();
        projetoLei3.setId(3L);
        projetoLei3.setStatus("Aguardando aprovação");
        projetoLei3.setTitulo("Projeto3");
        projetoLei3.setDescricao("\nauhuhauhau au uah ai ai au ai ai au ahiudhfiua iuaf\n" +
                "auhuhauhau au uah ai ai au ai ai au ahiudhfiua iuaf\n" +
                "auhuhauhau au uah ai ai au ai ai au ahiudhfiua iuaf\n" +
                "auhuhauhau au uah ai ai au ai ai au ahiudhfiua iuaf ");


        ProjetoLei projetoLei4 = new ProjetoLei();
        projetoLei4.setId(4L);
        projetoLei4.setStatus("Aguardando aprovação");
        projetoLei4.setTitulo("Projeto4");
        projetoLei4.setDescricao("\nauhuhauhau au uah ai ai au ai ai au ahiudhfiua iuaf\n" +
                "auhuhauhau au uah ai ai au ai ai au ahiudhfiua iuaf\n" +
                "auhuhauhau au uah ai ai au ai ai au ahiudhfiua iuaf\n" +
                "auhuhauhau au uah ai ai au ai ai au ahiudhfiua iuaf ");

        ProjetoLei projetoLei5 = new ProjetoLei();
        projetoLei5.setId(5L);
        projetoLei5.setStatus("Aguardando aprovação");
        projetoLei5.setTitulo("Projeto5");
        projetoLei5.setDescricao("\nauhuhauhau au uah ai ai au ai ai au ahiudhfiua iuaf\n" +
                "auhuhauhau au uah ai ai au ai ai au ahiudhfiua iuaf\n" +
                "auhuhauhau au uah ai ai au ai ai au ahiudhfiua iuaf\n" +
                "auhuhauhau au uah ai ai au ai ai au ahiudhfiua iuaf ");

        this.listProjetoLei.add(projetoLei);
        this.listProjetoLei.add(projetoLei2);
        this.listProjetoLei.add(projetoLei3);
        this.listProjetoLei.add(projetoLei4);
        this.listProjetoLei.add(projetoLei5);

        adapterListProjetoLei = new AdapterListProjetoLei(this.getActivity());
        setListAdapter(adapterListProjetoLei);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_projetolei_list, container, false);
        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(String id);
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);

        // Notify the active callbacks interface (the activity, if the
        // fragment is attached to one) that an item has been selected.
        mCallbacks.onItemSelected("");

        Intent detailIntent = new Intent(getActivity(), ActivityDetailProject.class);
        ProjetoLei projeto = getProjetoLei(id);
        detailIntent.putExtra(Utils.tagProjetoLeiSelecionado, projeto);
        startActivity(detailIntent);
    }

    private ProjetoLei getProjetoLei(long id)
    {
        for(ProjetoLei projeto : listProjetoLei)
        {
            if(projeto.getId() == id)
            {
                return projeto;
            }
        }
        return null;
    }

    private class AdapterListProjetoLei extends BaseAdapter
    {

        // private ImageButton imageViewEnviarOlah;
        // private ImageButton imageViewLock;
        // private ProgressBar progressBarLock;

        public AdapterListProjetoLei(Context context)
        {
            super();
        }

        @Override
        public int getCount()
        {

            if (listProjetoLei != null)
            {
                Log.i(Utils.tag, "adapterListOlah count " + listProjetoLei.size());
                return listProjetoLei.size();
            }
            Log.i(Utils.tag, "adapterListOlah count " + 0);
            return 0;
        }

        @Override
        public Object getItem(int position)
        {
            return listProjetoLei.get(position);
        }

        @Override
        public long getItemId(int position)
        {
            ProjetoLei projetoLei = listProjetoLei.get(position);
            if(projetoLei != null)
            {
                return projetoLei.getId();
            }
            return -1;
        }

        @Override
        public int getViewTypeCount()
        {
            // Two types of views, the normal ImageView and the top row of empty
            // views
            return 1;
        }

        @Override
        public boolean hasStableIds()
        {
            return true;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup container)
        {
            Log.i(Utils.tag, "getView FragmentOlahs ");
            LinearLayout viewProjetoLei;

            if (convertView == null)
            {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                viewProjetoLei = (LinearLayout) inflater.inflate(R.layout.fragment_projeto_lei, container, false);
            }
            else
            {
                viewProjetoLei = (LinearLayout) convertView;
            }

            final ProjetoLei projetoLei = (ProjetoLei) getItem(position);

            TextView textViewProjetoLei = (TextView)viewProjetoLei.findViewById(R.id.textViewDescricaoProjetoLei);
            TextView textViewProjetoLeiTitulo  = (TextView)viewProjetoLei.findViewById(R.id.textViewTituloProjetoLei);
            textViewProjetoLei.setText(projetoLei.getDescricao());
            textViewProjetoLeiTitulo.setText(projetoLei.getTitulo());


            return viewProjetoLei;
        }
    }


}
