package pl.apcode.sensorsmaster

abstract class SensorPropertyDef[T] {
    val parentEl : SensorDef
	val name : String
	
	val readInitialValue : Boolean
	val valueInit : T
	lazy val value = if(readInitialValue) read() else valueInit

    def convert(value : String) : T
  
    def write(value : T) : T = {
    	parentEl.write(name, value.toString)
    	value
    }
    
	def read() : T = {
		convert(parentEl.read(name))
	}
	
	override def toString = f"$name=$value"
}


class SensorProperty(
    val name : String, 
    val parentEl : SensorDef, 
    val valueInit : String = "",
    val readInitialValue : Boolean = true) extends SensorPropertyDef[String] {
  
	def this(name : String, parentEl : SensorDef, valueInit : String) = 
	  this(name, parentEl, valueInit, false)
  
	def convert(value: String) = value
}


class SensorPropertyDouble(
    val name : String, 
    val parentEl : SensorDef, 
    val valueInit : Double = 0,
    val readInitialValue : Boolean = true) extends SensorPropertyDef[Double] {
  
	def this(name : String, parentEl : SensorDef, valueInit : Double) = 
	  this(name, parentEl, valueInit, false)
  
	def convert(value: String) = value.toDouble
}


class SensorPropertyBoolean(
    val name : String, 
    val parentEl : SensorDef, 
    val valueInit : Boolean = false,
    val readInitialValue : Boolean = true) extends SensorPropertyDef[Boolean] {
  
	def this(name : String, parentEl : SensorDef, valueInit : Boolean) = 
	  this(name, parentEl, valueInit, false)
  
	def convert(value: String) = value.toBoolean
}

