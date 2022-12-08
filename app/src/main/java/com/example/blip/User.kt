package com.example.blip

class User {
    var name: String? = null
    var email: String? = null
    var uid: String? = null
    var phoneNumber: String? = null
    var password: String? = null
    var dateOfBirth: String? = null

    constructor(){}

    constructor(name: String?, email: String?, uid: String?, phoneNumber: String?, password: String?, dateOfBirth: String?){
        this.name = name
        this.email = email
        this.uid = uid
        this.phoneNumber = phoneNumber
        this.password = password
        this.dateOfBirth = dateOfBirth
    }
}