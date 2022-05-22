package ru.vsu.hb_front;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.Objects;

import io.reactivex.disposables.Disposable;

import ru.vsu.hb_front.api.Api;
import ru.vsu.hb_front.databinding.FragmentTransactionsBinding;
import ru.vsu.hb_front.databinding.FragmentUserEditBinding;
import ru.vsu.hb_front.dto.UserEditRequest;
import ru.vsu.hb_front.store.PreferenceStore;

public class UserEditFragment extends Fragment {

    private FragmentUserEditBinding b;
    private Disposable userEditDisposable;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        b = FragmentUserEditBinding.inflate(inflater, container, false);

        b.firstnameBtn.setOnClickListener(l -> {
            String firstname = b.firstname.getText().toString();
            if (firstname.length() == 0) {
                b.firstnameInputLayout.setError("Поле не может быть пустым");
            } else {
                UserEditRequest editRequest = new UserEditRequest();
                editRequest.setFirstName(firstname);
                userEditDisposable = Api.getInstance().updateUser(editRequest).subscribe(resp -> {
                    if (resp.isSuccessful()) {
                        PreferenceStore.getInstance().saveName(firstname);
                        Toast.makeText(getContext(), "Имя успешно изменено!", Toast.LENGTH_SHORT).show();
                    } else {
                        b.firstnameInputLayout.setError("Неизвестная ошибка");
                    }
                }, err -> {
                    b.firstnameInputLayout.setError("Неизвестная ошибка");
                });
            }
        });

        b.passwordBtn.setOnClickListener(l -> {
            String password = b.password.getText().toString();
            String passwordRepeat = b.passwordRepeat.getText().toString();
            if (password.length() < 6) {
                b.passwordInputLayout.setError("Пароль должен быть длиннее 6 символов");
            } else if (!Objects.equals(password, passwordRepeat)) {
                b.firstnameInputLayout.setError("Пароли не совпадают");
            } else {
                UserEditRequest editRequest = new UserEditRequest();
                editRequest.setFirstName(PreferenceStore.getInstance().getName());
                editRequest.setPassword(password);
                userEditDisposable = Api.getInstance().updateUser(editRequest).subscribe(resp -> {
                    if (resp.isSuccessful()) {
                        //PreferenceStore.getInstance().saveToken(resp.headers().get("Authorization"));
                        Toast.makeText(getContext(), "Пароль успешно изменён!", Toast.LENGTH_SHORT).show();
                    } else {
                        b.passwordInputLayout.setError("Неизвестная ошибка");
                    }
                }, err -> {
                    b.passwordInputLayout.setError("Неизвестная ошибка");
                });
            }
        });
        return b.getRoot();
    }
}
