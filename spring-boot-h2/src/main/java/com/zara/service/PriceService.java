package com.zara.service;

import com.zara.model.Prices;
import com.zara.model.PriceRequest;
import com.zara.model.PriceResponse;
import com.zara.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class PriceService {

    @Autowired
    private PriceRepository priceRepository;

    public List<Prices> getAll () {
        return priceRepository.findAll();
    }

    public List<PriceResponse> getPriceByApplicationDate (PriceRequest priceRequest) {
        try {
            List <Prices> pricesList = priceRepository.findByApplicationDateTime(priceRequest.getProductId(),
                    priceRequest.getBrandId(), priceRequest.getApplicationDate());
            List <PriceResponse> priceResponseList = new ArrayList<>();
            for (Prices prices : pricesList) {
                priceResponseList.add(new PriceResponse(prices.getProductId(), prices.getBrandId(),
                        prices.getPriceList(), prices.getStartDate(), prices.getEndDate(), prices.getPrice()));
            }
            return priceResponseList;

        } catch (Exception exc) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "PriceService error: ", exc);
        }
    }
}