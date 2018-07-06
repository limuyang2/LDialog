package top.limuyang2.ldialog.base


import android.content.Context
import android.content.DialogInterface
import android.content.res.Configuration
import android.graphics.Point
import android.os.Bundle
import android.os.Parcelable
import android.support.annotation.DrawableRes
import android.support.annotation.FloatRange
import android.support.annotation.LayoutRes
import android.support.annotation.StyleRes
import android.support.v4.app.FragmentManager
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import kotlinx.android.parcel.Parcelize
import top.limuyang2.ldialog.R


/**
 * BaseDialog(Can inherit this class)
 * Date 2018/6/26
 * @author limuyang
 */
@Suppress("UNCHECKED_CAST")
abstract class BaseLDialog<T : BaseLDialog<T>> : android.support.v4.app.DialogFragment() {

    protected var baseParams: BaseDialogParams

    protected var viewHandlerListener: ViewHandlerListener?

    private var onDialogDismissListener: OnDialogDismissListener? = null

    protected lateinit var mContext: Context

    init {
        baseParams = BaseDialogParams().apply {
            layoutRes = layoutRes()
            view = layoutView()
        }
        viewHandlerListener = this.viewHandler()
    }

    @LayoutRes
    protected abstract fun layoutRes(): Int

    protected abstract fun layoutView(): View?

    protected abstract fun viewHandler(): ViewHandlerListener?

