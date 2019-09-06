package fnr.bedir.echo.socket

import android.util.Log
import okhttp3.*
import okio.ByteString
import okhttp3.WebSocket


/**
 *CREATED BY bbedir on 2019-09-06.
 */
class SocketServiceImpl(listener: SocketListener?) : SocketService, WebSocketListener() {


    private val mBaseUrl: String = "wss://echo.websocket.org"
    private var mService: SocketService? = null
    private var mListener: SocketListener? = null
    private var mWebSocket: WebSocket? = null
    private var mSocketClient: OkHttpClient? = null

    init {
        this.mListener = listener
    }

    val getInstance: SocketService
        get() {
            if (mService == null) {
                mService = SocketServiceImpl(mListener)
                initializeSocket()
            }

            return mService as SocketServiceImpl
        }


    override fun connect() {

        //  mSocketClient?.dispatcher()?.executorService()?.shutdown()

    }

    override fun disconnect() {
        mWebSocket?.close(1000, null);
    }

    override fun initializeSocket() {

        mSocketClient = OkHttpClient()
        val request = Request.Builder().url(mBaseUrl).build()

        mWebSocket = mSocketClient?.newWebSocket(request, this)


    }

    override fun sendMessage(mMessage: String) {

        mSocketClient = OkHttpClient()   // this is a messy solution.Opening socket every time for sending message.
        // There is a bug that i couldnt understand  why !!!
        val request = Request.Builder().url(mBaseUrl).build()

        var mWebSocket = mSocketClient?.newWebSocket(request, this)
        val success = mWebSocket?.send(mMessage)
        if (success == false) {
            mListener?.onErrorOccured()
        }

    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        mListener?.onConnected()

        //mWebSocket?.close(1000, "Goodbye, World!");
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        mListener?.onErrorOccured()

    }


    override fun onMessage(webSocket: WebSocket, text: String) {
        mListener?.onMessageReceived(text)
        Log.e("receive", text)
    }


    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {

        Log.e("closed", "closed")
    }

}