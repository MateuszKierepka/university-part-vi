package pl.pollub.android.app_2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pl.pollub.android.app_2.databinding.PhoneItemBinding;
import pl.pollub.android.app_2.model.Phone;

public class PhoneListAdapter extends RecyclerView.Adapter<PhoneListAdapter.PhoneItemViewHolder> {
    private final LayoutInflater inflater;
    private List<Phone> phoneList;
    private OnClickListener listener;

    public PhoneListAdapter(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    public void setPhoneList(List<Phone> phoneList) {
        this.phoneList = phoneList;
        this.notifyDataSetChanged();
    }

    public void setListener(OnClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public PhoneItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PhoneItemViewHolder holder = new PhoneItemViewHolder(PhoneItemBinding.inflate(this.inflater, parent, false));
        holder.setOnClickListener(this.listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneItemViewHolder holder, int position) {
        if(this.phoneList != null) {
            Phone currentPhone = this.phoneList.get(position);
            holder.binding.tvManufacturer.setText(currentPhone.getManufacturer());
            holder.binding.tvModel.setText(currentPhone.getModel());
        }
    }

    @Override
    public int getItemCount() {
        return this.phoneList != null ? this.phoneList.size() : 0;
    }

    public class PhoneItemViewHolder extends RecyclerView.ViewHolder {
        private PhoneItemBinding binding;

        public PhoneItemViewHolder(@NonNull PhoneItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }


        public void setOnClickListener(OnClickListener listener){
            this.itemView.setOnClickListener(view -> listener.onItemClick(this.getAdapterPosition()));
        }
    }
    public interface OnClickListener{
        void onItemClick(int position);
    }
}
