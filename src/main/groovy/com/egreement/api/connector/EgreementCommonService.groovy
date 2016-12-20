package com.egreement.api.connector

import com.egreement.api.connector.Egreement.AgreementCreateDTO
import feign.Feign
import feign.Logger
import feign.form.FormEncoder
import feign.gson.GsonDecoder
import feign.gson.GsonEncoder

class EgreementCommonService {

    Egreement egreement

    void setupEgreement(String url) {
        egreement = Feign.builder()
                .encoder(new FormEncoder(new GsonEncoder()))
                .decoder(new GsonDecoder())
                .logger(new Logger.JavaLogger())
                .logLevel(Logger.Level.FULL)
                .target(Egreement.class, url)
    }

    Egreement.AgreementDTO createAgreement(AgreementCreateDTO agreementCreateDTO, String accessKey, String accountId) {
        return egreement.createAgreement(agreementCreateDTO, ["accessKey": accessKey, "accountId": accountId])
    }

    Egreement.AgreementListDTO listAgreement(String accessKey, Map<String, Object> queryParams = null) {
        return egreement.list(["accessKey": accessKey], queryParams)
    }

    Egreement.AgreementDTO getAgreement(String accessKey, String referenceNumber) {
        return egreement.get(['accessKey': accessKey], referenceNumber)
    }

    static Egreement getAgreementService(String url) {
        Egreement egreement = Feign.builder()
                .encoder(new FormEncoder(new GsonEncoder()))
                .decoder(new GsonDecoder())
                .logger(new Logger.JavaLogger())
                .logLevel(Logger.Level.FULL)
                .target(Egreement.class, url)

        return egreement
    }

    static Egreement.AgreementDTO createAgreement(String url, AgreementCreateDTO agreementCreateDTO, String accessKey, String accountId) {
        Egreement agreement = getAgreementService(url)
        return agreement.createAgreement(agreementCreateDTO, ['accessKey': accessKey, 'accountId': accountId])
    }

    Egreement.CompanyDetailDTO fetchCompanyInfo(String url, String accessKey, String organizationNumber, String mac) {
        setupEgreement(url)
        return egreement.fetchCompanyInfo([accessKey: accessKey], organizationNumber, [mac: mac])
    }
}
