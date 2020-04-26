#!/usr/bin/env bash


BASE_URL="http://localhost:9000"

call () {
    echo ">>> curl $@"
    curl "$@"
    echo
}

call $BASE_URL/addressbooks
call $BASE_URL/addressbooks/1
call $BASE_URL/addressbooks/2
call $BASE_URL/addressbooks/3
call $BASE_URL/addressbooks/4
call $BASE_URL/addressbooks/1/contacts
call \
    -H 'Content-Type: application/json' \
    --data-raw '{"name":"test", "email": "test"}' \
    $BASE_URL/addressbooks/1/contacts
call -X PUT\
    -H 'Content-Type: application/json' \
    --data-raw '{"name":"Test", "phone": "+12345"}' \
    $BASE_URL/contacts/8
call -X DELETE $BASE_URL/contacts/8
