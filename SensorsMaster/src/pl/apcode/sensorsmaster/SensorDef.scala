package pl.apcode.sensorsmaster

import org.owfs.ownet.OWNet

abstract class SensorDef {
    val net      : OWNet
    val path     : String
    
    lazy val propertiesAll = net.Dir(path)
    
    def readProperty(name : String) = {
	   val mval = net.Read(path + "/" + name)
	   new Property(name, mval, this)
	}
}

object SensorDef {
  var debugMode = false
}

class Directory(val net : OWNet, val path :  String) extends SensorDef {
  
}

class Sensor(val net : OWNet, val path :  String) extends SensorDef {
	lazy val stype =  readProperty("type")
	lazy val id = readProperty("id")
	override def toString = f"Sensor '$path' $id $stype"
}


class Property(val name : String, val value : String, val sensordef : SensorDef)  {
	def read() = sensordef.readProperty(name)
	
	override def toString = f"$name=$value"
} 
