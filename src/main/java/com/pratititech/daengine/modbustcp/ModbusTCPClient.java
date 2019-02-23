package com.pratititech.daengine.modbustcp;

import java.net.*;
import net.wimpi.modbus.*;
import net.wimpi.modbus.msg.*;
import net.wimpi.modbus.io.*;
import net.wimpi.modbus.net.*;


public class ModbusTCPClient {
	
	public ModbusTCPClient(String host, short port) {
		super();
		this.host = host;
		this.port = port;
	}

	private String host;
	
	private short port;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public short getPort() {
		return port;
	}

	public void setPort(short port) {
		this.port = port;
	}
	
	public ReadInputDiscretesResponse ReadRequest(ReadInputDiscretesRequest req) throws Exception	{
		
		/* The important instances of the classes mentioned before */
		TCPMasterConnection con = null; //the connection
		ModbusTCPTransaction trans = null; //the transaction
		ReadInputDiscretesResponse res = null; //the response

		/* Variables for storing the parameters */
		InetAddress addr = null; //the slave's address
		int port = Modbus.DEFAULT_PORT;
		int repeat = 1; //a loop for repeating the transaction
		
		addr = InetAddress.getByName(host);
		
		//2. Open the connection
		con = new TCPMasterConnection(addr);
		con.setPort(port);
		con.connect();

		
		//3. Prepare the transaction
		trans = new ModbusTCPTransaction(con);
		trans.setRequest(req);
		
		
		//4. Execute the transaction repeat times
		int k = 0;
		do {
		  trans.execute();
		  res = (ReadInputDiscretesResponse) trans.getResponse();
		  System.out.println("Digital Inputs Status=" + res.getDiscretes().toString());
		  k++;
		} while (k < repeat);

		 //5. Close the connection
		 con.close();
		
		return res;
		
	}

}
