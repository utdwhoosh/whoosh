package edu.utdallas.whoosh.api;

import java.util.List;

/**
 * Service interface that provides functionality to retrieve {@link Contact} information.
 *
 * Created by sasha on 9/22/15.
 */
public interface DirectoryService {

    /**
     * returns all available {@link Contact}s
     */
    List<Contact> getContacts();

}