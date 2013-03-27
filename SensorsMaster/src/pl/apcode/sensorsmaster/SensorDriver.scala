package pl.apcode.sensorsmaster

trait SensorDriver {
	def read(path : String, property : String) : String
	def write(path: String, property : String, value: String)
	def dir(path: String) : List[String]
	def connect()
	def disconnect()
	
	def pathCombine(pathA: String, pathB : String) : String
	def pathParse(path: String) : String
	
	def log(message: => String) = 
	  if(SensorDriver.debugMode) println("DEBUG " + message)
}

object SensorDriver {
	var debugMode = false
	
}

import org.owfs.ownet._ 

class OWNetDriver(val host : String, val port : Int) extends SensorDriver {

    private val ownet = new OWNet(host, port) 
	
	def read(path : String, property : String) : String = {
	  log(f"OWNetDriver: read '$path' $property")
	  ownet.Read( pathCombine(path, property) )
    }
	
	def write(path: String, property : String, value: String) = {
	  log(f"OWNetDriver:write '$path' $property $value")
	  ownet.Write(pathCombine(path, property), value)
	}
	  
	def dir(path: String) : List[String] = {
	  log(f"OWNetDriver:dir '$path'")
	  ownet.Dir(path).toList
	}
	
	def connect() = {}
	
	def disconnect() = { 
	  log(f"OWNetDriver: disconnect")
	  ownet.Disconnect() 
	}
	
	def pathCombine(pathA: String, pathB : String) : String = 
	  if(pathA endsWith "/" ) pathA + pathB else pathA + "/" + pathB
	
	def pathParse(path: String) : String = path
	
}
