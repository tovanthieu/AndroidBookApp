package com.example.nhomsundayqlsach.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.nhomsundayqlsach.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public OrderDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        try {
            database = dbHelper.getWritableDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Order> getAllOrders() {
        List<Order> orderList = new ArrayList<>();
        String query = "SELECT * FROM Orders";

        Cursor cursor = database.rawQuery(query, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Order order = new Order();

                int orderIdIndex = cursor.getColumnIndex("OrderID");
                int userIdIndex = cursor.getColumnIndex("UserID");
                int orderDateIndex = cursor.getColumnIndex("OrderDate");
                int statusIndex = cursor.getColumnIndex("Status");

                if (orderIdIndex >= 0) {
                    order.setOrderID(cursor.getInt(orderIdIndex));
                }

                if (userIdIndex >= 0) {
                    order.setUserID(cursor.getInt(userIdIndex));
                }

                if (orderDateIndex >= 0) {
                    order.setOrderDate(cursor.getString(orderDateIndex));
                }

                if (statusIndex >= 0) {
                    order.setStatus(cursor.getString(statusIndex));
                }

                orderList.add(order);
            }
            cursor.close();
        }

        return orderList;
    }


    public List<OrderDetails> getOrderDetailsByOrderId(int orderId) {
        List<OrderDetails> orderDetails = new ArrayList<>();
        String query = "SELECT * FROM OrderDetails WHERE OrderID = ?";

        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(orderId)});

        if (cursor != null) {
            while (cursor.moveToNext()) {
                OrderDetails orderDetail = new OrderDetails();

                int orderDetailIdIndex = cursor.getColumnIndex("OrderDetailID");
                int bookIdIndex = cursor.getColumnIndex("BookID");
                int quantityIndex = cursor.getColumnIndex("Quantity");

                if (orderDetailIdIndex >= 0) {
                    orderDetail.setOrderDetailID(cursor.getInt(orderDetailIdIndex));
                }

                if (bookIdIndex >= 0) {
                    orderDetail.setBookID(cursor.getInt(bookIdIndex));
                }

                if (quantityIndex >= 0) {
                    orderDetail.setQuantity(cursor.getInt(quantityIndex));
                }

                orderDetails.add(orderDetail);
            }
            cursor.close();
        }

        return orderDetails;
    }

    public String getBookNameById(int bookId) {
        String bookName = "";
        String query = "SELECT Title FROM Book WHERE BookID = ?";

        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(bookId)});

        if (cursor != null && cursor.moveToFirst()) {
            int titleIndex = cursor.getColumnIndex("Title");
            if (titleIndex >= 0) {
                bookName = cursor.getString(titleIndex);
            }
            cursor.close();
        }

        return bookName;
    }

    public double getPriceByBookId(int bookId) {
        double price = 0.0;
        String query = "SELECT Price FROM Book WHERE BookID = ?";

        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(bookId)});

        if (cursor != null && cursor.moveToFirst()) {
            int priceIndex = cursor.getColumnIndex("Price");
            if (priceIndex >= 0) {
                price = cursor.getDouble(priceIndex);
            }
            cursor.close();
        }

        return price;
    }

    // Các phương thức thêm, xóa, cập nhật đơn hàng...
    public boolean updateOrderStatus(int orderId, String newStatus) {
        ContentValues values = new ContentValues();
        values.put("Status", newStatus);

        String whereClause = "OrderID = ?";
        String[] whereArgs = {String.valueOf(orderId)};

        return database.update("Orders", values, whereClause, whereArgs) > 0;
    }

    public void updateOrderList(List<Order> orderList) {
        orderList.clear();
        orderList.addAll(getAllOrders());
    }

    public List<Order> getPurchaseHistory(int userID) {
        List<Order> purchaseHistory = new ArrayList<>();
        String query = "SELECT Orders.OrderID, Orders.OrderDate, Orders.Status, OrderDetails.BookID " +
                "FROM Orders " +
                "INNER JOIN OrderDetails ON Orders.OrderID = OrderDetails.OrderID " +
                "WHERE Orders.UserID = ? AND Orders.Status = 'Đã thanh toán'";



        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(userID)});

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Order order = new Order();

                int orderIDIndex = cursor.getColumnIndex("OrderID");
                int orderDateIndex = cursor.getColumnIndex("OrderDate");
                int statusIndex = cursor.getColumnIndex("Status");

                if (orderIDIndex >= 0) {
                    order.setOrderID(cursor.getInt(orderIDIndex));
                }

                if (orderDateIndex >= 0) {
                    order.setOrderDate(cursor.getString(orderDateIndex));
                }

                if (statusIndex >= 0) {
                    order.setStatus(cursor.getString(statusIndex));
                }

                // Lấy thông tin sản phẩm từ Book
                int bookIdIndex = cursor.getColumnIndex("BookID");
                if (bookIdIndex >= 0) {
                    int bookID = cursor.getInt(bookIdIndex);
                    Book book = getBookById(bookID);
                    order.setProductName(book.getTitle());
                    order.setProductImageName(book.getImageName());
                }

                purchaseHistory.add(order);
            }
            cursor.close();
        }

        return purchaseHistory;
    }


    // Hàm mới để lấy thông tin sách bằng BookID
    private Book getBookById(int bookID) {
        String query = "SELECT * FROM Book WHERE BookID = ?";
        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(bookID)});

        Book book = new Book();

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int titleIndex = cursor.getColumnIndex("Title");
                int imageNameIndex = cursor.getColumnIndex("ImageName");

                if (titleIndex >= 0) {
                    book.setTitle(cursor.getString(titleIndex));
                }

                if (imageNameIndex >= 0) {
                    book.setImageName(cursor.getString(imageNameIndex));
                }
            }

            cursor.close();
        }

        return book;
    }
    public Book getBookInfoFromOrder(int orderID) {
        Book book = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT B.* FROM Book B " +
                "JOIN OrderDetails OD ON B.BookID = OD.BookID " +
                "JOIN Orders O ON OD.OrderID = O.OrderID " +
                "WHERE O.OrderID = ?";

        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(orderID)});

        if (cursor.moveToFirst()) {
            book = new Book();
            // Kiểm tra xem cột có tồn tại trong kết quả truy vấn không
            int columnIndexBookID = cursor.getColumnIndex("BookID");
            if (columnIndexBookID >= 0) {
                book.setBookID(cursor.getInt(columnIndexBookID));
            }

            int columnIndexCategoryID = cursor.getColumnIndex("CategoryID");
            if (columnIndexCategoryID >= 0) {
                book.setCategoryID(cursor.getInt(columnIndexCategoryID));
            }

            int columnIndexTitle = cursor.getColumnIndex("Title");
            if (columnIndexTitle >= 0) {
                book.setTitle(cursor.getString(columnIndexTitle));
            }

            int columnIndexPrice = cursor.getColumnIndex("Price");
            if (columnIndexPrice >= 0) {
                book.setPrice(cursor.getDouble(columnIndexPrice));
            }

            int columnIndexDescription = cursor.getColumnIndex("Description");
            if (columnIndexDescription >= 0) {
                book.setDescription(cursor.getString(columnIndexDescription));
            }

            int columnIndexContent = cursor.getColumnIndex("Content");
            if (columnIndexContent >= 0) {
                book.setContent(cursor.getString(columnIndexContent));
                Log.d("docsach", "Content: " + book.getContent());
            }


            int columnIndexImageName = cursor.getColumnIndex("ImageName");
            if (columnIndexImageName >= 0) {
                book.setImageName(cursor.getString(columnIndexImageName));
            }
        }

        cursor.close();
        return book;
    }

}