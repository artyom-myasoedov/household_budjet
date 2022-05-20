package ru.vsu.hb_front.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import ru.vsu.hb_front.LoginActivity;
import ru.vsu.hb_front.MainActivity;
import ru.vsu.hb_front.store.PreferenceStore;

public class LogoutConfirmDialogFragment extends DialogFragment {
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        return builder
                //.setTitle("Диалоговое окно")
                //.setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("Вы уверены, что хотите выйти из учётной записи?")
                .setPositiveButton("Да", (dialogInterface, i) -> {
                    PreferenceStore.getInstance().saveName(null);
                    PreferenceStore.getInstance().saveToken(null);
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                })
                .setNegativeButton("Отмена", (dialogInterface, i) -> {
                    dismiss();
                })
                .create();

    }
}
