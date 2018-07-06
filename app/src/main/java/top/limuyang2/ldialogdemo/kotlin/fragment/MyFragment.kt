package top.limuyang2.ldialogdemo.kotlin.fragment

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_my.view.*
import top.limuyang2.customldialog.IOSMsgDialog
import top.limuyang2.ldialogdemo.R
import top.limuyang2.ldialogdemo.kotlin.activity.MainActivity

/**
 *
 * Date 2018/7/3
 * @author limuyang
 */
class MyFragment : Fragment() {

    private lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_my, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.goToActivity.setOnClickListener {
            startActivity(Intent(activity, MainActivity::class.java))
            activity?.finish()
        }


        view.ios_dialog_btn.setOnClickListener {
            IOSMsgDialog.init(childFragmentManager)
                    .setTitle("IOS Style")
                    .setMessage("这是一个仿IOS弹窗")
                    .setNegativeButton("取消", View.OnClickListener {
                        Toast.makeText(mContext, "关闭了弹窗", Toast.LENGTH_SHORT).show()
                    }, Color.RED)
                    .setPositiveButton("确定", View.OnClickListener {
                        Toast.makeText(mContext, "点击了确定", Toast.LENGTH_SHORT).show()
                    })
//                    .setWidthScale(1f)
//                    .setCancelableAll(false)
                    .setCancelableOutside(false)
                    .show()
        }

    }
}