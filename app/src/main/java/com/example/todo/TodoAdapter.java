package com.example.todo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder>  {
    List<Todo> todos;
    OnItemClickListener listener;
    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo, parent, false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        holder.textTitle.setText(todos.get(position).getTitle());
        holder.textDate.setText(todos.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        if (todos == null) {
            return 0;
        }
        return todos.size();
    }

    public class TodoViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle;
        TextView textDate;
        Button btnUpdate;
        Button btnDelete;

        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);

            textTitle = itemView.findViewById(R.id.text_title);
            textDate = itemView.findViewById(R.id.text_date);
            btnUpdate = itemView.findViewById(R.id.button_update);
            btnDelete = itemView.findViewById(R.id.button_delete);

            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onUpdateClick(getAdapterPosition());
                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onDeleteClick(getAdapterPosition());

                }
            });
        }
    }
    interface OnItemClickListener {
        void onUpdateClick(int position);

        void onDeleteClick(int position);
    }
}
