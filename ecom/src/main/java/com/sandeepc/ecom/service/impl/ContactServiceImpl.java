package com.sandeepc.ecom.service.impl;

import com.sandeepc.ecom.constant.ApplicationConstants;
import com.sandeepc.ecom.dto.ContactRequestDto;
import com.sandeepc.ecom.dto.ContactResponseDto;
import com.sandeepc.ecom.entity.Contact;
import com.sandeepc.ecom.exception.ResourceNotFoundException;
import com.sandeepc.ecom.repository.ContactRepository;
import com.sandeepc.ecom.service.IContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements IContactService {

    private final ContactRepository contactRepository;

    private Contact transformEntity (ContactRequestDto contactRequestDto){
        Contact contact = new Contact();
        BeanUtils.copyProperties(contactRequestDto,contact);
        return contact;
    }

    @Override
    public boolean saveContact(ContactRequestDto contactRequestDto) {
        Contact contact = transformEntity(contactRequestDto);
        contact.setStatus(ApplicationConstants.OPEN_MESSAGE);
        contactRepository.save(contact);
        return true;
    }

    @Override
    public List<ContactResponseDto> getAllOpenMessages() {
        List<Contact> contact = contactRepository.findByStatusOrderByCreatedAtDesc(ApplicationConstants.OPEN_MESSAGE);
        return contact.stream().map(this::mapToContactResponseDto).collect(Collectors.toList());
    }

    @Override
    public void updateMessageStatus(Long contactId, String status) {
        Contact contact = contactRepository.findById(contactId).orElseThrow(
                () -> new ResourceNotFoundException("Contact","contactId",contactId.toString())
        );
        contact.setStatus(status);
        contactRepository.save(contact);
    }

    private ContactResponseDto mapToContactResponseDto(Contact contact){
        return new ContactResponseDto(contact.getContactId(),contact.getName(),contact.getEmail(),
                contact.getMobileNumber(),contact.getMessage(),contact.getStatus());
    }
}
