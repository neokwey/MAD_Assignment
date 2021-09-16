package my.edu.tarc.mad_assignment

class PaymentDetails {
    private var transactionID: Int = 0
    private var payDate: String = ""
    private var payTime: String = ""
    private var totalAmount: Double = 0.0
    private var totalToPay: Double = 0.0

    constructor()
    constructor(transactionID: Int, payDate: String, payTime: String, totalAmount: Double,totalToPay:Double) {
        this.transactionID = transactionID
        this.payDate = payDate
        this.payTime = payTime
        this.totalAmount = totalAmount
        this.totalToPay = totalToPay
    }

    fun gettransactionID(): Int? {
        return transactionID
    }

    fun settransactionID(transactionID: Int) {
        this.transactionID = transactionID
    }

    fun getpayDate(): String? {
        return payDate
    }

    fun setpayDate(payDate: String) {
        this.payDate = payDate
    }

    fun getpayTime(): String? {
        return payTime
    }

    fun setpayTime(payTime: String) {
        this.payTime = payTime
    }

    fun gettotalAmount(): Double? {
        return totalAmount
    }

    fun settotalAmount(totalAmount: Double) {
        this.totalAmount = totalAmount
    }

    fun gettotalToPay(): Double? {
        return totalToPay
    }

    fun settotalToPay(totalToPay: Double) {
        this.totalToPay = totalToPay
    }
}
