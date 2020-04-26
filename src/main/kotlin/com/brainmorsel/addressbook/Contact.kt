package com.brainmorsel.addressbook

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

import com.fasterxml.jackson.annotation.JsonIgnore

@Entity
@Table(name="t_contact")
data class Contact(
    @Id
    @GeneratedValue
    var id: Long?,

    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="addressbook_id", referencedColumnName="id")
    var addressbook: Addressbook?,

    @Column(nullable=false, length=64)
    var name: String,

    @Column(nullable=true, length=64)
    var email: String?,

    @Column(nullable=true, length=64)
    var phone: String?
) {
    constructor(addressbook: Addressbook, name: String, email: String?, phone: String?):
        this(null, addressbook, name, email, phone)

    constructor(name: String, email: String?, phone: String?):
        this(null, null, name, email, phone)
}
