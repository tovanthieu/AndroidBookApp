package com.example.nhomsundayqlsach;
/*
 * Bản quyền (c) 2024 Tô Văn Thiều
 *
 * Đoạn mã này là sở hữu của Tô Văn Thiều.
 */
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nhomsundayqlsach.adapter.BookDetailsAdapter;
import com.example.nhomsundayqlsach.model.Book;
import com.example.nhomsundayqlsach.model.BookDAO;
import com.example.nhomsundayqlsach.model.CategoryDAO;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Book> productList;
    private BookDetailsAdapter BookDetailsAdapter;
    private List<String> categories;
    private CategoryDAO categoryDAO;
    private EditText editTextSearch;
    private Spinner spinnerCategory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnHome = findViewById(R.id.btnHome);
        Button btnProfile = findViewById(R.id.btnProfile);
        editTextSearch = findViewById(R.id.editTextSearch);
        spinnerCategory = findViewById(R.id.spinnerCategory);

        // Khởi tạo CategoryDAO
        categoryDAO = new CategoryDAO(this);

        // Lấy danh sách danh mục từ CategoryDAO
        categories = categoryDAO.getAllCategoriesWithAllOption();
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categories);
        spinnerCategory.setAdapter(spinnerAdapter);

        // Xử lý sự kiện khi chọn một danh mục từ Spinner
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String selectedCategory = categories.get(position);
                if ("Tất cả".equals(selectedCategory)) {
                    // Hiển thị tất cả sách
                    updateListView(getBooksFromSQLite());
                } else {
                    // Lấy danh sách sản phẩm theo danh mục được chọn
                    int categoryId = categoryDAO.getCategoryIdByName(selectedCategory);
                    List<Book> filteredBooks = getBooksByCategory(categoryId);

                    // Cập nhật ListView với danh sách sản phẩm mới
                    updateListView(filteredBooks);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // Không có hành động khi không có gì được chọn
            }
        });

        productList = getBooksFromSQLite(); // Sử dụng phương thức của BookDAO hoặc lớp tương tự
        BookDetailsAdapter = new BookDetailsAdapter(this, productList);
        ListView listView = findViewById(R.id.DSSach);
        listView.setAdapter(BookDetailsAdapter);




        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // No action needed before text changes
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Filter the list based on the search text
                if (BookDetailsAdapter != null) {
                    BookDetailsAdapter.getFilter().filter(charSequence);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // No action needed after text changes
            }
        });


        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });
    }
    private void updateListView(List<Book> filteredBooks) {
        BookDetailsAdapter.clear();
        BookDetailsAdapter.addAll(filteredBooks);
        BookDetailsAdapter.notifyDataSetChanged();
    }

    // Phương thức để lấy danh sách sản phẩm theo danh mục
    private List<Book> getBooksByCategory(int categoryId) {
        // Sử dụng BookDAO để lấy danh sách sản phẩm theo CategoryID
        List<Book> books = new ArrayList<>();
        BookDAO bookDAO = new BookDAO(this);

        // Mở cơ sở dữ liệu
        bookDAO.open();

        // Lấy danh sách sách từ SQLite
        books = bookDAO.getBooksByCategory(categoryId);

        // Đóng cơ sở dữ liệu
        bookDAO.close();

        return books;
    }

    // Phương thức để lấy danh sách tất cả sách từ SQLite
    private List<Book> getBooksFromSQLite() {
        List<Book> books = new ArrayList<>();
        BookDAO bookDAO = new BookDAO(this);

        // Mở cơ sở dữ liệu
        bookDAO.open();

        // Lấy danh sách sách từ SQLite
        books = bookDAO.getAllBooks();

        // Đóng cơ sở dữ liệu
        bookDAO.close();

        return books;
    }
}