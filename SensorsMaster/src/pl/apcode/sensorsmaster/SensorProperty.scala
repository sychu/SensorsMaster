package pl.apcode.sensorsmaster

import  scala.reflect.runtime.universe._

abstract class SensorPropertyDef[T : TypeTag](valueInit : => T) {
    val parentEl : SensorDef
    val name : String

    lazy val value = valueInit
    
    def write(value : T) : T = {
        parentEl.write(name, value)
        value
    }

    def read() : T = parentEl.read(name)

    override def toString = s"$name=$value"

}



class SensorProperty[T : TypeTag](
    val name : String, val parentEl : SensorDef,  valueInit : => T) extends SensorPropertyDef[T](valueInit)  {
  
	def this(name : String, parentEl : SensorDef) {
	  this(name, parentEl, parentEl.read(name))
	}
} 
    

