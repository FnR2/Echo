package fnr.bedir.echo

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fnr.bedir.echo.model.MockData
import kotlinx.android.synthetic.main.adapter_list_item.view.*

/**
 *CREATED BY bbedir on 2019-09-06.
 */
class MockAdapter(val items: ArrayList<MockData>, private val context: Context) : RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_list_item, p0, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.name.text = items[p1].name
    }

}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val name = view.tv_name
}