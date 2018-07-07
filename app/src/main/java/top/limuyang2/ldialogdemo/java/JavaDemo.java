package top.limuyang2.ldialogdemo.java;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import top.limuyang2.customldialog.IOSMsgDialog;
import top.limuyang2.customldialog.MaterialMsgDialog;
import top.limuyang2.ldialog.LDialog;
import top.limuyang2.ldialog.base.BaseLDialog;
import top.limuyang2.ldialog.base.OnDialogDismissListener;
import top.limuyang2.ldialog.base.ViewHandlerListener;
import top.limuyang2.ldialog.base.ViewHolder;
import top.limuyang2.ldialogdemo.R;

public class JavaDemo extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        IOSMsgDialog.Companion.init(getSupportFragmentManager())
                .setTitle("iOS Style")
                .setMessage("This is iOS style dialog!")
                .setAnimStyle(R.style.LDialogScaleAnimation)
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(JavaDemo.this, "关闭了弹窗", Toast.LENGTH_SHORT).show();
                    }
                }, Color.RED)
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(JavaDemo.this, "点击了确定", Toast.LENGTH_SHORT).show();
                    }
                })
                .setDismissListener(new OnDialogDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        Toast.makeText(JavaDemo.this, "dialog dismiss", Toast.LENGTH_SHORT).show();
                    }
                })
                .setCancelableOutside(true)
                .show();

        MaterialMsgDialog.Companion.init(getSupportFragmentManager())
                .setTitle("Material Style")
                .setMessage("This is Material Design dialog!")
                .setNegativeButton("Decline", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(JavaDemo.this, "Decline", Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton("Accept", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(JavaDemo.this, "Accept", Toast.LENGTH_SHORT).show();
                    }
                })
                .show();


        LDialog.Companion.init(getSupportFragmentManager())
                .setLayoutRes(R.layout.ldialog_share)
                .setBackgroundDrawableRes(R.drawable.shape_share_dialog_bg)
                .setGravity(Gravity.BOTTOM)
                .setWidthScale(0.95f)
                .setVerticalMargin(0.015f)
                .setAnimStyle(R.style.LDialogBottomAnimation)
                .setViewHandlerListener(new ViewHandlerListener() {
                    @Override
                    public void convertView(@NotNull ViewHolder holder, @NotNull BaseLDialog<?> dialog) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}
