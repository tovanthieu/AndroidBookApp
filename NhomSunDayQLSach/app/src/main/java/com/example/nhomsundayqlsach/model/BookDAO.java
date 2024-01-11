package com.example.nhomsundayqlsach.model;
/*
 * Bản quyền (c) 2024 Tô Văn Thiều
 *
 * Đoạn mã này là sở hữu của Tô Văn Thiều.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.nhomsundayqlsach.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BookDAO {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public BookDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        try {
            database = dbHelper.getWritableDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long addBook(Book book) {
        ContentValues values = new ContentValues();
        values.put("CategoryID", book.getCategoryID());
        values.put("Title", book.getTitle());
        values.put("Price", book.getPrice());
        values.put("Description", book.getDescription());
        values.put("Content", book.getContent());
        values.put("ImageName", book.getImageName());  // Thêm tên ảnh

        return database.insert("Book", null, values);
    }

    public List<Book> getAllBooks() {
        List<Book> bookList = new ArrayList<>();
        String query = "SELECT * FROM Book";

        Cursor cursor = database.rawQuery(query, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Book book = new Book();

                // Kiểm tra xem tên cột có tồn tại trong kết quả truy vấn hay không
                int bookIdIndex = cursor.getColumnIndex("BookID");
                int categoryIdIndex = cursor.getColumnIndex("CategoryID");
                int titleIndex = cursor.getColumnIndex("Title");
                int priceIndex = cursor.getColumnIndex("Price");
                int descriptionIndex = cursor.getColumnIndex("Description");
                int contentIndex = cursor.getColumnIndex("Content");
                int imageNameIndex = cursor.getColumnIndex("ImageName");

                if (bookIdIndex >= 0) {
                    book.setBookID(cursor.getInt(bookIdIndex));
                }

                if (categoryIdIndex >= 0) {
                    book.setCategoryID(cursor.getInt(categoryIdIndex));
                }

                if (titleIndex >= 0) {
                    book.setTitle(cursor.getString(titleIndex));
                }

                if (priceIndex >= 0) {
                    book.setPrice(cursor.getDouble(priceIndex));
                }

                if (descriptionIndex >= 0) {
                    book.setDescription(cursor.getString(descriptionIndex));
                }

                if (contentIndex >= 0) {
                    book.setContent(cursor.getString(contentIndex));
                }

                if (imageNameIndex >= 0) {
                    book.setImageName(cursor.getString(imageNameIndex));
                }

                bookList.add(book);
            }
            cursor.close();
        }

        return bookList;
    }
    public boolean deleteBook(int bookID) {
        return database.delete("Book", "BookID=?", new String[]{String.valueOf(bookID)}) > 0;
    }

    public boolean updateBook(Book book) {
        ContentValues values = new ContentValues();
        values.put("CategoryID", book.getCategoryID());
        values.put("Title", book.getTitle());
        values.put("Price", book.getPrice());
        values.put("Description", book.getDescription());
        values.put("Content", book.getContent());
        values.put("ImageName", book.getImageName());

        return database.update("Book", values, "BookID=?", new String[]{String.valueOf(book.getBookID())}) > 0;
    }
    public Book getBookByID(int bookID) {
        Book book = null;
        String query = "SELECT * FROM Book WHERE BookID = ?";
        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(bookID)});

        if (cursor != null && cursor.moveToFirst()) {
            // Kiểm tra xem tên cột có tồn tại trong kết quả truy vấn hay không
            int bookIdIndex = cursor.getColumnIndex("BookID");
            int categoryIdIndex = cursor.getColumnIndex("CategoryID");
            int titleIndex = cursor.getColumnIndex("Title");
            int priceIndex = cursor.getColumnIndex("Price");
            int descriptionIndex = cursor.getColumnIndex("Description");
            int contentIndex = cursor.getColumnIndex("Content");
            int imageNameIndex = cursor.getColumnIndex("ImageName");

            book = new Book();

            if (bookIdIndex >= 0) {
                book.setBookID(cursor.getInt(bookIdIndex));
            }

            if (categoryIdIndex >= 0) {
                book.setCategoryID(cursor.getInt(categoryIdIndex));
            }

            if (titleIndex >= 0) {
                book.setTitle(cursor.getString(titleIndex));
            }

            if (priceIndex >= 0) {
                book.setPrice(cursor.getDouble(priceIndex));
            }

            if (descriptionIndex >= 0) {
                book.setDescription(cursor.getString(descriptionIndex));
            }

            if (contentIndex >= 0) {
                book.setContent(cursor.getString(contentIndex));
            }

            if (imageNameIndex >= 0) {
                book.setImageName(cursor.getString(imageNameIndex));
            }

            cursor.close();
        }

        return book;
    }

    public List<Book> getBooksByCategory(int categoryId) {
        List<Book> books = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM Book WHERE CategoryID = ?";
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(query, new String[]{String.valueOf(categoryId)});
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    Book book = new Book();
                    // Lấy thông tin từ Cursor và thêm vào danh sách books
                    int bookIdIndex = cursor.getColumnIndex("BookID");
                    int titleIndex = cursor.getColumnIndex("Title");
                    int priceIndex = cursor.getColumnIndex("Price");
                    int descriptionIndex = cursor.getColumnIndex("Description");
                    int contentIndex = cursor.getColumnIndex("Content");
                    int imageNameIndex = cursor.getColumnIndex("ImageName");

                    // Kiểm tra xem các cột có tồn tại trong kết quả truy vấn hay không
                    if (bookIdIndex >= 0) {
                        book.setBookID(cursor.getInt(bookIdIndex));
                    }

                    if (titleIndex >= 0) {
                        book.setTitle(cursor.getString(titleIndex));
                    }

                    if (priceIndex >= 0) {
                        book.setPrice(cursor.getDouble(priceIndex));
                    }

                    if (descriptionIndex >= 0) {
                        book.setDescription(cursor.getString(descriptionIndex));
                    }

                    if (contentIndex >= 0) {
                        book.setContent(cursor.getString(contentIndex));
                    }

                    if (imageNameIndex >= 0) {
                        book.setImageName(cursor.getString(imageNameIndex));
                    }

                    books.add(book);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        db.close();
        return books;
    }

    public String getCurrentDate() {
        // Lấy ngày hiện tại theo định dạng yyyy-MM-dd HH:mm:ss
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }
    public long addOrder(int userID) {
        ContentValues values = new ContentValues();
        values.put("UserID", userID);
        values.put("OrderDate", getCurrentDate());  // Thay getCurrentDate() bằng phương thức lấy ngày hiện tại của bạn
        values.put("Status", "Chưa thanh toán");

        return database.insert("Orders", null, values);
    }

    public long addOrderDetail(long orderID, int bookID, double price) {
        ContentValues values = new ContentValues();
        values.put("OrderID", orderID);
        values.put("BookID", bookID);
        values.put("Price", price);  // Sửa lại tên cột

        return database.insert("OrderDetails", null, values);
    }


}