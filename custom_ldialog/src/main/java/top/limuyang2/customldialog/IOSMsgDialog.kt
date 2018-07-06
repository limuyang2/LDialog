package top.limuyang2.customldialog


import android.graphics.Color
import android.support.annotation.ColorInt
import android.support.v4.app.FragmentManager
import android.view.View
import android.widget.Button
import android.widget.TextView
import top.limuyang2.ldialog.base.BaseLDialog
import top.limuyang2.ldialog.base.ViewHandlerListener
import top.limuyang2.ldialog.base.ViewHolder

/**
 * iOS Style Dialog
 * Date 2018/6/26
 * @author limuyang
 */
class IOSMsgDialog : BaseLDialog<IOSMsgDialog>() {

    private var isShowTitle = false
    private var isShowPosBtn = false
    private var isShowNegBtn = false

    private var titleText: CharSequence = ""

    private var messageText: CharSequence = ""

    private var negativeButtonText: CharSequence = ""
    private var negativeButtonClickListener: View.OnClickListener? = null
    private var negativeButtonColor: Int = Color.parseColor("#0079fd")

    private var positiveButtonText: CharSequence = ""
    private var positiveButtonClickListener: View.OnClickListener? = null
    private var positiveButtonColor: Int = Color.parseColor("#0079fd")

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

                holder.getView<Button>(R.id.neg_btn).apply {
                    visibility = if (isShowNegBtn) View.VISIBLE else View.GONE
                    text = negativeButtonText
                    setTextColor(negativeButtonColor)
                    setOnClickListener {
                        negativeButtonClickListener?.onClick(it)
                        dialog.dismiss()
                    }
                }

                holder.getView<Button>(R.id.pos_btn).apply {
                    visibility = if (isShowPosBtn) View.VISIBLE else View.GONE
                    text = positiveButtonText
                    setTextColor(positiveButtonColor)
                    setOnClickListener {
                        positiveButtonClickListener?.onClick(it)
                        dialog.dismiss()
                    }
                }

            }
        }
    }

    override fun layoutRes(): Int = R.layout.layout_message_ios_dialog

    override fun layoutView(): View? = null

    /**
     * Title Text(Support Rich text)
     */
    fun setTitle(title: CharSequence): IOSMsgDialog {
        isShowTitle = true
        titleText = title
        return this
    }

    /**
     * Message Text(Support Rich text)
     */
    fun setMessage(msg: CharSequence): IOSMsgDialog {
        messageText = msg
        return this
    }

    /**
     * Left Button
     */
    @JvmOverloads
    fun setNegativeButton(text: CharSequence,
                          listener: View.OnClickListener? = null,
                          @ColorInt color: Int = negativeButtonColor): IOSMsgDialog {
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
                          @ColorInt color: Int = positiveButtonColor): IOSMsgDialog {
        isShowPosBtn = true
        positiveButtonText = text
        positiveButtonClickListener = listener
        positiveButtonColor = color
        return this
    }

    companion object {
        fun init(fragmentManager: FragmentManager): IOSMsgDialog {
            val dialog = IOSMsgDialog()
            dialog.setFragmentManager(fragmentManager)
            dialog.setBackgroundDrawableRes(R.drawable.shape_ios_dialog_bg)
            return dialog
        }
    }

}



