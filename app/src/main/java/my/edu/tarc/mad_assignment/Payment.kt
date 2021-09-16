package my.edu.tarc.mad_assignment

class Payment {
    private var carID: String = ""
    private var ncbPersentage: Int = 0
    private var amount: Double = 0.0


    constructor()
    constructor(amount: Double, carID: String, ncbPersentage: Int){
        this.amount = amount
        this.carID = carID
        this.ncbPersentage = ncbPersentage
    }
    fun getamount(): Double?{
        return amount

    }
    fun setamount(amount: Double){
        this.amount = amount
    }

    fun getncbPersentage(): Int?{
        return ncbPersentage

    }
    fun setncbPersentage(ncbPersentage: Int){
        this.ncbPersentage = ncbPersentage
    }

    fun getcarID(): String?{
        return carID

    }
    fun setcarID(carID: String){
        this.carID = carID
    }
}