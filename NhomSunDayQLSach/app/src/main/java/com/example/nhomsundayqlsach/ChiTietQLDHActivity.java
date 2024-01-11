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
import android.widget.TextView;

import com.example.nhomsundayqlsach.model.OrderDAO;
import com.example.nhomsundayqlsach.model.OrderDetails;
import com.example.nhomsundayqlsach.model.User;
import com.example.nhomsundayqlsach.model.UserDAO;

import java.util.List;

public class ChiTietQLDHActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet_qldh);

        // Lấy dữ liệu từ Intent
        Intent intent = getIntent();
        if (intent != null) {
            int orderID = intent.getIntExtra("OrderID", -1);
            int userID = intent.getIntExtra("UserID", -1);
            String orderDate = intent.getStringExtra("OrderDate");
            String status = intent.getStringExtra("Status");

            // Hiển thị thông tin đơn hàng trong giao diện của ChiTietQLDHActivity
            TextView tvOrderId = findViewById(R.id.tvOrderId);
            TextView tvBuyerName = findViewById(R.id.tvBuyerName);
            TextView tvProductName = findViewById(R.id.tvProductName);
            TextView tvPrice = findViewById(R.id.tvPrice);
            TextView trangthai = findViewById(R.id.trangthai);

            // Hiển thị thông tin đơn hàng trong giao diện
            tvOrderId.setText("Mã đơn hàng: " + orderID);

            // Lấy tên người mua
            String buyerName = getBuyerName(userID);
            tvBuyerName.setText("Người mua: " + buyerName);

            // Hiển thị thông tin sản phẩm và giá
            displayOrderDetails(orderID);

            trangthai.setText("Trạng thái thanh toán: " + status);

            // Xử lý sự kiện khi nhấn nút "Quay Lại"
            Button btnBack = findViewById(R.id.btnBack);
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish(); // Đóng màn hình hiện tại và quay lại màn hình trước đó
                }
            });
        }
    }
    private void displayOrderDetails(int orderID) {
        // Khởi tạo OrderDAO để thực hiện truy vấn cơ sở dữ liệu
        OrderDAO orderDAO = new OrderDAO(this);
        orderDAO.open();

        // Lấy danh sách chi tiết đơn hàng
        List<OrderDetails> orderDetailsList = orderDAO.getOrderDetailsByOrderId(orderID);

        // Khởi tạo các biến để lưu thông tin sản phẩm và giá
        StringBuilder productNames = new StringBuilder();
        double totalPrice = 0.0;

        // Lặp qua danh sách chi tiết đơn hàng để lấy thông tin sản phẩm và giá
        for (OrderDetails orderDetail : orderDetailsList) {
            int bookID = orderDetail.getBookID();
            String productName = orderDAO.getBookNameById(bookID);
            double price = orderDAO.getPriceByBookId(bookID);

            // Thêm thông tin sản phẩm và giá vào StringBuilder
            productNames.append(productName).append(", ");
            totalPrice += price;
        }

        // Đóng OrderDAO sau khi sử dụng xong
        orderDAO.close();

        // Kiểm tra xem có thông tin chi tiết đơn hàng hay không
        if (productNames.length() > 0) {
            // Xóa dấu phẩy cuối cùng
            productNames.deleteCharAt(productNames.length() - 2);
        }

        // Hiển thị thông tin trong tvProductName và tvPrice
        TextView tvProductName = findViewById(R.id.tvProductName);
        TextView tvPrice = findViewById(R.id.tvPrice);

        tvProductName.setText("Sản phẩm: " + productNames.toString());
        tvPrice.setText("Tổng giá: " + totalPrice);
    }


    private String getBuyerName(int userID) {
        String buyerName = "Không xác định";
        UserDAO userDAO = new UserDAO(this);

        try {
            userDAO.open();
            User buyer = userDAO.getUserByID(userID);

            if (buyer != null) {
                buyerName = buyer.getUsername();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            userDAO.close();
        }

        return buyerName;
    }


}
