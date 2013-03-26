package pl.apcode.sensorsmaster

import org.owfs.ownet._

object SensorsMaster extends App {
	println("OWnet test")
	
	val host = "localhost"
	val port = 3000
	
	val ownet = new OWNet(host,port)
	testSensor()
	//testOWNet()
	ownet.Disconnect()
	
	def testSensor() = {
	  SensorDef.debugMode = true
	  val tempEmilka = new Sensor(ownet, "tempEmilka")
	  val temp = tempEmilka.readProperty("temperature")
	  println( tempEmilka )
	  println( temp )
	  println( tempEmilka.id )
	  val pioSypialnia = new Sensor(ownet, "pioSypialnia")
	  val pio7 = pioSypialnia.readProperty("PIO.7")
	  println(pio7)
	}
	
	def testOWNet() = {
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
		
		val dirs = ownet.safeDir("/");
		dirs.foreach(println _);
	}
	
	
}