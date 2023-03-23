package com.thanhha.myapplication.database.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.thanhha.myapplication.R;
import com.thanhha.myapplication.databinding.ItemContainerBillHistoryBinding;
import com.thanhha.myapplication.models.entity.Bill;
import com.thanhha.myapplication.models.enumerate.OrderStatus;

import java.util.List;

public class BillListAdapter extends RecyclerView.Adapter<BillListAdapter.BillListHolder>{
    private List<Bill> bills;
    private LayoutInflater layoutInflater;

    public BillListAdapter(List<Bill> bills) {
        this.bills = bills;
    }

    @NonNull
    @Override
    public BillListAdapter.BillListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemContainerBillHistoryBinding billBinding = DataBindingUtil.inflate(
                layoutInflater,
                R.layout.item_container_bill_history,
                parent,
                false);
        return new BillListAdapter.BillListHolder(billBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BillListAdapter.BillListHolder holder, int position) {
        holder.bindingCart(bills.get(position));
    }

    public Bill getBillAt(int position) {
        return bills.get(position);
    }

    public void setBills(List<Bill> bills) {
        this.bills = bills;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return bills.size();
    }

    class BillListHolder extends RecyclerView.ViewHolder {
        private ItemContainerBillHistoryBinding binding;

        public BillListHolder(@NonNull ItemContainerBillHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindingCart(Bill bill) {
            binding.setBill(bill);

            switch (OrderStatus.valueOf(bill.getStatus())) {
                case DONE: binding.billStatus.setBackgroundResource(R.color.done_status); break;
                case PREPARED: binding.billStatus.setBackgroundResource(R.drawable.background_prepare_bill); break;
                case SHIPPED:  binding.billStatus.setBackgroundResource(R.color.shipped_status); break;
                case CANCELED:  binding.billStatus.setBackgroundResource(R.color.cancel_status); break;
            }

            binding.executePendingBindings();
        }
    }
}
