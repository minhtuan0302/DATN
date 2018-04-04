package org.tuan.multicast.topology;

import org.apache.commons.codec.binary.Base64;

public class BasicAuthenication {
	public String Authen(String usr,String pass) {	
		String authString = usr + ":" + pass;
		 byte [] authEncBytes = Base64.encodeBase64(authString.getBytes());
		return new String(authEncBytes);
	}  
}
