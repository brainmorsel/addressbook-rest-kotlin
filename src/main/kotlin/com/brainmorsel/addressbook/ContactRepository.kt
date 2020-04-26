package com.brainmorsel.addressbook

import org.springframework.data.jpa.repository.JpaRepository

interface ContactRepository : JpaRepository<Contact, Long> {
    fun findByAddressbookId(addressbookId: Long): List<Contact>
}
