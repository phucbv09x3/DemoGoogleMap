package com.mon.demogooglemap.model

data class RoutesObject(var bounds:Any,var copyrights:String,var legs:MutableList<LegsObject>,var overview_polyline:PolylineObject,var summary:String) {
}