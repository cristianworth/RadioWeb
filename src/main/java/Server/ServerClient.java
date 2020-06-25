/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

/**
 *
 * @author Marcelo
 */
import java.net.InetAddress;
   /**
     * class ServerClient 
     * Clientes conectados no servidor
     */
public class ServerClient {

	public String name;
	public InetAddress address;
	public int port;

	public ServerClient(String name, InetAddress address, int port) {
		this.name = name;
		this.address = address;
		this.port = port;
	}

}
