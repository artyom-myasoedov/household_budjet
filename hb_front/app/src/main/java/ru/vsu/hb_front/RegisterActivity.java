package ru.vsu.hb_front;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaCodec;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;

import java.util.Objects;
import java.util.regex.Pattern;

import io.reactivex.disposables.Disposable;
import ru.vsu.hb_front.api.Api;
import ru.vsu.hb_front.databinding.ActivityLoginBinding;
import ru.vsu.hb_front.databinding.ActivityRegisterBindingImpl;
import ru.vsu.hb_front.dto.UserDto;
import ru.vsu.hb_front.dto.UserEditRequest;
import ru.vsu.hb_front.dto.UserLoginRequest;
import ru.vsu.hb_front.store.PreferenceStore;

public class RegisterActivity extends Activity {

    private ActivityRegisterBindingImpl b;
    private Disposable registerDisposable;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,10}$", Pattern.CASE_INSENSITIVE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_register);
        b.registerBtn.setOnClickListener(l -> {
            if (Objects.requireNonNull(b.firstname.getText()).toString().length() == 0) {
                b.firstname.setError("Поле не может быть пустым");
            } else if (Objects.requireNonNull(b.username.getText()).toString().length() == 0) {
                b.username.setError("Поле не может быть пустым");
            } else if (!VALID_EMAIL_ADDRESS_REGEX.matcher(b.username.getText().toString()).matches()) {
                b.username.setError("Неверный формат email");
            } else if (Objects.requireNonNull(b.password.getText()).toString().length() < 6) {
                b.password.setError("Пароль должен быть длиной не менее 6 символов");
            } else if (!b.password.getText().toString().equals(Objects.requireNonNull(b.repeatPassword.getText()).toString())) {
                b.repeatPassword.setError("Пароли не совпадают");
            } else {
                String pass = b.password.getText().toString();
                UserEditRequest req = new UserEditRequest(b.username.getText().toString(), pass, b.firstname.getText().toString());
                registerDisposable = Api.getInstance().register(req).subscribe(
                        resp -> {
                            if (resp.isSuccessful()) {
                                if ("success".equals(resp.body().getCode())) {
                                    registerDisposable = Api.getInstance().login(new UserLoginRequest(resp.body().getData().getUserEmail(), pass)).subscribe(
                                            response -> {
                                                if (response.isSuccessful()) {
                                                    PreferenceStore.getInstance().saveToken(resp.headers().get("Authorization"));
                                                    PreferenceStore.getInstance().saveName(Objects.requireNonNull(resp.body()).getData().getFirstName());
                                                    Intent intent = new Intent(this, MainActivity.class);
                                                    startActivity(intent);
                                                } else {
                                                    Intent intent = new Intent(this, LoginActivity.class);
                                                    startActivity(intent);
                                                }
                                            }
                                    );
                                } else {
                                    b.username.setError(resp.body().getErrorMessage());
                                }
                            } else if (resp.errorBody().string().contains("client_error")) {
                                b.username.setError("Пользователь с таким email уже существует");
                            } else {
                                b.username.setError("Неизвестная ошибка");
                                System.out.println(resp.errorBody().string());
                            }
                        },
                        err -> {
                            b.username.setError("Неизвестная ошибка");
                            err.printStackTrace();
                        });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (registerDisposable != null) registerDisposable.dispose();
    }

}