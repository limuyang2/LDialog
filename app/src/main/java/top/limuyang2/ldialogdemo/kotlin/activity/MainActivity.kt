package top.limuyang2.ldialogdemo.kotlin.activity

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import top.limuyang2.customldialog.BottomTextListDialog
import top.limuyang2.customldialog.IOSMsgDialog
import top.limuyang2.customldialog.MaterialMsgDialog
import top.limuyang2.customldialog.adapter.BottomTextListAdapter
import top.limuyang2.ldialog.LDialog
import top.limuyang2.ldialog.base.*
import top.limuyang2.ldialogdemo.R
import top.limuyang2.ldialogdemo.kotlin.fragment.FragmentActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        goToFragment.setOnClickListener {
            startActivity(Intent(this@MainActivity, FragmentActivity::class.java))
            finish()
        }


        /*** CustomLDialog Library ***/
        ios_dialog_btn.setOnClickListener {
            IOSMsgDialog.init(supportFragmentManager)
                    .setTitle("iOS Style")
                    .setMessage("This is iOS style dialog!")
                    .setAnimStyle(R.style.LDialogScaleAnimation)
                    .setNegativeButton("取消", View.OnClickListener {
                        Toast.makeText(this@MainActivity, "关闭了弹窗", Toast.LENGTH_SHORT).show()
                    }, Color.RED)
                    .setPositiveButton("确定", View.OnClickListener {
                        Toast.makeText(this@MainActivity, "点击了确定", Toast.LENGTH_SHORT).show()
                    })
                    .setDismissListener(object : OnDialogDismissListener() {
                        override fun onDismiss(dialog: DialogInterface?) {
                            Toast.makeText(this@MainActivity, "dialog dismiss", Toast.LENGTH_SHORT).show()
                        }
                    })
                    .setCancelableOutside(true)
                    .show()
        }

        material_dialog_btn.setOnClickListener {
            MaterialMsgDialog.init(supportFragmentManager)
                    .setTitle("Material Style")
                    .setMessage("This is Material Design dialog!")
                    .setNegativeButton("Decline", View.OnClickListener {
                        Toast.makeText(this@MainActivity, "Decline", Toast.LENGTH_SHORT).show()
                    })
                    .setPositiveButton("Accept", View.OnClickListener {
                        Toast.makeText(this@MainActivity, "Accept", Toast.LENGTH_SHORT).show()
                    })
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
                    .setOnItemClickListener(object : BottomTextListAdapter.OnItemClickListener {
                        override fun onClick(view: View, position: Int) {
                            Toast.makeText(this@MainActivity, list[position], Toast.LENGTH_SHORT).show()
                        }
                    })
                    .show()
        }

        /*** LDialog Library ***/
        editText_dialog_btn.setOnClickListener {
            LDialog.init(supportFragmentManager)
                    .setLayoutRes(R.layout.ldialog_edittext)
                    .setWidthScale(1f)
                    .setGravity(Gravity.BOTTOM)
                    .setViewHandlerListener(object : ViewHandlerListener() {
                        override fun convertView(holder: ViewHolder, dialog: BaseLDialog<*>) {
                            val editText = holder.getView<EditText>(R.id.input_editText)
                            holder.setOnClickListener(R.id.sendBtn, View.OnClickListener {
                                Toast.makeText(this@MainActivity, editText.text, Toast.LENGTH_SHORT).show()
                                dialog.dismiss()
                            })

                        }
                    })
                    .setNeedKeyboardEditTextId(R.id.input_editText)
                    .show()
        }

        share_dialog_btn.setOnClickListener {
            LDialog.init(supportFragmentManager)
                    .setLayoutRes(R.layout.ldialog_share)
                    .setBackgroundDrawableRes(R.drawable.shape_share_dialog_bg)
                    .setGravity(Gravity.BOTTOM)
                    .setWidthScale(0.95f)
                    .setVerticalMargin(0.015f)
                    .setAnimStyle(R.style.LDialogBottomAnimation)
                    .setViewHandlerListener(object : ViewHandlerListener() {
                        override fun convertView(holder: ViewHolder, dialog: BaseLDialog<*>) {
                            holder.setOnClickListener(R.id.cancelBtn, View.OnClickListener {
                                dialog.dismiss()
                            })
                        }
                    })
                    .show()

        }

        topTips_dialog_btn.setOnClickListener {
            val handler = Handler()
            val dialog = LDialog.init(supportFragmentManager)
                    .setTag("topTips")
                    .setLayoutRes(R.layout.ldialog_top_tips)
                    .setGravity(Gravity.TOP)
                    .setWidthScale(1f)
                    .setKeepWidthScale(true)
                    .setAnimStyle(R.style.LDialogHorizontalAnimation)
                    .setViewHandlerListener(object : ViewHandlerListener() {
                        override fun convertView(holder: ViewHolder, dialog: BaseLDialog<*>) {
                            handler.postDelayed({
                                                    dialog.dismiss()
                                                }, 3000)
                        }
                    })
                    .show()

        }
    }
}
