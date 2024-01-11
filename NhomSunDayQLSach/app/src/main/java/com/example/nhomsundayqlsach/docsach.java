package com.example.nhomsundayqlsach;
/*
 * Bản quyền (c) 2024 Tô Văn Thiều
 *
 * Đoạn mã này là sở hữu của Tô Văn Thiều.
 */

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.nhomsundayqlsach.model.Book;
import com.example.nhomsundayqlsach.model.OrderDAO;

public class docsach extends BaseActivity {
    private Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_docsach);
        setupBackButton();
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("orderID")) {
            int orderId = intent.getIntExtra("orderID", -1);

            if (orderId != -1) {
                // Gọi OrderDAO để lấy thông tin sách từ đơn hàng
                OrderDAO orderDAO = new OrderDAO(this);
                orderDAO.open();

                // Lấy thông tin sách từ đơn hàng
                Book book = orderDAO.getBookInfoFromOrder(orderId);

                orderDAO.close();

                // Hiển thị thông tin sách trên giao diện người dùng
                displayBookInfo(book);
            }
        }

        // Nhận thông tin đơn hàng từ Intent

        }
    private void displayBookInfo(Book book) {
        // Sử dụng thông tin sách để cập nhật giao diện người dùng
        // Ví dụ:
        TextView titleTextView = findViewById(R.id.tvTenTruyen);
        TextView contentTextView = findViewById(R.id.tvContent);

        if (book != null) {
            titleTextView.setText(book.getTitle());

            // Kiểm tra xem có nội dung sách hay không
            if (book.getContent() != null && !book.getContent().isEmpty()) {
                contentTextView.setText(book.getContent());
                Log.d("docsach", "Content set successfully: " + book.getContent());
            } else {
                contentTextView.setText("Nội dung sách không có sẵn.");
                Log.d("docsach", "No content available.");
            }
        } else {
            titleTextView.setText("Không tìm thấy thông tin sách");
            contentTextView.setText("");
        }
    }


}


