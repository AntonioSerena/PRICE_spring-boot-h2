package com.zara.controller;

import com.zara.model.Prices;
import com.zara.model.PriceRequest;
import com.zara.model.PriceResponse;
import com.zara.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PriceController {

  @Autowired
  private PriceService priceservice;

  @RequestMapping()
  public @ResponseBody String runningOk() {
    return "Test API ok";
  }

  @GetMapping("/allPrices")
  public ResponseEntity<List<Prices>> getAll() {
      List<Prices> pricesList = priceservice.getAll();
      return new ResponseEntity<>(pricesList, HttpStatus.OK);
  }

  @GetMapping("/price")
  public ResponseEntity<List<PriceResponse>>getPrice(@RequestBody PriceRequest priceRequest) {
    try {

      List<PriceResponse> priceResponseList = priceservice.getPriceByApplicationDate(priceRequest);

      return new ResponseEntity<>(priceResponseList, HttpStatus.OK);

    } catch (Exception exc) {
      throw new ResponseStatusException(
              HttpStatus.SERVICE_UNAVAILABLE, "PriceController error: ", exc);
    }
  }
}