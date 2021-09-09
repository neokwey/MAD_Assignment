package my.edu.tarc.mad_assignment

class customer {
    private var uid: String = ""
    private var name: String = ""
    private var password: String = ""
    private var profile: String = ""
    private var status: String = ""
    private var email: String = ""
    private var phone: String = ""
    private var state: String = ""
    private var address: String = ""
    private 

    constructor()
    constructor(
        uid: String,
        name: String,
        password: String,
        profile: String,
        status: String,
        email: String,
        phone: String,
        state: String,
        address: String
    ) {
        this.uid = uid
        this.name = name
        this.password = password
        this.profile = profile
        this.status = status
        this.email = email
        this.phone = phone
        this.state = state
        this.address = address
    }

    fun getUID(): String?{
        return uid

    }
    fun setUID(uid: String){
        this.uid = uid
    }
    fun getname(): String?{
        return name
    }
    fun setname(name: String){
        this.name = name

    }
    fun getProfile(): String?{
        return profile

    }
    fun setProfile(profile: String){
        this.profile = profile

    }
    fun getStatus(): String?{
        return status

    }
    fun setStatus(status: String){
        this.status = status

    }
    fun getEmail(): String?{
        return email

    }
    fun setEmail(email: String){
        this.email = email

    }
    fun getPhone(): String?{
        return phone

    }
    fun setPhone(phone: String){
        this.phone = phone

    }
    fun getState(): String?{
        return state

    }
    fun setState(state: String){
        this.state = state

    }
    fun getAddress(): String?{
        return address

    }
    fun setAddress(address: String){
        this.address = address

    }


}