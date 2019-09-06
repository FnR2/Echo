package fnr.bedir.echo

import fnr.bedir.echo.model.MockDataResponse

/**
 *CREATED BY bbedir on 2019-09-06.
 */
interface MainContract {

    interface MainPresenter {

        fun getMockData()
        fun connectSocket()
        fun closeSocket()
        fun sendMessage(message: String)


    }

    interface MainView {

        fun onMockDataCompleted(mockDataResponse: MockDataResponse)
        fun onConnectedToSocket()
        fun onMessageReceived(id: Int,name:String)
        fun showMessage(message: String)
        fun changeToolbarTitle(message: String)
    }
}