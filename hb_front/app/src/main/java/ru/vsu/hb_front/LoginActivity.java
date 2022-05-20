package ru.vsu.hb_front;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import java.net.ConnectException;
import java.util.Objects;

import io.reactivex.disposables.Disposable;
import ru.vsu.hb_front.api.Api;
import ru.vsu.hb_front.databinding.ActivityLoginBinding;
import ru.vsu.hb_front.dto.UserLoginRequest;
import ru.vsu.hb_front.store.PreferenceStore;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding b;
    private Disposable loginDisposable;
    private static long back_pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = DataBindingUtil.setContentView(this, R.layout.activity_login);

        b.loginBtn.setOnClickListener(v->{
            if(Objects.requireNonNull(b.username.getText()).toString().length()==0){
                b.username.setError("Поле не может быть пустым");
            }else if(Objects.requireNonNull(b.password.getText()).toString().length() == 0){
                b.password.setError("Поле не может быть пустым");
            }else{
                UserLoginRequest ulr = new UserLoginRequest();
                ulr.setPassword(b.password.getText().toString());
                ulr.setUserEmail(b.username.getText().toString());
                loginDisposable = Api.getInstance().login(ulr).subscribe(resp ->{
                    if(resp.isSuccessful()){
                        PreferenceStore.getInstance().saveToken(resp.headers().get("Authorization"));
                        PreferenceStore.getInstance().saveName(Objects.requireNonNull(resp.body()).getData().getFirstName());
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                    }else{
                        if (Objects.requireNonNull(resp.errorBody()).string().contains("forbidden")){
                            b.username.setError("Неверный логин или пароль");
                            b.password.setError("Неверный логин или пароль");
                        }else if(resp.errorBody().string().contains("not_found")){
                            b.username.setError("Пользователь не существует");
                            b.password.setError("Пользователь не существует");
                        }else {
                            b.username.setError("Неизвестная ошибка");
                            b.password.setError("Неизвестная ошибка");
                        }
                    }
                }, err ->{
                    String msg = "Не удалось подключиться к серверу";
                    if (!(err instanceof ConnectException))
                        msg = err.getMessage();
                    b.username.setError(msg);
                    b.password.setError(msg);
                    err.printStackTrace();
                });
            }
        });

        b.registrationBtn.setOnClickListener(v->{
            startActivity(new Intent(this, RegisterActivity.class));
            //Fragment fragment = new RegisterActivity();
//            getSupportFragmentManager().beginTransaction().addToBackStack("register")
//                    .replace(R.id.fragment, fragment, "register").commit();
        });

        b.statisticsBtn.setOnClickListener(v->{
            startActivity(new Intent(this, UnauthorizedActivity.class));
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(loginDisposable!=null) loginDisposable.dispose();
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            finishAffinity();
        } else {
            Toast.makeText(getBaseContext(), "Нажмите ещё раз для выхода!", Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
    }
}