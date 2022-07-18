package com.example.sdcardfileexplorer20194041;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView noFileTxt;
    ImageView addFolderIcon;
    ImageView addFileIcon;
    FolderAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT >=30 && !Environment.isExternalStorageManager()){
            Uri uri = Uri.parse("package" + BuildConfig.APPLICATION_ID);
            Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION, uri);
            startActivity(intent);
        }
        if(!checkPermission()) requestPermission();


        recyclerView = findViewById(R.id.recycle_view);
        noFileTxt =findViewById(R.id.noFile_txtV);
        addFileIcon = findViewById(R.id.add_file);
        addFolderIcon = findViewById(R.id.add_folder);

        String path = Environment.getExternalStorageDirectory().getAbsolutePath();

        File root = new File(path);
        File[] filesAndFolders = root.listFiles();

        if( filesAndFolders == null || filesAndFolders.length == 0){
            noFileTxt.setVisibility(View.VISIBLE);
            addFolderIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder newFileDialog= new AlertDialog.Builder(MainActivity.this);
                    newFileDialog.setTitle("Tạo Folder mới tên:");
                    final EditText txtNewFolder = new EditText(newFileDialog.getContext());
                    newFileDialog.setView(txtNewFolder);

                    newFileDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String new_name = txtNewFolder.getEditableText().toString();
                            File fNewFolder = new File(path+"/"+new_name);
                            Boolean bIsNewFolderCreated = fNewFolder.mkdir();
                            String path = Environment.getExternalStorageDirectory().getAbsolutePath();

                            File root = new File(path);
                            File[] filesAndFolders = root.listFiles();
                            adapter.notifyDataSetChanged();
                        }
                    });
                    newFileDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog b = newFileDialog.create();
                    b.show();
                }
            });
            addFileIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder newFileDialog= new AlertDialog.Builder(MainActivity.this);
                    newFileDialog.setTitle("Tạo File mới tên:");
                    final EditText txtNewFolder = new EditText(newFileDialog.getContext());
                    newFileDialog.setView(txtNewFolder);

                    newFileDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String new_name = txtNewFolder.getEditableText().toString();
                            File fNewFolder = new File(path,new_name);
                            FileOutputStream fos = null;
                            try {
                                fos = new FileOutputStream(fNewFolder);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            Boolean bIsNewFolderCreated = fNewFolder.mkdir();


                        }
                    });
                    newFileDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog b = newFileDialog.create();
                    b.show();
                }
            });
            return;
        }

        noFileTxt.setVisibility(View.INVISIBLE);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter =new FolderAdapter(MainActivity.this, filesAndFolders);
        recyclerView.setAdapter(adapter);
        addFolderIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder newFileDialog= new AlertDialog.Builder(MainActivity.this);
                newFileDialog.setTitle("Tạo Folder mới tên:");
                final EditText txtNewFolder = new EditText(newFileDialog.getContext());
                newFileDialog.setView(txtNewFolder);

                newFileDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String new_name = txtNewFolder.getEditableText().toString();
                        File fNewFolder = new File(path+"/"+new_name);
                        Boolean bIsNewFolderCreated = fNewFolder.mkdir();
                        adapter.notifyDataSetChanged();
                    }
                });
                newFileDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog b = newFileDialog.create();
                b.show();
            }
        });
        addFileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder newFileDialog= new AlertDialog.Builder(MainActivity.this);
                newFileDialog.setTitle("Tạo File mới tên:");
                final EditText txtNewFolder = new EditText(newFileDialog.getContext());
                newFileDialog.setView(txtNewFolder);

                newFileDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String new_name = txtNewFolder.getEditableText().toString();
                        File fNewFolder = new File(path,new_name);
                        FileOutputStream fos = null;
                        try {
                            fos = new FileOutputStream(fNewFolder);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        Boolean bIsNewFolderCreated = fNewFolder.mkdir();


                    }
                });
                newFileDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                AlertDialog b = newFileDialog.create();
                b.show();
            }
        });
    }


    private boolean checkPermission(){
        int res = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(res == PackageManager.PERMISSION_GRANTED){
            return true;
        }else  return false;
    }

    private void requestPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            Toast.makeText(MainActivity.this, "Hãy cấp quyền truy cập và sửa đổi bộ nhớ dùng chung cho ứng dụng của bạn trong \"Cài đặt\"",Toast.LENGTH_LONG).show();
        }else
            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 111);
    }
}