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
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.nhomsundayqlsach.R;
import com.example.nhomsundayqlsach.model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {
    private Context context;
    private List<Book> bookList;
    private List<Book> filteredList;
    public BookAdapter(Context context, List<Book> bookList) {
        super(context, 0, bookList);
        this.context = context;
        this.bookList = bookList;
        this.filteredList = new ArrayList<>(bookList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_product, parent, false);
        }

        ImageView imgProduct = convertView.findViewById(R.id.imgProduct);
        TextView tvProductName = convertView.findViewById(R.id.tvProductName);

        Book book = bookList.get(position);

        // Hiển thị tên sách
        tvProductName.setText(book.getTitle());

        // Hiển thị ảnh sách từ resources
        int resourceId = context.getResources().getIdentifier(book.getImageName(), "drawable", context.getPackageName());
        imgProduct.setImageResource(resourceId);




        return convertView;
    }





    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                filteredList.clear();

                if (filterPattern.isEmpty()) {
                    // Nếu filterPattern rỗng, hiển thị tất cả sản phẩm
                    filteredList.addAll(bookList);
                } else {
                    for (Book book : bookList) {
                        if (book.getTitle().toLowerCase().contains(filterPattern)) {
                            filteredList.add(book);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filteredList;
                results.count = filteredList.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults results) {
                clear();
                addAll((List<Book>) results.values);
                notifyDataSetChanged();
            }
        };
    }
}