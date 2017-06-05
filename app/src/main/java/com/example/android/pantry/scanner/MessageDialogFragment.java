package com.example.android.pantry.scanner;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android.pantry.R;
import com.example.android.pantry.dataStore.LocationsTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dewong4 on 5/13/17.
 */

public class MessageDialogFragment extends DialogFragment implements AdapterView.OnItemSelectedListener {
    public interface MessageDialogListener {
        public void onDialogAddClick(DialogFragment dialog);
        public void onDialogRemoveClick(DialogFragment dialog);
        public void onDialogCancelClick(DialogFragment dialog);
    }

    private String mTitle;
    private String mMessage;
    private String[] mLocations;
    private MessageDialogListener mListener;

    public void onCreate(Bundle state) {
        super.onCreate(state);
        setRetainInstance(true);
    }

    public static MessageDialogFragment newInstance(String title, String message, String[] locations,
                                                    MessageDialogListener listener) {
        MessageDialogFragment fragment = new MessageDialogFragment();
        fragment.mTitle = title;
        fragment.mMessage = message;
        fragment.mLocations = locations;
        fragment.mListener = listener;
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        /*
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View addInventoryView = inflater.inflate(R.layout.add_inventory, null);
        TextView messageTextView = (TextView) addInventoryView.findViewById(R.id.tv_message);
        messageTextView.setText(mMessage);

        Spinner locationsSpinner = (Spinner) addInventoryView.findViewById(R.id.locations_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_spinner_item, mLocations);
        locationsSpinner.setAdapter(adapter);
        */

        builder.setTitle(mTitle)
                .setMessage(mMessage);
        //        .setView(addInventoryView);

        builder.setPositiveButton(R.string.add_button, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                if(mListener != null) {
                    mListener.onDialogAddClick(MessageDialogFragment.this);
                }
            }
        });

        builder.setNeutralButton(R.string.cancel_button, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                if(mListener != null) {
                    mListener.onDialogCancelClick(MessageDialogFragment.this);
                }
            }
        });

        builder.setNegativeButton(R.string.remove_button, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                if(mListener != null) {
                    mListener.onDialogRemoveClick(MessageDialogFragment.this);
                }
            }
        });

        return builder.create();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
