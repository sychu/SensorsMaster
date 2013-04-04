package pl.apcode.sensorsmaster

import org.owfs.ownet._ 

import scala.reflect.runtime.universe._

trait SensorDriver {
    def read[T](path : String, property : String) : T
    def write[T](path: String, property : String, value: T): Unit
    def dir(path: String) : List[String]
    def connect(): Unit
    def disconnect(): Unit

    def pathCombine(pathA: String, pathB : String) : String
    def pathParse(path: String) : String

    protected def converToString[T](value: T): String = value match {
        case x: Boolean => if (x) "1" else "0"
        case x          => value.toString
    }
    protected def convertToValue[T: TypeTag](value: String): T = {
        val converted = typeOf[T] match {
            case x if x =:= typeOf[Boolean] => if (value.toInt == 0) false else true
            case x if x =:= typeOf[Byte]    => value.toByte
            case x if x =:= typeOf[Char]    => value.charAt(0)
            case x if x =:= typeOf[Short]   => value.toShort
            case x if x =:= typeOf[Int]     => value.toInt
            case x if x =:= typeOf[Long]    => value.toLong
            case x if x =:= typeOf[Float]   => value.toFloat
            case x if x =:= typeOf[Double]  => value.toDouble
            case x if x =:= typeOf[String]  => value
            case _ => throw new RuntimeException(s"SensorDriver.convertToValue: type '${typeOf[T]}' not allowed.")
        }
        converted.asInstanceOf[T]
    }
}

trait SensorDriverLog extends SensorDriver {
    override abstract def read[T](path : String, property : String) : T = {
        log(s"SensorDriver: read '$path' $property")
        super.read(path, property)
    }
    override abstract def write[T](path: String, property : String, value: T): Unit = {
        log(s"SensorDriver: write '$path' $property $value")
        super.write(path, property, value)
    }
    override abstract def dir(path: String) : List[String] = {
        log(s"SensorDriver:dir '$path'")
        super.dir(path)
    }
    override abstract def disconnect(): Unit = {
        log(s"SensorDriver: disconnect")
        super.disconnect()
    }

    private def log(message: => String): Unit = println("DEBUG => " + message)
}

class OWNetDriver(val host : String, val port : Int) extends SensorDriver {

    private val ownet = new OWNet(host, port) 

    def read[T](path : String, property : String) : T = {
        val value = ownet.Read(pathCombine(path, property))
        convertToValue(value)
    }

    def write[T](path: String, property : String, value: T) = {
        ownet.Write(pathCombine(path, property), converToString(value))
    }

    def dir(path: String) : List[String] = {
        ownet.Dir(path).toList
    }

    def connect() = {}

    def disconnect() = {
        ownet.Disconnect() 
    }

    def pathCombine(pathA: String, pathB : String) : String = 
        if(pathA endsWith "/" ) pathA + pathB else pathA + "/" + pathB

    def pathParse(path: String) : String = path
}
