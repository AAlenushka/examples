package com.revimedia.testing.configuration.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collections;
import java.util.List;

/**
 * Created by Denis Stoianov on 4/28/2014.
 */

@XmlRootElement(name = "LeadsData")
@XmlAccessorType(XmlAccessType.FIELD)
public class Contacts implements RandomObject {

    @XmlElement(name = "ContactData")
    private List<Contact> contacts = null;

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public Contact getRandom() {
        Collections.shuffle(contacts);
        return contacts.get(0);
        //return this.employees.get(new Random().nextInt(this.employees.size()-1));
    }


}
