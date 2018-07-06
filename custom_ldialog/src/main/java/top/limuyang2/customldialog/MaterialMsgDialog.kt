package top.limuyang2.customldialog

import android.support.annotation.ColorInt
import android.support.v4.app.FragmentManager
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import top.limuyang2.ldialog.base.BaseLDialog
import top.limuyang2.ldialog.base.ViewHandlerListener
import top.limuyang2.ldialog.base.ViewHolder

/**
 *
 * Date 2018/7/5
 * @author limuyang
 */
class MaterialMsgDialog : BaseLDialog<MaterialMsgDialog>() {

    private var isShowTitle = false
    private var isShowPosBtn = false
    private var isShowNegBtn = false

    private var titleText: CharSequence = ""

    private var messageText: CharSequence = ""

    private var negativeButtonText: CharSequence = ""
    private var negativeButtonClickListener: View.OnClickListener? = null
    private var negativeButtonColor: Int = 0

    private var positiveButtonText: CharSequence = ""
    private var positiveButtonClickListener: View.OnClickListener? = null
    private var positiveButtonColor: Int = 0

    init {
        setWidthDp(56f * 5.5f)
        setBackgroundDrawableRes(R.drawable.def_dialog_bg)
    }


    /**
     * View Handler
     * The management of the relevant state of the view is written here
     */
    override fun viewHandler(): ViewHandlerListener? {
        return object : ViewHandlerListener() {
            override fun convertView(holder: ViewHolder, dialog: BaseLDialog<*>) {
                holder.getView<TextView>(R.id.title_tv).apply {
                    visibility = if (isShowTitle) View.VISIBLE else View.GONE
                    text = titleText
                }

                holder.getView<TextView>(R.id.msg_tv).apply {
                    text = messageText
                }

                if (!isShowNegBtn && !isShowPosBtn) {
                    holder.getView<LinearLayout>(R.id.bottomBtnLayout).visibility = View.GONE
                } else {
                    holder.getView<Button>(R.id.neg_btn).apply {
                        visibility = if (isShowNegBtn) View.VISIBLE else View.GONE
                        text = negativeButtonText
                        if (negativeButtonColor != 0) {
                            setTextColor(negativeButtonColor)
                        }
                        setOnClickListener {
                            negativeButtonClickListener?.onClick(it)
                            dialog.dismiss()
                        }
                    }

                    holder.getView<Button>(R.id.pos_btn).apply {
                        visibility = if (isShowPosBtn) View.VISIBLE else View.GONE
                        text = positiveButtonText
                        if (positiveButtonColor != 0) {
                            setTextColor(positiveButtonColor)
                        }
                        setOnClickListener {
                            positiveButtonClickListener?.onClick(it)
                            dialog.dismiss()
                        }
                    }
                }

            }
        }
    }

    override fun layoutRes(): Int = R.layout.layout_materia_dialog

    override fun layoutView(): View? = null

    /**
     * Title Text(Support Rich text)
     */
    fun setTitle(title: CharSequence): MaterialMsgDialog {
        isShowTitle = true
        titleText = title
        return this
    }

    /**
     * Message Text(Support Rich text)
     */
    fun setMessage(msg: CharSequence): MaterialMsgDialog {
        messageText = msg
        return this
    }

    /**
     * Left Button
     */
    @JvmOverloads
    fun setNegativeButton(text: CharSequence,
                          listener: View.OnClickListener? = null,
                          @ColorInt color: Int = negativeButtonColor): MaterialMsgDialog {
        isShowNegBtn = true
        negativeButtonText = text
        negativeButtonClickListener = listener
        negativeButtonColor = color
        return this
    }

    /**
     * Right Button
     */
    @JvmOverloads
    fun setPositiveButton(text: CharSequence,
                          listener: View.OnClickListener? = null,
                          @ColorInt color: Int = positiveButtonColor): MaterialMsgDialog {
        isShowPosBtn = true
        positiveButtonText = text
        positiveButtonClickListener = listener
        positiveButtonColor = color
        return this
    }

    companion object {
        fun init(fragmentManager: FragmentManager): MaterialMsgDialog {
            val dialog = MaterialMsgDialog()
            dialog.setFragmentManager(fragmentManager)
            return dialog
        }
    }

}