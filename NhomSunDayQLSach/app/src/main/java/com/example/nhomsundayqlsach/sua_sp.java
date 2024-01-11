package com.example.nhomsundayqlsach;
/*
 * Bản quyền (c) 2024 Tô Văn Thiều
 *
 * Đoạn mã này là sở hữu của Tô Văn Thiều.
 */
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nhomsundayqlsach.model.Book;
import com.example.nhomsundayqlsach.model.BookDAO;

public class sua_sp extends AppCompatActivity {

    private EditText edtProductName;
    private EditText edtProductPrice;
    private EditText edtProductGioiThieu;
    private Spinner spinnerCategories;
    private EditText edtBookContent;
    private EditText edtImageName;

    private Button btnUpProduct;
    private Button btnBack;

    private BookDAO bookDAO;
    private Book selectedBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suasp);

        edtProductName = findViewById(R.id.edtProductName);
        edtProductPrice = findViewById(R.id.edtProductPrice);
        edtProductGioiThieu = findViewById(R.id.edtProductGioiThieu);
        spinnerCategories = findViewById(R.id.spinnerCategories);
        edtBookContent = findViewById(R.id.edtBookContent);
        edtImageName = findViewById(R.id.edtImageName);

        btnUpProduct = findViewById(R.id.btnUpProduct);
        btnBack = findViewById(R.id.btnBack);

        bookDAO = new BookDAO(this);

        // Lấy thông tin sách từ Intent
        Intent intent = getIntent();
        if (intent != null) {
            int bookID = intent.getIntExtra("bookID", -1);
            if (bookID != -1) {
                selectedBook = bookDAO.getBookByID(bookID);
                if (selectedBook != null) {
                    // Hiển thị thông tin sách lên giao diện
                    edtProductName.setText(selectedBook.getTitle());
                    edtProductPrice.setText(String.valueOf(selectedBook.getPrice()));
                    edtProductGioiThieu.setText(selectedBook.getDescription());
                    edtBookContent.setText(selectedBook.getContent());
                    edtImageName.setText(selectedBook.getImageName());
                    // Tiếp tục hiển thị các trường thông tin khác
                }
            }
        }

        // Xử lý sự kiện khi nhấn nút "Sửa sản phẩm"
        btnUpProduct.setOnClickListener(v -> {
            // Lấy thông tin nhập từ người dùng
            String productName = edtProductName.getText().toString().trim();
            double productPrice = Double.parseDouble(edtProductPrice.getText().toString().trim());
            String productGioiThieu = edtProductGioiThieu.getText().toString().trim();
            String bookContent = edtBookContent.getText().toString().trim();
            String imageName = edtImageName.getText().toString().trim();

            // Cập nhật thông tin của sách
            selectedBook.setTitle(productName);
            selectedBook.setPrice(productPrice);
            selectedBook.setDescription(productGioiThieu);
            selectedBook.setContent(bookContent);
            selectedBook.setImageName(imageName);

            // Tiếp tục cập nhật thông tin các trường khác

            // Gọi phương thức updateBook() trong bookDAO
            if (bookDAO.updateBook(selectedBook)) {
                // Cập nhật thành công, hiển thị thông báo và kết thúc activity
                Toast.makeText(sua_sp.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                // Cập nhật thất bại, hiển thị thông báo
                Toast.makeText(sua_sp.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
            }
        });


        // Xử lý sự kiện khi nhấn nút "Quay lại"
        btnBack.setOnClickListener(v -> {
            // Trở lại trang danh sách sản phẩm
            finish();
        });
    }
}
