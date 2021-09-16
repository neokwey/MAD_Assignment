package my.edu.tarc.mad_assignment

class PaymentVoucher{
    var quantity : String = ""
    var discountAmount : String = ""
    var vouncherID : String = ""

    constructor()
    constructor(quantity: String, discountAmount: String, vouncherID: String){
        this.quantity = quantity
        this.discountAmount = discountAmount
        this.vouncherID = vouncherID
    }
    fun getquantity(): String?{
        return quantity

    }
    fun setquantity(quantity: String){
        this.quantity = quantity
    }

    fun getdiscountAmount(): String?{
        return discountAmount

    }
    fun setdiscountAmount(discountAmount: String){
        this.discountAmount = discountAmount
    }

    fun getvouncherID(): String?{
        return vouncherID

    }
    fun setvouncherID(vouncherID: String){
        this.vouncherID = vouncherID
    }
}
