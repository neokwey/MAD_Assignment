package my.edu.tarc.mad_assignment

import com.google.firebase.database.Exclude

import java.net.URL

data class Vehicle (val b_url: String? = "",
    val f_url: String? = "", val l_url: String? = "",
    val r_url: String? = "", val CC_type: String? = "",
    val brand: String? = "", val carname: String? = "",
    val caryear: String? = "", val plate_num: String? = ""){  }