package my.edu.tarc.mad_assignment

class Vehicle {
    private var b_url: String = ""
    private var brand: String = ""
    private var CC: String = ""
    private var carname: String = ""
    private var car_plate: String = ""
    private var car_type: String = ""
    private var caryear: String = ""
    private var f_url: String = ""
    private var l_url: String = ""
    private var r_url: String = ""

    constructor()
    constructor(b_url: String, brand: String, CC: String, carname: String, car_plate: String, car_type: String, caryear: String, f_url: String, l_url: String, r_url: String) {
        this.b_url = b_url
        this.brand = brand
        this.CC = CC
        this.carname = carname
        this.car_plate = car_plate
        this.car_type = car_type
        this.caryear = caryear
        this.f_url = f_url
        this.l_url = l_url
        this.r_url = r_url
    }

    fun getbUrl(): String?{
        return b_url

    }
    fun setbUrl(b_url: String){
        this.b_url = b_url

    }

    fun getBrand(): String?{
        return brand

    }
    fun setBrand(brand: String){
        this.brand = brand

    }

    fun getCC(): String?{
        return CC

    }
    fun setCC(CC: String){
        this.CC = CC

    }

    fun getCarname(): String?{
        return carname

    }
    fun setCarname(carname: String){
        this.carname = carname

    }

    fun getPlate_num(): String?{
        return car_plate

    }
    fun setPlate_num(plate_num: String){
        this.car_plate = plate_num

    }

    fun getcarType(): String?{
        return car_type

    }
    fun setcarType(car_type: String){
        this.car_type = car_type

    }

    fun getcarYear(): String?{
        return caryear

    }
    fun setcarYear(caryear: String){
        this.caryear = caryear

    }

    fun getfUrl(): String?{
        return f_url

    }
    fun setfUrl(f_url: String){
        this.f_url = f_url

    }

    fun getlUrl(): String?{
        return l_url

    }
    fun setlUrl(l_url: String){
        this.l_url = l_url

    }

    fun getrUrl(): String?{
        return r_url

    }
    fun setrUrl(r_url: String){
        this.r_url = r_url

    }

}