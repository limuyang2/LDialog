package top.limuyang2.customldialog.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import top.limuyang2.customldialog.R

/**
 * Date 2018/7/4
 *
 * @author limuyang
 */
class BottomTextListAdapter(private val data: List<String>) : RecyclerView.Adapter<BottomTextListAdapter.ViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bottom_text_list_dialog, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = data[position]

        holder.itemView.setOnClickListener {
            onItemClickListener?.onClick(it, position)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.dialog_item_tv)
    }

    interface OnItemClickListener {
        fun onClick(view: View, position: Int)
    }
}
