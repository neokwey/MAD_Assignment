package my.edu.tarc.mad_assignment

data class PaymentHistoryClass(var date : String? = null,
                               var discount : Double? = null,
                               var paymentMethod : String? = null,
                               var status : String? = null,
                               var time : String? = null,
                               var totalAmount : Double? = null,
                               var totalPay : String? = null,
                               var transactionID : Long?=null)
