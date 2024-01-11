package com.example.nhomsundayqlsach;
/*
 * Bản quyền (c) 2024 Tô Văn Thiều
 *
 * Đoạn mã này là sở hữu của Tô Văn Thiều.
 */
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nhomsundayqlsach.model.User;
import com.example.nhomsundayqlsach.model.UserDAO;

public class LoginActivity extends AppCompatActivity {
    private EditText edtUsername, edtPassword;
    private Button btnLogin;

    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btnDangKy = findViewById(R.id.btnDangKy);
        edtUsername = findViewById(R.id.edtTaiKhoan);
        edtPassword = findViewById(R.id.edtMatKhau);
        btnLogin = findViewById(R.id.btnDangNhap);

        userDAO = new UserDAO(this);
        userDAO.open();

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();

                // Kiểm tra thông tin hợp lệ trước khi kiểm tra đăng nhập
                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
                    // Kiểm tra đăng nhập
                    User user = userDAO.getUser(username, password);

                    if (user != null) {
                        // Trong LoginActivity, sau khi đăng nhập thành công
                        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                        Log.d("LoginActivity", "UserID: " + user.getUserID());
                        Log.d("LoginActivity", "Username: " + user.getUsername());
                        Log.d("LoginActivity", "Username just retrieved from SharedPreferences: " + sharedPreferences.getString("Username", ""));
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("UserID", user.getUserID());
                        editor.putString("Username", user.getUsername());
                        editor.apply();

                        // Đăng nhập thành công
                        if (user.getRoleID() == 1) {
                            // Nếu là admin, chuyển hướng sang AdminActivity
                            Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                            startActivity(intent);
                        } else {
                            // Nếu là user, chuyển hướng sang MainActivity
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    } else {
                        // Đăng nhập thất bại
                        Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không chính xác!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userDAO.close();
    }
}
