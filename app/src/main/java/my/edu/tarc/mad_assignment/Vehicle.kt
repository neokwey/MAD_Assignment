package my.edu.tarc.mad_assignment

class Vehicle {
    private var bUrl: String = ""
    private var brand: String = ""
    private var carID: String = ""
    private var carType: String = ""
    private var carYear: String = ""
    private var carname: String = ""
    private var cc: String = ""
    private var fUrl: String = ""
    private var lUrl: String = ""
    private var plate_num: String = ""
    private var rUrl: String = ""

    constructor()
    constructor(bUrl: String, brand: String, carID: String, carType: String, carYear: String, carname: String, cc: String, fUrl: String, lUrl: String, plate_num: String, rUrl: String) {
        this.bUrl = bUrl
        this.brand = brand
        this.carID = carID
        this.cc = cc
        this.carname = carname
        this.plate_num = plate_num
        this.carType = carType
        this.carYear = carYear
        this.fUrl = fUrl
        this.lUrl = lUrl
        this.rUrl = rUrl
    }

    fun getbUrl(): String?{
        return bUrl
    }
    fun setbUrl(bUrl: String){
        this.bUrl = bUrl
    }

    fun getbrand(): String?{
        return brand

    }
    fun setbrand(brand: String){
        this.brand = brand

    }

    fun getcarID(): String?{
        return carID

    }
    fun setcarID(carID: String){
        this.carID = carID

    }

    fun getcarType(): String?{
        return carType

    }
    fun setcarType(carType: String){
        this.carType = carType

    }

    fun getcarYear(): String?{
        return carYear

    }
    fun setcarYear(carYear: String){
        this.carYear = carYear

    }

    fun getcarname(): String?{
        return carname

    }
    fun setcarname(carname: String){
        this.carname = carname

    }

    fun getcc(): String?{
        return cc

    }
    fun setcc(cc: String){
        this.cc = cc

    }

    fun getfUrl(): String?{
        return fUrl

    }
    fun setfUrl(fUrl: String){
        this.fUrl = fUrl

    }

    fun getlUrl(): String?{
        return lUrl

    }
    fun setlUrl(lUrl: String){
        this.lUrl = lUrl

    }

    fun getplate_num(): String?{
        return plate_num

    }
    fun setplate_num(plate_num: String){
        this.plate_num = plate_num

    }

    fun getrUrl(): String?{
        return rUrl

    }
    fun setrUrl(rUrl: String){
        this.rUrl = rUrl

    }

}