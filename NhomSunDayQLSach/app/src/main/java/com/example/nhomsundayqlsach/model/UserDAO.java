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
import android.util.Log;

import com.example.nhomsundayqlsach.DatabaseHelper;

public class UserDAO {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public UserDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long addUser(User user) {
        ContentValues values = new ContentValues();
        values.put("Username", user.getUsername());
        values.put("Password", user.getPassword());
        values.put("RoleID", user.getRoleID());

        return database.insert("Users", null, values);
    }

    public User getUser(String username, String password) {
        String[] columns = {"UserID", "Username", "RoleID"};
        String selection = "Username = ? AND Password = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = database.query("Users", columns, selection, selectionArgs, null, null, null);

        User user = null;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int userIDIndex = cursor.getColumnIndex("UserID");
                int usernameIndex = cursor.getColumnIndex("Username");
                int roleIDIndex = cursor.getColumnIndex("RoleID");

                // Kiểm tra xem các cột có tồn tại trong kết quả trả về hay không
                if (userIDIndex != -1 && usernameIndex != -1 && roleIDIndex != -1) {
                    user = new User();
                    user.setUserID(cursor.getInt(userIDIndex));
                    user.setUsername(cursor.getString(usernameIndex));
                    user.setRoleID(cursor.getInt(roleIDIndex));

                    // Log giá trị Username tại đây
                    Log.d("UserDAO", "User's Username: " + user.getUsername());
                }
            }
            cursor.close();
        }

        return user;
    }
    public User getUserByID(int userID) {
        User user = null;
        String query = "SELECT * FROM Users WHERE UserID = ?";
        Cursor cursor = null;

        try {
            cursor = database.rawQuery(query, new String[]{String.valueOf(userID)});

            if (cursor != null && cursor.moveToFirst()) {
                int userIdIndex = cursor.getColumnIndex("UserID");
                int usernameIndex = cursor.getColumnIndex("Username");

                user = new User();

                if (userIdIndex >= 0) {
                    user.setUserID(cursor.getInt(userIdIndex));
                }

                if (usernameIndex >= 0) {
                    user.setUsername(cursor.getString(usernameIndex));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return user;
    }


}