package com.brainmorsel.addressbook

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

import com.fasterxml.jackson.annotation.JsonIgnore

@Entity
@Table(name="t_addressbook")
data class Addressbook(
    @Id
    @GeneratedValue
    var id: Long?,

    @Column(nullable=false, length=64)
    var name: String
) {
    @JsonIgnore
    @OneToMany(mappedBy="addressbook", fetch=FetchType.LAZY, CascadeType.ALL)
    var contacts: Set<Contact>? = null

    constructor(name: String) : this(null, name)
}
