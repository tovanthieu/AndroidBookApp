package com.example.nhomsundayqlsach.adapter;
/*
 * Bản quyền (c) 2024 Tô Văn Thiều
 *
 * Đoạn mã này là sở hữu của Tô Văn Thiều.

 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.nhomsundayqlsach.R;
import com.example.nhomsundayqlsach.model.Order;
import com.example.nhomsundayqlsach.model.OrderDAO;
import com.example.nhomsundayqlsach.model.OrderDetails;

import java.util.List;

public class OrderListAdapter extends ArrayAdapter<Order> {
    private Context context;
    private List<Order> orderList;

    public OrderListAdapter(Context context, List<Order> orderList) {
        super(context, 0, orderList);
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.order_list_item, parent, false);
        }

        TextView tvOrderId = convertView.findViewById(R.id.tvOrderId);
        TextView tvProductName = convertView.findViewById(R.id.tvProductName);
        TextView tvStatus = convertView.findViewById(R.id.tvStatus);

        Order order = orderList.get(position);

        tvOrderId.setText("Mã đơn hàng: " + order.getOrderID());

        // Lấy danh sách chi tiết đơn hàng
        OrderDAO orderDAO = new OrderDAO(context);
        List<OrderDetails> orderDetails = orderDAO.getOrderDetailsByOrderId(order.getOrderID());

        // Hiển thị tên sản phẩm và trạng thái của đơn hàng
        StringBuilder productNames = new StringBuilder();
        for (OrderDetails orderDetail : orderDetails) {
            // Lấy thông tin sách từ bảng Book
            String productName = orderDAO.getBookNameById(orderDetail.getBookID());
            productNames.append(productName).append(", ");
        }
        if (productNames.length() > 0) {
            // Xóa dấu phẩy cuối cùng
            productNames.deleteCharAt(productNames.length() - 2);
        }
        tvProductName.setText("Sản phẩm: " + productNames.toString());
        tvStatus.setText("Trạng thái: " + order.getStatus());

        return convertView;
    }
}
