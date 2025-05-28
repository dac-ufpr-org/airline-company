package com.msvoo.ms_voo.controller;

import com.msvoo.ms_voo.entity.Flight;
import com.msvoo.ms_voo.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
 import java.util.List;



 @RestController
 @RequestMapping("/flights")
 public class FlightController {

     @Autowired
     private FlightService flightService;

     @PostMapping
     public Flight createFlight(@RequestBody Flight flight) {
         return flightService.saveFlight(flight);
     }

     @GetMapping
     public List<Flight> getAllFlights() {
         return flightService.getAllFlights();
     }

     @GetMapping("/{id}")
     public Flight getFlightById(@PathVariable Long id) {
         return flightService.getFlightById(id);
     }

     @PutMapping("/{id}")
     public Flight updateFlight(@PathVariable Long id, @RequestBody Flight flight) {
         return flightService.updateFlight(id, flight);
     }

     @DeleteMapping("/{id}")
     public void deleteFlight(@PathVariable Long id) {
         flightService.deleteFlight(id);
     }
 }
//     public void setCode(String code) {
//         this.code = code;
//     }

//
//