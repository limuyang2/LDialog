package top.limuyang2.ldialogdemo.activity

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import top.limuyang2.customldialog.MessageIOSDialog
import top.limuyang2.ldialog.base.OnDialogDismissListener
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
                    .setDismissListener(object : OnDialogDismissListener(){
                        override fun onDismiss(dialog: DialogInterface?) {
                            System.out.println("dialog dismiss")
                        }
                    })
//                    .setWidthScale(1f)
//                    .setCancelableAll(false)
                    .setCancelableOutside(true)
                    .show()
        }
    }
}
