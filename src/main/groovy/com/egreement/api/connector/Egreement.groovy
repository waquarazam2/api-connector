package com.egreement.api.connector

import feign.*

@Headers("Accept: application/json")
interface Egreement {

    @RequestLine("GET /v2/agreements/{referenceNumber}")
    AgreementDTO get(@HeaderMap Map<String, Object> headerMap, @Param("referenceNumber") String referenceNumber)

    @RequestLine("GET /v2/agreements")
    AgreementListDTO list(
            @HeaderMap Map<String, Object> headerMap, @QueryMap Map<String, Object> paramMap)

    @RequestLine("POST /v2/agreements")
    @Headers("Content-Type: application/json")
    AgreementDTO createAgreement(AgreementCreateDTO agreementCreateDTO, @HeaderMap Map<String, Object> headerMap);

    @RequestLine("GET v2/agreements/{referenceNumber}")
    AgreementDownloadDTO downloadAgreement(
            @HeaderMap Map<String, Object> headerMap, @Param("referenceNumber") String referenceNumber);

    @RequestLine("GET v2/agreements/{referenceNumber}/fields")
    AgreementFieldValuesListDTO listAgreementFieldValues(
            @HeaderMap Map<String, Object> headerMap, @Param("referenceNumber") String referenceNumber);

    @RequestLine("POST v2/agreements/{referenceNumber}/share")
    @Headers("Content-Type: application/json")
    AgreementShareDTO shareAgreement(AgreementShareRequestDTO agreementShareDTO,
                                     @HeaderMap Map<String, Object> headerMap,
                                     @Param("referenceNumber") String referenceNumber);

    @RequestLine("POST v2/agreements/{referenceNumber}/reject")
    @Headers("Content-Type: application/json")
    AgreementRejectDTO rejectAgreement(AgreementRejectRequestDTO agreementRejectRequestDTO,
                                       @HeaderMap Map<String, Object> headerMap,
                                       @Param("referenceNumber") String referenceNumber)

    @RequestLine("POST v2/files/attachment/agreement")
    @Headers("Content-Type: multipart/form-data")
    FileDTO fileUpload(@HeaderMap Map<String, Object> headerMap, @Param("file") File file);

    @RequestLine("GET v2/files/attachment/agreement")
    FileListDTO listFilesUploaded(@HeaderMap Map<String, Object> headerMap);

    @RequestLine("GET v2/files/attachment/agreement/{token}")
    FileDTO getFileUploaded(@HeaderMap Map<String, Object> headerMap, @Param("token") String token);

    @RequestLine("DELETE v2/files/attachment/agreement/{token}")
    FileDeleteDTO deleteFileUploaded(@HeaderMap Map<String, Object> headerMap, @Param("token") String token);

    @RequestLine("GET v2/companies/{organizationNumber}")
    CompanyDetailDTO fetchCompanyInfo(
            @HeaderMap Map<String, Object> headerMap,
            @Param("organizationNumber") String organizationNumber, @QueryMap Map<String, Object> paramMap)

    static class AgreementListDTO {
        List<AgreementDTO> agreements
        Integer start
        Integer count
        Integer totalCount
    }

    static class AgreementDTO {
        String referenceNumber
        String name
        String status
        String postedOn
        String expireOn
        String closedOn
        String rejectedOn
        String lastModifiedOn
        PartiesDTO parties

    }

    static class PartiesDTO {
        List<PrivatePartyDTO> privates
        List<CompanyPartyDTO> companies
    }

    static class PrivatePartyDTO {
        String personNo
        String name
        SignatureStatus status
        String signedOn
        String rejectedOn
    }

    static class CompanyPartyDTO {
        String orgNo
        String name
        String personNo
        SignatureStatus status
        String signedOn
        String rejectedOn
    }

    static enum SignatureStatus {
        NOT_SIGNED, SIGNED, REJECTED
    }

    static class AgreementCreateDTO {
        String name
        AgreementType type
        String expireIn
        List<NotificationMessageDTO> notificationMessages
        boolean allowEmailSigning
        List<ReminderDTO> reminders
        PartiesRequestDTO parties
        String text
        List<AttachmentDTO> attachments
        DynamicPdfDTO dynamicPdf
        WebhookDTO webhook
    }

    static enum AgreementType {
        FREE_TEXT, ATTACHED, DYNAMIC_PDF
    }

    static class NotificationMessageDTO {
        NotificationType type
        String additionalText
    }

    static class DynamicPdfDTO {
        String templateId
        List<DynamicPdfFieldDTO> fields
    }

    static abstract class AttachmentDTO {
        AgreementAttachmentOperationType optType
    }

    static enum NotificationType {
        posted, rejected, autoRejectionReminder, autoRejection, signed, closed
    }

    static class Base64BasedAttachmentDTO extends AttachmentDTO {
        String name
        File data
    }

    static class TokenBasedAttachmentDTO extends AttachmentDTO {
        String token
    }

    static class DynamicPdfFieldDTO {
        String name
        String value
    }

    static enum AgreementAttachmentOperationType {
        ATTACH, MERGE
    }

    static class PartiesRequestDTO {
        List<PrivatePartyRequestDTO> privates
        List<CompanyPartyRequestDTO> companies
    }

    static class PrivatePartyRequestDTO {
        String personNo
        String firstName
        String lastName
        String email
        Integer signingOrder
    }

    static class CompanyPartyRequestDTO {
        String orgNo
        String firstName
        String lastName
        String email
        Integer signingOrder
        List<PrivatePartyRequestDTO> allowedPersons

    }

    static class AgreementDownloadDTO {

    }

    static class AgreementFieldValuesListDTO {
        String referenceNumber
        String dynamicPdfId
        DynamicPdfFieldDTO[] fields
    }

    static class AgreementShareRequestDTO {
        ShareWithType shareWithType
        String personNo
        String orgNo
        String email
        String message
        ShareType shareType
        Date date

    }

    static class ReminderDTO {
        String date
        String message
        String email
    }

    static class WebhookDTO {
        List<WebhookEvent> events
        String url
    }

    static enum WebhookEvent {
        POSTED,
        SIGNED,
        SIGNED_BY_ALL,
        REJECTED
    }

    static enum ShareWithType {
        PERSON, ORGANIZATION
    }

    static class AgreementShareDTO {
        String referenceNumber
        String sharedWith
        ShareType shareType
    }

    static enum ShareType {
        PERMANENT, TILL_DATE
    }

    static class AgreementRejectRequestDTO {
        String referenceNumber
        PartyType partyType
        String personNo
        String orgNo
    }

    static enum PartyType {
        PRIVATE, COMPANY
    }

    static class AgreementRejectDTO {
        String referenceNumber
        RejectedBy rejectedBy;
    }

    static class RejectedBy {
        String orgNo
        String personNo
    }

    static class FileUploadRequestDTO {
        File file
    }

    static class FileListDTO {
        List<FileDTO> items
        Integer start
        Integer count
        Integer totalCount
    }

    static class FileDTO {
        String token
        String fileName
        String createdOn
    }

    static class FileDeleteDTO {
        String token
        String fileName
        Date deletedOnDate
    }

    static class CompanyDetailDTO {
        String name
        String accessKey
        boolean isActive
        ContactInfoDTO contactInformation
    }

    static class ContactInfoDTO {
        String firstName
        String lastName
        String email
        AddressDTO address
    }

    static class AddressDTO{
        String street1
        String street2
        String city
        String country
        Integer zipCode
    }
}