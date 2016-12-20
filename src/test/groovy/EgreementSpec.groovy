package com.egreement.api.connector

import feign.Feign
import feign.Logger
import feign.gson.GsonDecoder
import feign.gson.GsonEncoder
import spock.lang.Specification
class EgreementSpec extends Specification {
//
//    Egreement agreement = Feign.builder()
//            .encoder(new GsonEncoder())
//            .decoder(new GsonDecoder())
//            .logger(new Logger.JavaLogger())
//            .logLevel(Logger.Level.FULL)
//            .target(Egreement.class, "https://test.agreegram.net");

    def "getCompanyDetail"(){
        setup:
        Egreement agreement = Feign.builder()
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .logger(new Logger.JavaLogger())
                .logLevel(Logger.Level.FULL)
                .target(Egreement.class, "https://dev-nsm.agreegram.net");
        Egreement.CompanyDetailDTO companyDetailDTO = agreement.fetchCompanyInfo(['accessKey': 'WholeSaleApplication'],'5567004394',['mac':'61A2A2B44C1852A0E457A8A1C3287108']);

        expect:
        companyDetailDTO
        companyDetailDTO.accessKey=='FZdMXrm+Sn+H2AxKpgl6emIyCEIWLNfL5eIWmhmffF7Ycm0lFmbChtRobqcp3DFE3W0S7lp7WlZ+jcHCmpUbDw=='
        companyDetailDTO.isActive
        companyDetailDTO.name == 'Egreement AB'
        companyDetailDTO.contactInformation.address.city=='VÄSTRA FRÖLUNDA'
        companyDetailDTO.contactInformation.address.zipCode==42135
    }

//    def "Get"() {
//        setup:
//        String referenceNumber = createAgreement().referenceNumber
//
//        expect:
//        referenceNumber == agreement.get(['accessKey': 'Xdid9k7O+fjvZSLZwn3E+bZ3BZT3sGlzXlNbpaXZ0O1BfDe+usKKiBbNdWATrn8plLwLocZBrZqrIGJ8N/PCbw=='], referenceNumber).referenceNumber
//
//    }
//
//    def "List"() {
//        when:
//        Map<String, String> queryParams = ["from": "1987-01-01", "to": "2016-01-20"]
//        Egreement.AgreementListDTO agreementListDTO = agreement.list(['accessKey': 'Xdid9k7O+fjvZSLZwn3E+bZ3BZT3sGlzXlNbpaXZ0O1BfDe+usKKiBbNdWATrn8plLwLocZBrZqrIGJ8N/PCbw=='], queryParams);
//        List<Egreement.AgreementDTO> agreementDTOList = agreementListDTO.agreements
//
//        then:
//        agreementDTOList
//    }
//
//    def "CreateAgreement"() {
//        when:
//        Egreement.AgreementDTO agreementDTO = createAgreement()
//        then:
//        agreementDTO
//        agreementDTO.referenceNumber
//        'test agree' == agreementDTO.name
//
//    }
//
//    def "DownloadAgreement"() {
//        expect:
//        agreement.downloadAgreement(['accessKey': 'Xdid9k7O+fjvZSLZwn3E+bZ3BZT3sGlzXlNbpaXZ0O1BfDe+usKKiBbNdWATrn8plLwLocZBrZqrIGJ8N/PCbw=='], '160200031324')
//    }
//
//    def "ListAgreementFieldValues"() {
//        when:
//        Egreement.AgreementFieldValuesListDTO agreementFieldValues= agreement.listAgreementFieldValues(['accessKey': 'Xdid9k7O+fjvZSLZwn3E+bZ3BZT3sGlzXlNbpaXZ0O1BfDe+usKKiBbNdWATrn8plLwLocZBrZqrIGJ8N/PCbw=='],'160100031046')
//
//        then:
//        agreementFieldValues.referenceNumber
//    }
//
//    @IgnoreRest
//    def "ShareAgreement"() {
//        when:
//        Egreement.AgreementShareRequestDTO agreementShareRequestDTO = new Egreement.AgreementShareRequestDTO(
//                shareWithType: Egreement.ShareWithType.PERSON,
//                personNo: '195502254559',
//                email: 'wa@uhik.com',
//                shareType: Egreement.ShareType.PERMANENT,
//                message: 'sign it'
//        )
//
//        Egreement.AgreementShareDTO agreementShareDTO = agreement.shareAgreement(agreementShareRequestDTO, ['accessKey': 'Xdid9k7O+fjvZSLZwn3E+bZ3BZT3sGlzXlNbpaXZ0O1BfDe+usKKiBbNdWATrn8plLwLocZBrZqrIGJ8N/PCbw==','accountId':'66'], '160900037690')
//
//        then:
//        agreementShareDTO
//    }
//
//    def "RejectAgreement"() {
//
//        setup:
//        Egreement.AgreementRejectRequestDTO agreementRejectRequestDTO = new Egreement.AgreementRejectRequestDTO(
//                partyType: Egreement.PartyType.PRIVATE,
//                personNo: '198208197171'
//        )
//
//        expect:
//        Egreement.AgreementDTO agreementDTO = createAgreement()
//        agreement.rejectAgreement(agreementRejectRequestDTO, ['accessKey': 'Xdid9k7O+fjvZSLZwn3E+bZ3BZT3sGlzXlNbpaXZ0O1BfDe+usKKiBbNdWATrn8plLwLocZBrZqrIGJ8N/PCbw=='], agreementDTO.referenceNumber)
//    }
//
//    def "FileUpload"() {
//        ;
//        File file = new File("/home/waquar/rich.pdf")
//        Egreement.FileDTO fileDTO = agreement.fileUpload(['accessKey': 'Xdid9k7O+fjvZSLZwn3E+bZ3BZT3sGlzXlNbpaXZ0O1BfDe+usKKiBbNdWATrn8plLwLocZBrZqrIGJ8N/PCbw=='], file)
//
//        expect:
//        fileDTO
//    }
//
//    def "ListFilesUploaded"() {
//        when:
//        Egreement.FileListDTO fileListDTO = agreement.listFilesUploaded(['accessKey': 'Xdid9k7O+fjvZSLZwn3E+bZ3BZT3sGlzXlNbpaXZ0O1BfDe+usKKiBbNdWATrn8plLwLocZBrZqrIGJ8N/PCbw=='])
//
//        then:
//        fileListDTO
//    }
//
//    def "GetFileUploaded"() {
//        when:
//        Egreement.FileDTO fileDTO = agreement.getFileUploaded(['accessKey': 'Xdid9k7O+fjvZSLZwn3E+bZ3BZT3sGlzXlNbpaXZ0O1BfDe+usKKiBbNdWATrn8plLwLocZBrZqrIGJ8N/PCbw=='], 'a062b21b6bb34bd78266da08225cca3f')
//
//        then:
//        fileDTO
//    }
//
//    def "DeleteFileUploaded"() {
//
//    }
//
//    Egreement.AgreementDTO createAgreement() {
//        Egreement.NotificationMessageDTO notificationMessageDTO = new Egreement.NotificationMessageDTO(
//                type: Egreement.NotificationType.posted,
//                additionalText: "sfvdsv"
//        )
//        Egreement.PrivatePartyRequestDTO privatePartyRequestDTO = new Egreement.PrivatePartyRequestDTO(
//                firstName: 'Waquar',
//                lastName: 'Azam',
//                personNo: '7604190459',
//                email: 'waquar.azam@tothenew.com'
//
//        )
//
//        Egreement.WebhookDTO webhook = new Egreement.WebhookDTO(events: [Egreement.WebhookEvent.POSTED], url: 'http://hfab.qa3.intelligrape.net/admin/api/agreements')
//        Egreement.AgreementCreateDTO agreementCreate = new Egreement.AgreementCreateDTO(
//                name: 'test agree',
//                notificationMessages: [notificationMessageDTO],
//                type: Egreement.AgreementType.FREE_TEXT,
//                allowEmailSigning: true,
//                webhook: webhook,
//                expireIn: 30, text: 'abcd hoola',
//                parties: new Egreement.PartiesRequestDTO(
//                        privates: [privatePartyRequestDTO]))
//        Egreement.AgreementDTO agreementDTO = agreement.createAgreement(agreementCreate, ['accessKey': 'Xdid9k7O+fjvZSLZwn3E+bZ3BZT3sGlzXlNbpaXZ0O1BfDe+usKKiBbNdWATrn8plLwLocZBrZqrIGJ8N/PCbw==', 'accountId': '66'])
//
//        return agreementDTO
//    }
}
