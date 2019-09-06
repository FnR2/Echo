package fnr.bedir.echo.socket

/**
 *CREATED BY bbedir on 2019-09-06.
 */

interface SocketListener {

    fun onConnected()
    fun onDisconnected()
    fun onMessageReceived(message:String)
    fun onErrorOccured()
}