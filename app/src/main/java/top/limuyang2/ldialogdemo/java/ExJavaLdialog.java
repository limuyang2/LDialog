package top.limuyang2.ldialogdemo.java;

import android.view.View;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import top.limuyang2.ldialog.base.BaseLDialog;
import top.limuyang2.ldialog.base.ViewHandlerListener;
import top.limuyang2.ldialog.base.ViewHolder;
import top.limuyang2.ldialogdemo.R;

public class ExJavaLdialog extends BaseLDialog<ExJavaLdialog> {

    @Override
    protected int layoutRes() {
        return R.layout.ldialog_share;
    }

    @Nullable
    @Override
    protected View layoutView() {
        return null;
    }

    /**
     * 必须
     * 如果【需要】考虑横竖屏旋转，则控件的相关属性在此设置
     * @return
     */
    @Nullable
    @Override
    protected ViewHandlerListener viewHandler() {
        return new ViewHandlerListener() {
            @Override
            public void convertView(@NotNull ViewHolder holder, @NotNull BaseLDialog<?> dialog) {

            }
        };
    }

    /**
     * 可选
     * 如果【不】考虑横竖屏旋转，也可以在此设置控件属性
     * @param view
     */
    @Override
    public void initView(@NotNull View view) {

    }
}
