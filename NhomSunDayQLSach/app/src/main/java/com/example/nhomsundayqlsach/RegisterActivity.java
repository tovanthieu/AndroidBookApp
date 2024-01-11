package com.example.nhomsundayqlsach;
/*
 * Bản quyền (c) 2024 Tô Văn Thiều
 *
 * Đoạn mã này là sở hữu của Tô Văn Thiều.
 */
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nhomsundayqlsach.model.User;
import com.example.nhomsundayqlsach.model.UserDAO;

public class RegisterActivity extends AppCompatActivity {
    private EditText edtUsername, edtPassword ,nhaplaimatkhau;
    private Button btnRegister;

    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtUsername = findViewById(R.id.edtTaiKhoan);
        edtPassword = findViewById(R.id.edtMatKhau);
        nhaplaimatkhau = findViewById(R.id.nhaplaimatkhau);;

        btnRegister = findViewById(R.id.btnDangKy);
        Button btnDangNhap = findViewById(R.id.btnDangNhap);
        userDAO = new UserDAO(this);
        userDAO.open();

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                String confirmPassword = nhaplaimatkhau.getText().toString();

                // Kiểm tra thông tin hợp lệ trước khi thêm vào cơ sở dữ liệu
                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(confirmPassword)) {
                    // Kiểm tra xem mật khẩu và nhập lại mật khẩu có giống nhau không
                    if (password.equals(confirmPassword)) {
                        // Thêm người dùng vào cơ sở dữ liệu
                        User newUser = new User();
                        newUser.setUsername(username);
                        newUser.setPassword(password);
                        newUser.setRoleID(2); // Mặc định là user

                        long result = userDAO.addUser(newUser);

                        if (result != -1) {
                            // Đăng ký thành công, có thể chuyển hướng hoặc thực hiện các tác vụ khác
                            Toast.makeText(RegisterActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Tài khoản đã được đăng ký!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Mật khẩu và nhập lại mật khẩu không khớp
                        Toast.makeText(RegisterActivity.this, "Mật khẩu và nhập lại mật khẩu không khớp!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
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
