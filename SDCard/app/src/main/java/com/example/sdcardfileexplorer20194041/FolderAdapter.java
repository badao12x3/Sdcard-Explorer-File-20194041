package com.example.sdcardfileexplorer20194041;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;

public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.ViewHolder>{

    Context context;
    File[] filesAndFolders;

    public  FolderAdapter(Context context, File[] filesAndFolders){
        this.context = context;
        this.filesAndFolders = filesAndFolders;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.folder_file_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        File selectedFile = filesAndFolders[position];
        holder.textView.setText(selectedFile.getName());

        if(selectedFile.isDirectory()){
            holder.imageView.setImageResource(R.drawable.ic_baseline_folder_24);
        }else holder.imageView.setImageResource(R.drawable.ic_baseline_insert_drive_file_24);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedFile.isDirectory()){
                    Intent intent = new Intent(context, FileAndFolderListActivity.class);
                    String path = selectedFile.getAbsolutePath();
                    intent.putExtra("path", path);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }else {
                    try {
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        String type ="text/*";
                        intent.setDataAndType(Uri.parse(selectedFile.getAbsolutePath()),type);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }catch (Exception e){
                        Toast.makeText(context.getApplicationContext(),"Đây không phải text file. Vì vậy không thể mở file!",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                if (selectedFile.isDirectory()){
                    PopupMenu popupMenu = new PopupMenu(context,view);
                    popupMenu.getMenu().add("Rename Folder");
                    popupMenu.getMenu().add("Delete Folder");

                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            if (menuItem.getTitle().equals("Rename Folder")){
                                Toast.makeText(context.getApplicationContext(),"Đổi tên file thành công", Toast.LENGTH_SHORT).show();
                            }
                            if (menuItem.getTitle().equals("Delete Folder")){
                                boolean delete = selectedFile.delete();
                                if (delete){
                                    Toast.makeText(context.getApplicationContext(),"Xóa file thành công", Toast.LENGTH_SHORT).show();
                                    view.setVisibility(View.GONE);
                                }
                            }
                            return true;
                        }
                    });
                    popupMenu.show();
                }else {
                    PopupMenu popupMenu = new PopupMenu(context,view);
                    popupMenu.getMenu().add("Rename File");
                    popupMenu.getMenu().add("Move File");
                    popupMenu.getMenu().add("Copy File");
                    popupMenu.getMenu().add("Delete File");

                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            if (menuItem.getTitle().equals("Delete File")){
                                AlertDialog.Builder deleteDialog = new AlertDialog.Builder(context.getApplicationContext());
                                deleteDialog.setTitle("Bạn có chắc xóa file"+selectedFile.getName()+"?");

                                deleteDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        selectedFile.delete();
                                        view.setVisibility(View.GONE);
                                        Toast.makeText(context.getApplicationContext(),"Xóa file thành công",Toast.LENGTH_SHORT).show();

                                    }
                                });

                                deleteDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });
                                AlertDialog alertDialog_delete = deleteDialog.create();
                                alertDialog_delete.show();


                                boolean delete = selectedFile.delete();
                                if (delete){
                                    Toast.makeText(context.getApplicationContext(),"Xóa file thành công", Toast.LENGTH_SHORT).show();
                                    view.setVisibility(View.GONE);
                                }
                            }
                            if (menuItem.getTitle().equals("Move File")){
                                Toast.makeText(context.getApplicationContext(),"Chuyển file thành công", Toast.LENGTH_SHORT).show();

                            }
                            if (menuItem.getTitle().equals("Copy File")){
                                Toast.makeText(context.getApplicationContext(),"Sao chép file thành công", Toast.LENGTH_SHORT).show();
                            }
                            if (menuItem.getTitle().equals("Rename File")){
                                AlertDialog.Builder renameDialog = new AlertDialog.Builder(view.getContext());
                                renameDialog.setTitle("Đặt lại tên File thành:");
                                final EditText name = new EditText(renameDialog.getContext());
                                renameDialog.setView(name);

                                renameDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        String new_name = name.getEditableText().toString();
                                        String extention = selectedFile.getAbsolutePath().substring(selectedFile.getAbsolutePath().lastIndexOf("."));
                                        File current = new File(selectedFile.getAbsolutePath());
                                        File destination = new File(selectedFile.getAbsolutePath().replace(selectedFile.getName(),new_name)+extention);
                                        if (current.renameTo(destination)){
                                            holder.textView.setText(selectedFile.getName());
                                        }else{
                                            Toast.makeText(context.getApplicationContext(),"Đổi tên không thành công! Tên đã tồn tại!",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                renameDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });
                                AlertDialog alertDialog_rename = renameDialog.create();
                                alertDialog_rename.show();
                            }
                            return true;
                        }
                    });
                    popupMenu.show();
                }

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return filesAndFolders.length;
    }

    public class  ViewHolder extends RecyclerView.ViewHolder{

        TextView textView;
        ImageView imageView;

        public  ViewHolder(View itemView){
            super(itemView);
            textView = itemView.findViewById(R.id.folder_name_txtV);
            imageView = itemView.findViewById(R.id.icon_view);
        }
    }
}
