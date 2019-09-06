package fnr.bedir.echo.socket

/**
 *CREATED BY bbedir on 2019-09-06.
 */

interface SocketService {

    fun initializeSocket()
    fun connect()
    fun disconnect()
    fun sendMessage(mMessage:String)
}