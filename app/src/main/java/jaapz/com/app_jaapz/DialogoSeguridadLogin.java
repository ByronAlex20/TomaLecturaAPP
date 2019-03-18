package jaapz.com.app_jaapz;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DialogoSeguridadLogin{
    public interface FinalizoCuadroDialogo{
        void resultadoCuadroDialogo(Boolean sesionIniciada);
    }

    private FinalizoCuadroDialogo interfaz;

    public DialogoSeguridadLogin(final Context context, FinalizoCuadroDialogo actividad){
        interfaz = actividad;

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.view_sesion_seguridad);

        final EditText et_usuario = (EditText)dialog.findViewById(R.id.et_usuario);
        final EditText et_clave = (EditText)dialog.findViewById(R.id.et_clave);
        Button btn_ingresar = (Button)dialog.findViewById(R.id.btn_ingresar);
        Button btn_cancelar = (Button)dialog.findViewById(R.id.btn_cancelar);

        btn_ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean bandera = false;
                if(et_usuario.getText().toString().equals("admin") && et_clave.getText().toString().equals("admin")) {
                    bandera = true;
                    interfaz.resultadoCuadroDialogo(bandera);
                    dialog.dismiss();
                }else
                    Toast.makeText(context, "Usuario o clave incorrecto!!!", Toast.LENGTH_SHORT).show();
            }
        });
        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaz.resultadoCuadroDialogo(false);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
