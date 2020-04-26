package com.brainmorsel.addressbook

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

class ContactNotFoundException(id: Long) : RuntimeException("Could not find contact ${id}")

@ControllerAdvice
class ContactNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(ContactNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun contactNotFoundHandler(ex: ContactNotFoundException) = ex.message
}

@RestController
class ContactController(val repository: ContactRepository, val addressbookRepository: AddressbookRepository) {

    @GetMapping("/addressbooks/{addressbookId}/contacts")
    fun all(@PathVariable addressbookId: Long) = repository.findByAddressbookId(addressbookId)

    @PostMapping("/addressbooks/{addressbookId}/contacts")
    fun newContact(@PathVariable addressbookId: Long, @RequestBody newContact: Contact): Contact {
        val addressbook = addressbookRepository.findById(addressbookId)
            .orElseThrow({ AddressbookNotFoundException(addressbookId) })
        newContact.addressbook = addressbook
        return repository.save(newContact)
    }

    @GetMapping("/contacts/{id}")
    fun one(@PathVariable id: Long): Contact =
        repository.findById(id)
            .orElseThrow({ ContactNotFoundException(id) })

    @PutMapping("/contacts/{id}")
    fun replaceContact(
        @RequestBody newContact: Contact, @PathVariable id: Long
    ): Contact =
        repository.findById(id)
            .map({
                it.name = newContact.name
                it.email = newContact.email
                it.phone = newContact.phone
                repository.save(it)
            })
            .orElseThrow({ ContactNotFoundException(id) })

    @DeleteMapping("/contacts/{id}")
    fun deleteContact(@PathVariable id: Long) = repository.deleteById(id)
}