    open fun initView(view: View){}

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Restore UI status
        savedInstanceState?.let {
            baseParams = it.getParcelable(KEY_PARAMS)
            viewHandlerListener = savedInstanceState.getParcelable(KEY_VIEW_HANDLER)
            onDialogDismissListener = savedInstanceState.getParcelable(KEY_DISMISS_LISTENER)
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)
        //Clear the title of Android4.4
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return when {
            baseParams.layoutRes > 0 -> inflater.inflate(baseParams.layoutRes, container)
            baseParams.view != null  -> baseParams.view!!
            else                     ->
                throw IllegalArgumentException("请先设置LayoutRes或View!")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewHandlerListener?.convertView(ViewHolder.create(view), this)
        initView(view)

        //Set open Keyboard
        if (this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT && baseParams.needKeyboardViewId != 0) {
            val editText = view.findViewById<EditText>(baseParams.needKeyboardViewId)

            editText.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                              ?: return
                    if (imm.showSoftInput(editText, 0)) {
                        editText.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    }
                }
            })
        }
    }

    //save UI state
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.apply {
            putParcelable(KEY_PARAMS, baseParams)
            putParcelable(KEY_VIEW_HANDLER, viewHandlerListener)
            putParcelable(KEY_DISMISS_LISTENER, onDialogDismissListener)
        }
    }

    override fun onStart() {
        super.onStart()

        //Get screen size
        val point = Point()
        val windowManager = activity?.getSystemService(Context.WINDOW_SERVICE) as? WindowManager
        windowManager?.defaultDisplay?.getSize(point)

        //Set window
        dialog.window?.let {
            val params = it.attributes
            params.gravity = baseParams.gravity
            it.attributes
            //Set dialog width
            when {
                baseParams.widthScale > 0f -> {
                    if ((this.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE && baseParams.keepWidthScale)
                        || this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                        //横屏并且保持比例 或者 竖屏
                        params.width = (point.x * baseParams.widthScale).toInt()
                    }
                }
                baseParams.widthDp > 0f    -> params.width = dp2px(mContext, baseParams.widthDp)

//                else -> params.width = WindowManager.LayoutParams.WRAP_CONTENT
            }

            //Set dialog height
            when {
                baseParams.heightScale > 0f -> {
                    if ((this.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE && baseParams.keepHeightScale)
                        || this.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                        //横屏并且保持比例 或者 竖屏
//                        params.height = WindowManager.LayoutParams.WRAP_CONTENT
                        params.height = (point.y * baseParams.heightScale).toInt()
                    }
                }
                baseParams.heightDp > 0f    -> params.height = dp2px(mContext, baseParams.heightDp)

//                else -> params.height = WindowManager.LayoutParams.WRAP_CONTENT
            }
            //Set Window verticalMargin
            params.verticalMargin = baseParams.verticalMargin

            it.attributes = params
            it.setBackgroundDrawableResource(baseParams.backgroundDrawableRes)
            it.setWindowAnimations(baseParams.animStyle)
        }

        //Set touch cancelable
        if (!baseParams.cancelable) {
            isCancelable = baseParams.cancelable
        } else {
            dialog.setCanceledOnTouchOutside(baseParams.cancelableOutside)
        }
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        System.out.println("onDismiss")
        onDialogDismissListener?.onDismiss(dialog)
    }


    protected fun setFragmentManager(fragmentManager: FragmentManager) {
        baseParams.fragmentManager = fragmentManager
    }

    /*** Set Params  (start) [External call]***/
    fun setTag(tag: String): T {
        baseParams.tag = tag
        return this as T
    }

    fun setDismissListener(onDialogDismissListener: OnDialogDismissListener): T {
        this.onDialogDismissListener = onDialogDismissListener
        return this as T
    }

    fun setGravity(gravity: Int): T {
        baseParams.gravity = gravity
        return this as T
    }

    /**
     * Dialog occupies the proportion of the screen
     * {setWidthScale()} priority is higher than {setWidthDp()}
     * @param scale Float
     * @return T
     */
    fun setWidthScale(@FloatRange(from = 0.0, to = 1.0) scale: Float): T {
        baseParams.widthScale = scale
        return this as T
    }

    fun setWidthDp(dp: Float): T {
        baseParams.widthDp = dp
        return this as T
    }

    fun setHeightScale(@FloatRange(from = 0.0, to = 1.0) scale: Float): T {
        baseParams.heightScale = scale
        return this as T
    }

    fun setHeightDp(dp: Float): T {
        baseParams.heightDp = dp
        return this as T
    }

    /**
     * Whether to maintain the {setWidthScale()} when the screen is rotated
     * If not set {setWidthScale()}, This item does not take effect
     * @param isKeep Boolean [Default false]
     * @return T
     */
    fun setKeepWidthScale(isKeep: Boolean): T {
        baseParams.keepWidthScale = isKeep
        return this as T
    }

    /**
     * Whether to maintain the {setHeightScale()} when the screen is rotated
     * If not set {setHeightScale()}, This item does not take effect
     * @param isKeep Boolean [Default false]
     * @return T
     */
    fun setKeepHeightScale(isKeep: Boolean): T {
        baseParams.keepHeightScale = isKeep
        return this as T
    }

    fun setVerticalMargin(@FloatRange(from = 0.0, to = 0.1) verticalMargin: Float): T {
        baseParams.verticalMargin = verticalMargin
        return this as T
    }


    fun setCancelableAll(cancelable: Boolean): T {
        baseParams.cancelable = cancelable
        return this as T
    }


    fun setCancelableOutside(cancelableOutside: Boolean): T {
        baseParams.cancelableOutside = cancelableOutside
        return this as T
    }

    fun setBackgroundDrawableRes(@DrawableRes resId: Int): T {
        baseParams.backgroundDrawableRes = resId
        return this as T
    }

    fun setAnimStyle(@StyleRes animStyleRes: Int): T {
        baseParams.animStyle = animStyleRes
        return this as T
    }

    /**
     * auto open keyboard, (only EditText)
     * @param id Int EditTextView ID
     * @return T
     */
    fun setNeedKeyboardEditTextId(id: Int): T {
        baseParams.needKeyboardViewId = id
        return this as T
    }

    fun show(): T {
        show(baseParams.fragmentManager, baseParams.tag)
        return this as T
    }

    /*** Set Params  (end)***/

    companion object {
        private const val KEY_PARAMS = "key_params"
        private const val KEY_VIEW_HANDLER = "view_handler"
        private const val KEY_DISMISS_LISTENER = "dismiss_listener"

        private fun dp2px(context: Context, dipValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (dipValue * scale + 0.5f).toInt()
        }

    }

    abstract class UnParcelableParams(var fragmentManager: FragmentManager? = null,
                                      var view: View? = null)

    @Parcelize
    class BaseDialogParams(
            @LayoutRes var layoutRes: Int = 0,
            var widthScale: Float = 0f,
            var widthDp: Float = 0f,

            var heightScale: Float = 0f,
            var heightDp: Float = 0f,
            var keepWidthScale: Boolean = false,
            var keepHeightScale: Boolean = false,
            var verticalMargin: Float = 0f,

            var gravity: Int = Gravity.CENTER,
            var tag: String = "LDialog",
            var cancelable: Boolean = true,
            var cancelableOutside: Boolean = true,
            var backgroundDrawableRes: Int = R.drawable.def_dialog_bg,
            var animStyle: Int = 0,
            var needKeyboardViewId: Int = 0
    ) : UnParcelableParams(), Parcelable

}
