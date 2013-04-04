package pl.apcode.sensorsmaster

import  scala.reflect.runtime.universe._

abstract class SensorPropertyDef[T] {
    val parentEl : SensorDef
    val name : String

    val initialValue = read()

    def write(value : T) : T = {
        parentEl.write(name, value)
        value
    }

    def read() : T = parentEl.read(name)

    override def toString = s"$name=$initialValue"

}

class SensorProperty[T](
    val name : String, 
    val parentEl : SensorDef) extends SensorPropertyDef[T] {
}
