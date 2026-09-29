package com.sandeepc.ecom.service;

import com.sandeepc.ecom.dto.ContactRequestDto;
import com.sandeepc.ecom.dto.ContactResponseDto;

import java.util.List;

public interface IContactService {

    boolean saveContact(ContactRequestDto contactRequestDto);

    List<ContactResponseDto> getAllOpenMessages();

    void updateMessageStatus(Long contactId, String status);
}
