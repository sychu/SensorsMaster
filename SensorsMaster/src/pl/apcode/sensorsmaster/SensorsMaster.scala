package pl.apcode.sensorsmaster

import org.owfs.ownet._

object SensorsMaster extends App {
	println("OWnet test");
	
	val host = "kamaz";
	val port = 3000;
	
	val ownet = new OWNet(host,port);
	
	try {
		println (ownet.Read("/niematakiegonumeru") )
	} catch {
		case ex : OWException => {
		  ex.getErrorCode() match {
		    case OWErrorCodes.EISDIR => println("It is directory. " + ex.toString() );
		    case _ => println( ex.toString() );
		  }
		}
		case ex : Exception => println( ex.toString() );
		  
	  
	}
	ownet.Disconnect();
	
	val dirs = ownet.safeDir("/");
	dirs.foreach(println _);

	ownet.Disconnect();
}