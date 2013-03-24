package org.owfs.ownet;

import java.util.HashMap;
import java.util.Map;

public enum OWErrorCodes {

	EUNKNOWN      (0),
    EACCES        ( 13),
    EADDRINUSE    ( 98),
    EADDRNOTAVAIL ( 99),
    EAGAIN        ( 11),
    EBADFS        (  9),
    EBADMSG       ( 74),
    EBUSY         ( 16),
    ECONNABORTED  (103),
    EFAULT        ( 14),
    EINTR         (  4),
    EINVAL        ( 22),
    EIO           (  5),
    EISDIR        ( 21),
    ELOOP         ( 40),
    EMSGSIZE      ( 90),
    ENAMETOOLONG  ( 36),
    ENOBUFS       (105),
	ENODEV        ( 19),
	ENOENT        (  2),
    ENOMEM        ( 12),
	ENOMSG        ( 42),
	ENOTDIR       ( 20),
	ENOTSUP       ( 95),
	ERANGE        ( 34),
	EROFS         ( 30);
	

	private final int value;
	
	private OWErrorCodes(int value) {
		this.value = value;
	}
	
	private static final Map<Integer, OWErrorCodes> intToTypeMap = new HashMap<Integer, OWErrorCodes>();
	static {
	    for (OWErrorCodes type : OWErrorCodes.values()) {
	        intToTypeMap.put(type.value, type);
	    }
	}

	public static OWErrorCodes fromInt(int i) {
		//instead ENOENT (2) owfs returns (1) in same case  
		if(i==1)
			i=2;
		OWErrorCodes type = intToTypeMap.get(Integer.valueOf(i));
	    if (type == null) 
	        return OWErrorCodes.EUNKNOWN;
	    return type;
	}	
}
