package top.limuyang2.ldialog.base

import android.os.Parcel
import android.os.Parcelable


/**
 *
 * Date 2018/7/2
 * @author limuyang
 */

abstract class ViewHandlerListener : Parcelable {

    abstract fun convertView(holder: ViewHolder, dialog: BaseLDialog<*>)

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {

    }

    constructor()

    protected constructor(source: Parcel)

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ViewHandlerListener> = object : Parcelable.Creator<ViewHandlerListener> {
            override fun createFromParcel(source: Parcel): ViewHandlerListener {
                return object : ViewHandlerListener(source) {
                    override fun convertView(holder: ViewHolder, dialog: BaseLDialog<*>) {
                        dialog?.dismiss()
                    }
                }
            }

            override fun newArray(size: Int): Array<ViewHandlerListener?> {
                return arrayOfNulls(size)
            }
        }
    }
}