package com.ronaldogoncalves.coopvote.integration;

import com.ronaldogoncalves.coopvote.exception.BusinessException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CpfService {
    private final String externalApiUrl;
    private final RestTemplate restTemplate;

    public CpfService(@Value("${external.service.cpf.url}") String externalApiUrl, RestTemplate restTemplate) {
        this.externalApiUrl = externalApiUrl;
        this.restTemplate = restTemplate;
    }

    public boolean isAbleToVote(String cpf) {
        String apiUrl = externalApiUrl + "/" + cpf;
        CpfServiceResponse response = restTemplate.getForObject(apiUrl, CpfServiceResponse.class);

        if (response != null && response.isAbleToVote()) {
            return true;
        }

        throw new BusinessException("CPF is not eligible to vote.");
    }

}

