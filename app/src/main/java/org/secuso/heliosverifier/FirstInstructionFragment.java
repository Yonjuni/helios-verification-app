package org.secuso.heliosverifier;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class FirstInstructionFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_first, container, false);

        Button buttonScan = (Button) rootView.findViewById(R.id.buttonScan);

        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanFromFragment();
            }
        });

        return rootView;
    }

    public void scanFromFragment() {
        IntentIntegrator.forSupportFragment(this).initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(getActivity(), getString(R.string.first_empty_qr_code), Toast.LENGTH_LONG).show();
            } else {
                if (result.getContents().length() > 53) {
                    Toast.makeText(getActivity(), getString(R.string.first_wrong_qr_code), Toast.LENGTH_LONG).show();
                    final TextView wrong_qr = (TextView) getActivity().findViewById(R.id.wrong_qr);
                    wrong_qr.setVisibility(View.VISIBLE);
                } else {
                    openNewFragment();
                }
            }
        }
    }

    public void openNewFragment() {
        SecondInstructionFragment secondInstructionFragment = new SecondInstructionFragment();
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_main, secondInstructionFragment, "SecondInstructionFragment")
                .addToBackStack(null)
                .commit();
    }
}
