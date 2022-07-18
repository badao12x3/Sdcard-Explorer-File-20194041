package com.example.sdcardfileexplorer20194041;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class FileAndFolderListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView noFileTxt;
    ImageView backIcon;
    TextView nameBar;
    ImageView addFolderIcon;
    ImageView addFileIcon;
    FolderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_and_folder_list);

        recyclerView = findViewById(R.id.recycle_view);
        noFileTxt =findViewById(R.id.noFile_txtV);
        backIcon = findViewById(R.id.back_buton);
        nameBar = findViewById(R.id.name_toolbar_text);
        addFileIcon = findViewById(R.id.add_file);
        addFolderIcon = findViewById(R.id.add_folder);


        String path = getIntent().getStringExtra("path");
        File root = new File(path);
        nameBar.setText(root.getName());
        File[] filesAndFolders = root.listFiles();

        if( filesAndFolders == null || filesAndFolders.length == 0){
            noFileTxt.setVisibility(View.VISIBLE);
            backIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            addFolderIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder newFileDialog= new AlertDialog.Builder(FileAndFolderListActivity.this);
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
                    AlertDialog.Builder newFileDialog= new AlertDialog.Builder(FileAndFolderListActivity.this);
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
        adapter = new FolderAdapter(FileAndFolderListActivity.this, filesAndFolders);
        recyclerView.setAdapter(adapter);

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        addFolderIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder newFileDialog= new AlertDialog.Builder(FileAndFolderListActivity.this);
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
                AlertDialog.Builder newFileDialog= new AlertDialog.Builder(FileAndFolderListActivity.this);
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

}