package com.brainmorsel.addressbook

import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import com.brainmorsel.addressbook.logger

@Configuration
class LoadDatabase {
    val logger = logger()

    @Bean
    fun initDatabase(addressbookRepository: AddressbookRepository, contactRepository: ContactRepository) = CommandLineRunner {
        logger.info(
            "Preloading {}", addressbookRepository.save(Addressbook("Family")).also {
                it.contacts = setOf(
                    Contact(it, "Mom", "mom@examle.com", "+123456"),
                    Contact(it, "Dad", "dad@examle.com", "+123457")
                )
                it.contacts?.let { contactRepository.saveAll(it.toList()) }
            }
        )
        logger.info(
            "Preloading {}", addressbookRepository.save(Addressbook("Co-workers")).also {
                it.contacts = setOf(
                    Contact(it, "Anny", "anny@examle.com", "+223456"),
                    Contact(it, "Bob", "bob@examle.com", "+223457"),
                    Contact(it, "Carl", "carl@examle.com", "+223458")
                )
                it.contacts?.let { contactRepository.saveAll(it.toList()) }
            }
        )
    }
}
