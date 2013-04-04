package pl.apcode.sensorsmaster

abstract class SensorDef {
    val driver : SensorDriver
    val path : String

    lazy val dir = driver.dir(path)

    def read[T](name : String): T = driver.read(path, name)
    def write[T](name : String, value : T): Unit = driver.write(path, name, value)

    def getProperty[T](name : String) = {
        new SensorProperty[T](name, this)
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

class Thermometer(val driver : SensorDriver,  val path : String) extends SensorDef {
   lazy val temperature = getProperty[Double]("temperature")

   override def toString = super.toString + " " + temperature
}
