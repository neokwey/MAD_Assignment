package my.edu.tarc.mad_assignment

data class Vehicle (val b_url: String? = "",
    val f_url: String? = "", val l_url: String? = "",
    val r_url: String? = "", val CC_type: String? = "",
    val brand: String? = "", val carname: String? = "",
    val caryear: String? = "", val plate_num: String? = ""){
    override fun toString(): String {
        return "$b_url : $plate_num"
    }
}