package my.edu.tarc.mad_assignment

class insurance {
    private var carID: String = ""
    private var expiredDate: String = ""
    private var count: Int = 0
    private var ncb: Int = 0
    private var ncbPersentage: Double = 0.0
    private var amount: Double = 0.0

    constructor()

    constructor(carID: String, expiredDate: String, ncb: Int) {
        this.carID = carID
        this.expiredDate = expiredDate
        this.ncb = ncb
    }





    fun getexpiredDate(): String?{
        return expiredDate

    }
    fun setexpiredDate(expiredDate: String){
        this.expiredDate = expiredDate
    }

    fun getncb(): Int?{
        return ncb
    }
    fun setncb(ncb: Int){
        this.ncb = ncb
    }

}