package com.android.settings;

import android.content.Intent;
import android.app.Fragment;
import com.android.settings.NetworksMain;
import android.os.Bundle;

public class ResultFragment extends Fragment {
    @Override
    public void onCreate (Bundle savedInstanceState) {
      Intent intent = new Intent(getActivity(), NetworksMain.class);
      startActivity(intent);
      getActivity().getFragmentManager().beginTransaction().remove(this).commit();
      super.onCreate(savedInstanceState);
    }
}
	   