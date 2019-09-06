package fnr.bedir.echo

import fnr.bedir.echo.api.ApiClient
import fnr.bedir.echo.api.ApiService
import fnr.bedir.echo.model.MockDataResponse
import fnr.bedir.echo.socket.SocketListener
import fnr.bedir.echo.socket.SocketService
import fnr.bedir.echo.socket.SocketServiceImpl
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.Exception

/**
 *CREATED BY bbedir on 2019-09-06.
 */
class MainPresenterImpl(view: MainContract.MainView) : MainContract.MainPresenter, SocketListener {

    var mApiClient: Retrofit
    var mSocketService: SocketService
    var mView: MainContract.MainView

    init {
        this.mView = view
        this.mSocketService = SocketServiceImpl(this).getInstance
        this.mApiClient = ApiClient.getInstance()
    }

    override fun getMockData() {

        mApiClient.create(ApiService::class.java).getMockData().enqueue(object : retrofit2.Callback<MockDataResponse> {
            override fun onResponse(call: Call<MockDataResponse>, response: Response<MockDataResponse>) {


                try {
                    val mockData = response.body()
                    if (mockData != null) {
                        mView.onMockDataCompleted(mockData)
                    } else {
                        mView.showMessage("Null Response")
                    }
                } catch (error: Exception) {
                    mView.showMessage(error.toString())
                }


            }

            override fun onFailure(call: Call<MockDataResponse>?, t: Throwable?) {
                mView.showMessage(t.toString())
            }
        })
    }

    override fun connectSocket() {
        mSocketService.connect()
    }

    override fun closeSocket() {
        mSocketService.disconnect()
    }

    override fun sendMessage(message: String) {
        mSocketService.sendMessage(message)
    }

    override fun onConnected() {
        mView.onConnectedToSocket()
    }

    override fun onDisconnected() {

    }

    override fun onMessageReceived(message: String) {

        try {

            if (message.toLowerCase().equals("logout")) {
                mView.changeToolbarTitle("Out")
            } else if (message.toLowerCase().equals("login")) {
                mView.changeToolbarTitle("Echo")
            } else {

                var parts = message.split("-")
                mView.onMessageReceived(parts[0].toInt(), parts[1])
            }

        } catch (exception: Exception) {
            mView.showMessage("Invalid Socket Data")
        }
    }

    override fun onErrorOccured() {
        mView.showMessage("Socket Error")
    }
}