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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nhomsundayqlsach.adapter.PurchaseHistoryAdapter;
import com.example.nhomsundayqlsach.model.Order;
import com.example.nhomsundayqlsach.model.OrderDAO;

import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    private List<Order> purchaseHistory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Button btnLogout = findViewById(R.id.btnLogout);
        Button btnHome = findViewById(R.id.btnHome);
        Button btnView = findViewById(R.id.btnView);
        // Trong ProfileActivity, trong phương thức onCreate
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        int userID = sharedPreferences.getInt("UserID", -1); // -1 là giá trị mặc định nếu không tìm thấy
        String username = sharedPreferences.getString("Username", "Không có tên");

        // Hiển thị thông tin người dùng
        TextView tvUsername = findViewById(R.id.tvUsername);
        tvUsername.setText(username);

        // Log the username after it's initialized
        Log.d("ProfileActivity", "Username: " + username);
        // Hiển thị lịch sử mua hàng
        displayPurchaseHistory(userID);
        // Làm các bước tương tự để hiển thị lịch sử mua hàng

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xóa thông tin người dùng từ SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                // Chuyển hướng đến màn hình đăng nhập
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
                finish(); // Đóng ProfileActivity
            }
        });
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the selected orderID from the intent
                int orderID = getIntent().getIntExtra("orderID", -1);

                if (orderID != -1) {
                    // Create an intent to start the DocsachActivity
                    Intent intent = new Intent(ProfileActivity.this, docsach.class);

                    // Pass relevant information to the DocsachActivity
                    intent.putExtra("orderID", orderID);

                    startActivity(intent);
                } else {
                    // Notify the user that no order is selected
                    // For example, you can show a Toast or display a message in the UI.
                    Toast.makeText(ProfileActivity.this, "Bạn chưa chọn đơn hàng để xem.", Toast.LENGTH_SHORT).show();
                }
            }
        });





    }
    private void displayPurchaseHistory(int userId) {
        // Truy cập vào cơ sở dữ liệu để lấy lịch sử mua hàng của người dùng
        OrderDAO orderDAO = new OrderDAO(this);
        orderDAO.open(); // Mở cơ sở dữ liệu

        try {
            // Lấy lịch sử mua hàng từ cơ sở dữ liệu cho userId
            purchaseHistory = orderDAO.getPurchaseHistory(userId);

            // Hiển thị lịch sử mua hàng trong ListView
            ListView listViewPurchaseHistory = findViewById(R.id.listViewPurchaseHistory);
            PurchaseHistoryAdapter adapter = new PurchaseHistoryAdapter(this, purchaseHistory);
            listViewPurchaseHistory.setAdapter(adapter);
        } finally {
            // Đảm bảo rằng cơ sở dữ liệu được đóng ngay cả khi có ngoại lệ xảy ra
            orderDAO.close();
        }
    }



}