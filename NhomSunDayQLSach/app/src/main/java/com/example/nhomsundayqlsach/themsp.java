package com.example.nhomsundayqlsach;
/*
 * Bản quyền (c) 2024 Tô Văn Thiều
 *
 * Đoạn mã này là sở hữu của Tô Văn Thiều.
 */
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nhomsundayqlsach.model.Book;
import com.example.nhomsundayqlsach.model.BookDAO;
import com.example.nhomsundayqlsach.model.CategoryDAO;

import java.util.List;

public class themsp extends BaseActivity {
    private EditText edtImageName, edtProductName, edtProductPrice, edtProductGioiThieu, edtBookContent;
    private Button btnAddProduct, btnBack;

    private Book newBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themsp);

        // Gọi hàm setupBackButton để xử lý nút "Quay Lại"
        setupBackButton();

        edtProductName = findViewById(R.id.edtProductName);
        edtProductPrice = findViewById(R.id.edtProductPrice);
        edtProductGioiThieu = findViewById(R.id.edtProductGioiThieu);
        edtBookContent = findViewById(R.id.edtBookContent);
        edtImageName = findViewById(R.id.edtImageName);

        btnAddProduct = findViewById(R.id.btnAddProduct);
        btnBack = findViewById(R.id.btnBack);

        Spinner spinnerCategories = findViewById(R.id.spinnerCategories);

        //them danh muc vao spinner
        // Trong onCreate của Activity
        CategoryDAO categoryDAO = new CategoryDAO(this);
        List<String> categories = categoryDAO.getAllCategories();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategories.setAdapter(adapter);




        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String productName = edtProductName.getText().toString();
                double productPrice = Double.parseDouble(edtProductPrice.getText().toString());
                String productGioiThieu = edtProductGioiThieu.getText().toString();
                String bookContent = edtBookContent.getText().toString();
                String imageName = edtImageName.getText().toString();


                String selectedCategory = spinnerCategories.getSelectedItem().toString();
                int categoryID = categoryDAO.getCategoryIdByName(selectedCategory);


                if (categoryID == -1) {
                    Toast.makeText(themsp.this, "Lỗi: Không tìm thấy CategoryID cho danh mục đã chọn", Toast.LENGTH_SHORT).show();
                    return;
                }


                newBook = new Book();
                newBook.setCategoryID(categoryID);
                newBook.setTitle(productName);
                newBook.setPrice(productPrice);
                newBook.setDescription(productGioiThieu);
                newBook.setContent(bookContent);
                newBook.setImageName(imageName);

                BookDAO bookDAO = new BookDAO(themsp.this);
                long result = bookDAO.addBook(newBook);
                if (result != -1) {
                    Toast.makeText(themsp.this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(themsp.this, "Thêm sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }









}

