package pl.apcode.sensorsmaster


abstract class SensorDef {
    val driver : SensorDriver
    val path : String
     
    lazy val dir = driver.dir(path)
    
    def read(name : String) = driver.read(path, name)
    def write(name : String, value : String) = driver.write(path, name, value)

    
    def getProperty(name : String) = {
	   new SensorProperty(name, this)
	}
    
    def getPropertyDouble(name : String) = {
      new SensorPropertyDouble(name, this)
    }
    
    def getPropertyBoolean(name : String) = {
      new SensorPropertyBoolean(name, this)
    }
}


class SensorDir(val driver : SensorDriver,  val path : String) extends SensorDef {
	override def toString = f"SensorDir '$path'"
}

class Sensor(val driver : SensorDriver,  val path : String) extends SensorDef {
	lazy val stype =  getProperty("type")
	lazy val id = getProperty("id")
	
	override def toString = f"Sensor '$path' $id $stype"
}

class Thermometer(val driver : SensorDriver,  val path : String) extends SensorDef {
   lazy val temperature = getPropertyDouble("temperature")
   
   override def toString = super.toString + " " + temperature
}


