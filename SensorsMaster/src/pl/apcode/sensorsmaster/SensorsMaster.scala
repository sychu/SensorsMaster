package pl.apcode.sensorsmaster

import org.owfs.ownet._

object SensorsMaster extends App {
	println("OWnet test")
	
	val host = "kamaz"
	val port = 3000
	

	testSensor()
	
	
	//testOWNet()

	
	def testSensor() = {
	  
	  SensorDriver.debugMode = true
	  
	  val owdrv = new OWNetDriver(host, port) 
	  
	  
	  
	  val tempEmilka = new Sensor(owdrv, "tempEmilka")
	  val temp = tempEmilka.getProperty("temperature")
	  println( tempEmilka )
	  println( temp )
	  println( tempEmilka.id )
	  val pioSypialnia = new Sensor(owdrv, "pioSypialnia")
	  val pio7 = pioSypialnia.getProperty("PIO.7")
	  println(pio7)
	  println(pio7)
	  println(pio7)
	  
	  println(pio7.read())
	  
	  val pio6 = pioSypialnia.getProperty("PIO.6")
	  println(pio6.read())
	  
	  val tempGaraz = new Thermometer(owdrv, "/tempGaraz")
	  tempGaraz.temperature
	  
	  println(tempGaraz.temperature)
	  
	  println(tempGaraz)
	  
	  val lsdir = new SensorDir(owdrv,"/structure/29")
	  lsdir.dir.foreach(println _)
	  
	}
	
	def testOWNet() = {
	  	val ownet = new OWNet(host,port)
		
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
		
	    ownet.Disconnect()
	}
	
	
}
