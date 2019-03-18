package jaapz.com.app_jaapz.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import jaapz.com.app_jaapz.PrincipalActivity;

public class CuadroDialogo extends DialogFragment {
    public static final String TAG = "CuadroDialogo";
    public static final String TAG_MENSAJE = "MensajeDialogo";
    public static final String TAG_TITULO = "TituloDialogo";

    public interface OnDialogListener{
        void OnPositiveButtonClicked();
        void OnNegativeButtonClicked();
    }
    private OnDialogListener onDialogListener;

    private String mensaje;
    private String titulo;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onDialogListener = (OnDialogListener) getActivity();
        mensaje = getArguments().getString(TAG_MENSAJE,"Algo ocurrio mal");
        titulo = getArguments().getString(TAG_TITULO,"Algo ocurrio mal");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(titulo)
                .setMessage(mensaje)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onDialogListener.OnPositiveButtonClicked();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onDialogListener.OnNegativeButtonClicked();
                    }
                });
        builder.setCancelable(true);
        return builder.create();
    }
}
