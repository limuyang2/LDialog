package top.limuyang2.ldialogdemo.activity

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import top.limuyang2.customldialog.BottomTextListDialog
import top.limuyang2.customldialog.MessageIOSDialog
import top.limuyang2.ldialog.LDialog
import top.limuyang2.ldialog.base.*
import top.limuyang2.ldialogdemo.R
import top.limuyang2.ldialogdemo.fragment.FragmentActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        goToFragment.setOnClickListener {
            startActivity(Intent(this@MainActivity, FragmentActivity::class.java))
            finish()
        }

        ios_dialog_btn.setOnClickListener {
            MessageIOSDialog.init(supportFragmentManager)
                    .setTitle("IOS Style")
                    .setMessage("这是一个仿IOS弹窗")
                    .setNegativeButton("取消", View.OnClickListener {
                        Toast.makeText(this, "关闭了弹窗", Toast.LENGTH_SHORT).show()
                    }, Color.RED)
                    .setPositiveButton("确定", View.OnClickListener {
                        Toast.makeText(this, "点击了确定", Toast.LENGTH_SHORT).show()
                    })
                    .setDismissListener(object : OnDialogDismissListener() {
                        override fun onDismiss(dialog: DialogInterface?) {
                            System.out.println("dialog dismiss")
                        }
                    })
                    .setCancelableOutside(true)
                    .show()
        }

        bottom_textList_dialog_btn.setOnClickListener {
            val list = ArrayList<String>()
            for (i in 0..10) {
                list.add("Test item $i")
            }
            BottomTextListDialog.init(supportFragmentManager)
                    .setTextList(list)
                    .setHeightScale(0.6f)
                    .setKeepHeightScale(true)
                    .show()
        }

        /*** LDialog Liability ***/
        editText_dialog_btn.setOnClickListener {
            LDialog.init(supportFragmentManager)
                    .setLayoutRes(R.layout.ldialog_edittext)
                    .setWidthScale(0.6f)
                    .setViewHandlerListener(object : ViewHandlerListener() {
                        override fun convertView(holder: ViewHolder, dialog: BaseLDialog<*>) {
                            val editText = holder.getView<EditText>(R.id.input_editText)
                            holder.setOnClickListener(R.id.ok_btn, View.OnClickListener {
                                Toast.makeText(this@MainActivity, editText.text, Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                            })

                            holder.setOnClickListener(R.id.close_btn, View.OnClickListener {
                                dialog.dismiss()
                            })
                        }
                    })
                    .setNeedKeyboardViewId(R.id.input_editText)
                    .show()
        }
    }
}
