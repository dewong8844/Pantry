package com.example.android.pantry.scanner;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.example.android.pantry.R;

/**
 * Created by dewong4 on 5/13/17.
 */

public class MessageDialogFragment extends DialogFragment {
    public interface MessageDialogListener {
        public void onDialogAddClick(DialogFragment dialog);
        public void onDialogRemoveClick(DialogFragment dialog);
        public void onDialogCancelClick(DialogFragment dialog);
    }

    private String mTitle;
    private String mMessage;
    private MessageDialogListener mListener;

    public void onCreate(Bundle state) {
        super.onCreate(state);
        setRetainInstance(true);
    }

    public static MessageDialogFragment newInstance(String title, String message, MessageDialogListener listener) {
        MessageDialogFragment fragment = new MessageDialogFragment();
        fragment.mTitle = title;
        fragment.mMessage = message;
        fragment.mListener = listener;
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(mMessage)
                .setTitle(mTitle);

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

}
