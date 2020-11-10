package com.mon.demogooglemap.model

data class LegsObject(var distance: ValueObject
                      , var duration: ValueObject
                      , var end_address: String
                      , var end_location: LocationObject
                      , var start_address: String
                      , var start_location: LocationObject) {
}