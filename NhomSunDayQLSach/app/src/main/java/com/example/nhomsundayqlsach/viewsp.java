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
    import android.widget.ListView;
    import android.widget.Toast;

    import com.example.nhomsundayqlsach.adapter.BookAdapter;
    import com.example.nhomsundayqlsach.model.Book;
    import com.example.nhomsundayqlsach.model.BookDAO;

    import java.util.List;

    public class viewsp extends BaseActivity {
        private ListView listViewProducts;
        private List<Book> bookList;
        private BookDAO bookDAO;
        private BookAdapter adapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_viewsp);

            // Gọi hàm setupBackButton để xử lý nút "Quay Lại"
            setupBackButton();

            listViewProducts = findViewById(R.id.listViewProducts);
            bookDAO = new BookDAO(this);
            bookList = bookDAO.getAllBooks();
            Button btnThemSP = findViewById(R.id.btnThemSP);
            adapter = new BookAdapter(this, bookList);
            listViewProducts.setAdapter(adapter);

            // Xử lý sự kiện khi người dùng nhấp vào một sách trong danh sách
            listViewProducts.setOnItemClickListener((parent, view, position, id) -> {
                // Lấy sách được chọn
                Book selectedBook = bookList.get(position);
                // TODO: Xử lý logic khi sách được chọn
            });
            btnThemSP.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Chuyển đến màn hình thêm danh mục sản phẩm
                    Intent intent = new Intent(viewsp.this, themsp.class);
                    startActivity(intent);
                }
            });
            // TODO: Thêm xử lý cho nút xóa và nút sửa
            Button btnDeleteSP = findViewById(R.id.btnDeleteSP);
            btnDeleteSP.setOnClickListener(v -> {
                // Lấy vị trí của sách được chọn
                int selectedPosition = listViewProducts.getCheckedItemPosition();
                if (selectedPosition != ListView.INVALID_POSITION) {
                    // Lấy sách được chọn
                    Book selectedBook = bookList.get(selectedPosition);
                    // Xóa sách từ cơ sở dữ liệu
                    if (bookDAO.deleteBook(selectedBook.getBookID())) {
                        // Xóa thành công, cập nhật lại danh sách và thông báo
                        bookList.remove(selectedBook);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(viewsp.this, "Xóa sách thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(viewsp.this, "Xóa sách thất bại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(viewsp.this, "Vui lòng chọn một sách để xóa", Toast.LENGTH_SHORT).show();
                }
            });

            Button btnChangeSP = findViewById(R.id.btnChangeSP);
            btnChangeSP.setOnClickListener(v -> {
                int selectedPosition = listViewProducts.getCheckedItemPosition();
                if (selectedPosition != ListView.INVALID_POSITION) {
                    Book selectedBook = bookList.get(selectedPosition);
                    Intent intent = new Intent(viewsp.this, sua_sp.class);
                    intent.putExtra("bookID", selectedBook.getBookID());
                    startActivity(intent);
                } else {
                    Toast.makeText(viewsp.this, "Vui lòng chọn một sách để sửa", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
