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

class AddressbookNotFoundException(id: Long) : RuntimeException("Could not find addressbook ${id}")

@ControllerAdvice
class AddressbookNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(AddressbookNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun addressbookNotFoundHandler(ex: AddressbookNotFoundException) = ex.message
}

@RestController
class AddressbookController(val repository: AddressbookRepository) {

    @GetMapping("/addressbooks")
    fun all() = repository.findAll()

    @PostMapping("/addressbooks")
    fun newAddressbook(@RequestBody newAddressbook: Addressbook): Addressbook =
        repository.save(newAddressbook)

    @GetMapping("/addressbooks/{id}")
    fun one(@PathVariable id: Long): Addressbook =
        repository.findById(id)
            .orElseThrow({ AddressbookNotFoundException(id) })

    @PutMapping("/addressbooks/{id}")
    fun replaceAddressbook(
        @RequestBody newAddressbook: Addressbook, @PathVariable id: Long
    ): Addressbook =
        repository.findById(id)
            .map({
                it.name = newAddressbook.name
                repository.save(it)
            })
            .orElseGet({
                newAddressbook.id = id
                repository.save(newAddressbook)
            })

    @DeleteMapping("/addressbooks/{id}")
    fun deleteAddressbook(@PathVariable id: Long) = repository.deleteById(id)
}
