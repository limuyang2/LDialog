package top.limuyang2.ldialog


import android.support.annotation.LayoutRes
import android.support.v4.app.FragmentManager
import android.view.View
import top.limuyang2.ldialog.base.BaseLDialog
import top.limuyang2.ldialog.base.ViewHandlerListener

/**
 *
 * Date 2018/6/27
 * @author limuyang
 */
class LDialog : BaseLDialog<LDialog>() {

    override fun layoutRes(): Int = 0

    override fun layoutView(): View? = null

    override fun viewHandler(): ViewHandlerListener? {
        return null
    }

    fun setLayoutRes(@LayoutRes layoutRes: Int): LDialog {
        baseParams.layoutRes = layoutRes
        return this
    }

    fun setLayoutView(view: View): LDialog {
        baseParams.view = view
        return this
    }

    fun setViewHandlerListener(viewHandlerListener: ViewHandlerListener): LDialog {
        this@LDialog.viewHandlerListener = viewHandlerListener
        return this
    }

    companion object {
        fun init(fragmentManager: FragmentManager): LDialog {
            val dialog = LDialog()
            dialog.setFragmentManager(fragmentManager)
            return dialog
        }
    }
}