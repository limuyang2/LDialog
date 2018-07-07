package top.limuyang2.ldialogdemo.kotlin

import android.view.View
import top.limuyang2.ldialog.base.BaseLDialog
import top.limuyang2.ldialog.base.ViewHandlerListener
import top.limuyang2.ldialog.base.ViewHolder
import top.limuyang2.ldialogdemo.R

class ExKotlinLdialog : BaseLDialog<ExKotlinLdialog>() {

    override fun layoutRes(): Int = R.layout.ldialog_share

    override fun layoutView(): View? = null

    /**
     * 必须
     * 如果【需要】考虑横竖屏旋转，则控件的相关属性在此设置
     * @return
     */
    override fun viewHandler(): ViewHandlerListener? {
        return object : ViewHandlerListener() {
            override fun convertView(holder: ViewHolder, dialog: BaseLDialog<*>) {

            }
        }
    }

    /**
     * 可选
     * 如果【不】考虑横竖屏旋转，也可以在此设置控件属性
     * @param view
     */
    override fun initView(view: View) {

    }
}
