package top.limuyang2.customldialog

import android.support.v4.app.FragmentManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.View
import top.limuyang2.customldialog.adapter.BottomTextListAdapter
import top.limuyang2.ldialog.base.BaseLDialog
import top.limuyang2.ldialog.base.ViewHandlerListener
import top.limuyang2.ldialog.base.ViewHolder

/**
 *
 * Date 2018/7/3
 * @author limuyang
 */
class BottomTextListDialog : BaseLDialog<BottomTextListDialog>() {

    init {
        setWidthScale(1f)
        setKeepWidthScale(true)
        setGravity(Gravity.BOTTOM)
        setAnimStyle(R.style.LDialogBottomAnimation)
    }

    private val textList = ArrayList<String>()

    override fun layoutRes(): Int = R.layout.layout_bottom_text_list_dialog

    override fun layoutView(): View? = null

    override fun viewHandler(): ViewHandlerListener? {
        return object : ViewHandlerListener() {
            override fun convertView(holder: ViewHolder, dialog: BaseLDialog<*>) {
                holder.getView<RecyclerView>(R.id.item_recyclerView).apply {
                    layoutManager = LinearLayoutManager(mContext)
                    adapter = BottomTextListAdapter(textList)
                }
            }
        }
    }

    fun setTextList(textList: List<String>): BottomTextListDialog {
        this.textList.apply {
            clear()
            addAll(textList)
        }
        return this
    }

    companion object {
        fun init(fragmentManager: FragmentManager): BottomTextListDialog {
            val dialog = BottomTextListDialog()
            dialog.setFragmentManager(fragmentManager)
            return dialog
        }
    }
}