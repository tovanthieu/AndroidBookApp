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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nhomsundayqlsach.model.Book;
import com.example.nhomsundayqlsach.model.BookDAO;
import com.example.nhomsundayqlsach.model.CategoryDAO;

public class ChiTietSachActivity extends BaseActivity {
    private Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietsach);
        btnBack = findViewById(R.id.btnBack);
        // Gọi hàm setupBackButton để xử lý nút "Quay Lại"
        setupBackButton();

        // Lấy dữ liệu từ Intent
        Intent intent = getIntent();
        int bookID = intent.getIntExtra("BookID", -1);

        BookDAO bookDAO = new BookDAO(this);
        bookDAO.open();
        Book book = bookDAO.getBookByID(bookID);
        bookDAO.close();

        if (book != null) {
            // Hiển thị thông tin lên giao diện
            TextView tvTenTruyen = findViewById(R.id.tvTenTruyen);
            TextView tvDanhMuc = findViewById(R.id.tvDanhMuc);
            TextView tvGia = findViewById(R.id.tvGia);
            TextView tvGioiThieu = findViewById(R.id.tvGioiThieu);
            ImageView imgTruyen = findViewById(R.id.imgTruyen);

            tvTenTruyen.setText(book.getTitle());

            // Sử dụng CategoryDAO để lấy tên danh mục
            CategoryDAO categoryDAO = new CategoryDAO(this);
            categoryDAO.open();
            String categoryName = categoryDAO.getCategoryNameById(book.getCategoryID());
            categoryDAO.close();
            tvDanhMuc.setText("Danh Mục: " + categoryName);

            tvGia.setText("Giá: " + String.valueOf(book.getPrice()));
            tvGioiThieu.setText("Giới Thiệu: " + book.getDescription());

            int resourceId = getResources().getIdentifier(book.getImageName(), "drawable", getPackageName());
            imgTruyen.setImageResource(resourceId);
        }




        Button btnMua = findViewById(R.id.btnMua);
        btnMua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy thông tin sách từ Intent
                Intent intent = getIntent();
                int bookID = intent.getIntExtra("BookID", -1);

                // Lấy thông tin người dùng từ SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                int userID = sharedPreferences.getInt("UserID", -1);

                // Khai báo biến bookDAO một lần
                BookDAO bookDAO = new BookDAO(ChiTietSachActivity.this);

                if (bookID != -1 && userID != -1) {
                    // Lấy giá sách từ cơ sở dữ liệu
                    bookDAO.open();
                    Book book = bookDAO.getBookByID(bookID);
                    if (book != null) {
                        // Thêm thông tin đơn hàng và chi tiết đơn hàng vào SQLite
                        long orderID = bookDAO.addOrder(userID);  // Thêm đơn hàng và lấy OrderID
                        if (orderID != -1) {
                            // Thêm chi tiết đơn hàng với giá của sách
                            double price = book.getPrice();
                            bookDAO.addOrderDetail(orderID, bookID, price);

                            // Hiển thị thông báo mua sách thành công
                            Toast.makeText(ChiTietSachActivity.this, "Mua sách thành công!", Toast.LENGTH_SHORT).show();
                        } else {
                            // Hiển thị thông báo lỗi khi thêm đơn hàng
                            Toast.makeText(ChiTietSachActivity.this, "Có lỗi xảy ra khi mua sách. Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Hiển thị thông báo lỗi khi không tìm thấy sách
                        Toast.makeText(ChiTietSachActivity.this, "Không tìm thấy sách. Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                    }

                    bookDAO.close();
                }
            }
        });










    }
}