package politikei.com.br.politikei.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import politikei.com.br.politikei.R;

public class FragmentDetailProject extends Fragment {

    public FragmentDetailProject() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.proposed_law_details, container, false);
    }
}
