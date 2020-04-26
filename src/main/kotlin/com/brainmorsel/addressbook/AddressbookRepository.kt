package com.brainmorsel.addressbook

import org.springframework.data.jpa.repository.JpaRepository

interface AddressbookRepository : JpaRepository<Addressbook, Long>
