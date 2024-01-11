package com.example.nhomsundayqlsach.model;
/*
 * Bản quyền (c) 2024 Tô Văn Thiều
 *
 * Đoạn mã này là sở hữu của Tô Văn Thiều.
 */
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.nhomsundayqlsach.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;
    public CategoryDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
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
                    // ... (giữ nguyên phần code để lấy thông tin từ Cursor và thêm vào danh sách books)
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

    public List<String> getAllCategories() {
        List<String> categories = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT CategoryName FROM Category";
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(query, null);
            if (cursor != null) {
                int columnIndex = cursor.getColumnIndex("CategoryName");
                if (columnIndex != -1 && cursor.moveToFirst()) {
                    do {
                        String categoryName = cursor.getString(columnIndex);
                        categories.add(categoryName);
                    } while (cursor.moveToNext());
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
        return categories;
    }
    public List<String> getAllCategoriesWithAllOption() {
        List<String> categories = new ArrayList<>();
        categories.add("Tất cả"); // Thêm mục "Tất cả" vào đầu danh sách
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT CategoryName FROM Category";
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(query, null);
            if (cursor != null) {
                int columnIndex = cursor.getColumnIndex("CategoryName");
                if (columnIndex != -1 && cursor.moveToFirst()) {
                    do {
                        String categoryName = cursor.getString(columnIndex);
                        categories.add(categoryName);
                    } while (cursor.moveToNext());
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
        return categories;
    }

    public int getCategoryIdByName(String categoryName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT CategoryID FROM Category WHERE CategoryName = ?";
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(query, new String[]{categoryName});
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex("CategoryID");
                if (columnIndex != -1) {
                    return cursor.getInt(columnIndex);
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
        return -1; // Trả về -1 nếu không tìm thấy CategoryID
    }
    public boolean deleteCategory(String categoryName) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int affectedRows = db.delete("Category", "CategoryName=?", new String[]{categoryName});
        db.close();
        return affectedRows > 0;
    }

    public String getCategoryNameById(int categoryId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT CategoryName FROM Category WHERE CategoryID = ?";
        Cursor cursor = null;

        try {
            cursor = db.rawQuery(query, new String[]{String.valueOf(categoryId)});
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex("CategoryName");
                if (columnIndex != -1) {
                    return cursor.getString(columnIndex);
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
        return ""; // Trả về chuỗi rỗng nếu không tìm thấy tên danh mục
    }

}