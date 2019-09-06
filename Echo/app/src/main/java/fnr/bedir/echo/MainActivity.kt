package fnr.bedir.echo

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import fnr.bedir.echo.model.MockData
import fnr.bedir.echo.model.MockDataResponse

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), MainContract.MainView, View.OnClickListener {


    var mPresenter: MainPresenterImpl? = null
    var mDataList = ArrayList<MockData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener(this)

        mPresenter = MainPresenterImpl(this)
        mPresenter?.connectSocket()
        mPresenter?.getMockData()

    }


    override fun onMockDataCompleted(mockDataResponse: MockDataResponse) {
        mDataList = mockDataResponse.data as ArrayList<MockData>
        rc_data.layoutManager = LinearLayoutManager(this)
        rc_data.adapter = MockAdapter(mDataList, this)


    }

    override fun onConnectedToSocket() {
        showMessage("Connected to Socket")
    }

    override fun onMessageReceived(id: Int, name: String) {


        val index = mDataList.indexOfFirst { it.id == id }  // -1 if not found
        if (index >= 0) {
            mDataList[index].name = name
            rc_data.post(Runnable {
                rc_data.adapter?.notifyDataSetChanged()
            })


        }

    }

    override fun changeToolbarTitle(message: String) {

        supportActionBar?.title = message


    }

    override fun showMessage(message: String) {
        Snackbar.make(fab, message, Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }

    override fun onClick(p0: View?) {
        val message = et_message.text.toString()
        if (!message.trim().isEmpty()) {
            mPresenter?.sendMessage(message.trim())
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.closeSocket()
    }
}
