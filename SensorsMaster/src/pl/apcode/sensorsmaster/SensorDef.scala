package pl.apcode.sensorsmaster

import scala.reflect.runtime.universe._

abstract class SensorDef {
    val driver : SensorDriver
    val path : String

    lazy val dir = driver.dir(path)

    def read[T : TypeTag](name : String): T = driver.read(path, name)
    def write[T : TypeTag](name : String, value : T): Unit = driver.write(path, name, value)

    def getProperty[T : TypeTag](name : String) = {
        new SensorProperty[T](name, this)
    }
    
    def getProperty[T : TypeTag](name : String, valueInit : T) = {
        new SensorProperty[T](name, this, valueInit)
    }
    
    def getProperty(name : String) = {
        new SensorProperty[String](name, this)
    }
    
    def getProperty(name : String, valueInit : String) = {
        new SensorProperty[String](name, this, valueInit)
    }
}

class SensorDir(val driver : SensorDriver,  val path : String) extends SensorDef {
    override def toString = s"SensorDir '$path'"
}

class Sensor(val driver : SensorDriver,  val path : String) extends SensorDef {
    lazy val stype = getProperty[String]("type")
    lazy val id = getProperty[String]("id")

    override def toString = s"Sensor '$path' $id $stype"
}

class Thermometer(driver : SensorDriver,  path : String) extends Sensor(driver, path) {
   lazy val temperature = getProperty[Double]("temperature")

   override def toString = super.toString + " " + temperature
}
