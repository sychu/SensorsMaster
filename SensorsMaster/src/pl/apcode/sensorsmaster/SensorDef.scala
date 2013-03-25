package pl.apcode.sensorsmaster

import org.owfs.ownet.OWNet

abstract class SensorDef( _net : OWNet,  _path : String) {

    val net = _net
    val path = _path
     
    lazy val ls = net.safeDir(path)
    
    def read(name : String) = {
      if(SensorDef.debugMode)
        println(f"DEBUG: safeRead($path/$name)")
      
      net.safeRead(path + "/" + name)
    }
    
    def getProperty(name : String) = {
	   new Property(name, this)
	}
}

object SensorDef {
  var debugMode = false
}

class SensorDir(_net : OWNet, _path :  String) extends SensorDef(_net, _path) {
 
	override def toString = f"SensorDir '$path'"
}

class Sensor(_net : OWNet, _path :  String) extends SensorDef(_net, _path) {
	lazy val stype =  getProperty("type")
	lazy val id = getProperty("id")
	
	override def toString = f"Sensor '$path' $id $stype"
}

class Thermometer(_net : OWNet, _path :  String) extends Sensor(_net, _path) {
   lazy val temperature = getProperty("temperature")
   
   override def toString = super.toString + " " + temperature
}


class Property(name : String, parentEl : SensorDef, valueInit: =>String)  {
	
	def this(name : String, parentEl : SensorDef) = this(name, parentEl, parentEl.read(name))
	
	lazy val value = valueInit
	def get() = parentEl.getProperty(name)
	
	def set(valueToSet : String) = {
	  new Property(name, parentEl, valueToSet) 
	}
	
	override def toString = f"$name=$value"
} 
