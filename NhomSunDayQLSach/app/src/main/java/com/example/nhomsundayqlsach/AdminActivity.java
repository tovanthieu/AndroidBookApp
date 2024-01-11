package com.example.nhomsundayqlsach;
/*
 * Bản quyền (c) 2024 Tô Văn Thiều
 *
 * Đoạn mã này là sở hữu của Tô Văn Thiều.
 */
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // Gọi hàm setupBackButton để xử lý nút "Quay Lại"
        setupBackButton();

        // Lấy reference đến các nút từ layout
        Button btnAddProduct = findViewById(R.id.btnAddProduct);
        Button btnAddCategory = findViewById(R.id.btnAddCategory);
        Button btnManageOrders = findViewById(R.id.btnManageOrders);
        Button btnLogout = findViewById(R.id.btnLogout);

        // Thiết lập sự kiện onClick cho từng nút

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến màn hình thêm sản phẩm
                Intent intent = new Intent(AdminActivity.this, viewsp.class);
                startActivity(intent);
            }
        });

        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến màn hình thêm danh mục sản phẩm
                Intent intent = new Intent(AdminActivity.this, themdmsp.class);
                startActivity(intent);
            }
        });

        btnManageOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển đến màn hình quản lý đơn hàng
                Intent intent = new Intent(AdminActivity.this, QLDHActivity.class);
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
