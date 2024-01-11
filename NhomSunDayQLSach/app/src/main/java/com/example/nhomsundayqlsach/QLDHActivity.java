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

import com.example.nhomsundayqlsach.adapter.OrderListAdapter;
import com.example.nhomsundayqlsach.model.Order;
import com.example.nhomsundayqlsach.model.OrderDAO;

import java.util.List;

public class QLDHActivity extends BaseActivity {
    private ListView listViewOrders;
    private OrderListAdapter adapter;
    private OrderDAO orderDAO;
    private Order selectedOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qldh);

        // Gọi hàm setupBackButton để xử lý nút "Quay Lại"
        setupBackButton();

        listViewOrders = findViewById(R.id.listViewOrders);
        orderDAO = new OrderDAO(this);
        List<Order> orderList = orderDAO.getAllOrders();
        adapter = new OrderListAdapter(this, orderList);
        listViewOrders.setAdapter(adapter);
        listViewOrders.setOnItemClickListener((parent, view, position, id) -> {
            // Lấy đơn hàng được chọn và lưu vào biến selectedOrder
            selectedOrder = orderList.get(position);
        });

        Button btnChangeStatus = findViewById(R.id.btnChangeStatus);
        btnChangeStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedOrder != null) {
                    // Cập nhật trạng thái đơn hàng thành "Đã thanh toán"
                    boolean updated = orderDAO.updateOrderStatus(selectedOrder.getOrderID(), "Đã thanh toán");

                    if (updated) {
                        // Cập nhật danh sách đơn hàng sau khi thay đổi trạng thái
                        orderDAO.updateOrderList(orderList);

                        // Cập nhật lại giao diện ListView
                        adapter.notifyDataSetChanged();

                        // Hiển thị thông báo thành công
                        Toast.makeText(QLDHActivity.this, "Đã cập nhật trạng thái đơn hàng thành công.", Toast.LENGTH_SHORT).show();
                    } else {
                        // Hiển thị thông báo lỗi nếu cập nhật không thành công
                        Toast.makeText(QLDHActivity.this, "Có lỗi xảy ra khi cập nhật trạng thái đơn hàng.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Thông báo cho người dùng rằng họ chưa chọn đơn hàng
                    Toast.makeText(QLDHActivity.this, "Vui lòng chọn một đơn hàng để cập nhật trạng thái.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // TODO: Xử lý sự kiện khi người dùng chọn một đơn hàng để xem chi tiết
        Button btnViewDetails = findViewById(R.id.btnViewDetails);
        btnViewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedOrder != null) {
                    // Tạo Intent để chuyển đến ChiTietQLDHActivity
                    Intent intent = new Intent(QLDHActivity.this, ChiTietQLDHActivity.class);

                    // Truyền thông tin đơn hàng sang màn hình chi tiết
                    intent.putExtra("OrderID", selectedOrder.getOrderID());
                    intent.putExtra("UserID", selectedOrder.getUserID());
                    intent.putExtra("OrderDate", selectedOrder.getOrderDate());
                    intent.putExtra("Status", selectedOrder.getStatus());

                    // Chuyển đến màn hình chi tiết
                    startActivity(intent);
                } else {
                    // Thông báo cho người dùng rằng họ chưa chọn đơn hàng
                    Toast.makeText(QLDHActivity.this, "Vui lòng chọn một đơn hàng để xem.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
