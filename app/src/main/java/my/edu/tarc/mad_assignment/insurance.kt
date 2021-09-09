package my.edu.tarc.mad_assignment

class insurance {
    private var carID: String = ""
    private var expiredDate: String = ""
    private var count: Int = 0

    constructor()
    constructor(carID: String, expiredDate: String, count: Int) {
        this.carID = carID
        this.expiredDate = expiredDate
        this.count = count
    }

    fun getcarID(): String?{
        return carID

    }
    fun setcarID(carID: String){
        this.carID = carID
    }

    fun getexpiredDate(): String?{
        return expiredDate

    }
    fun setexpiredDate(expiredDate: String){
        this.expiredDate = expiredDate
    }

    fun getcount(): Int?{
        return count
    }
    fun setcount(count: Int){
        this.count = count
    }

}