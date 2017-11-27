package com.rednik.login.dto;

import android.net.Uri;

/**
 * Created by mauricio on 26/11/17.
 */

public class UserDTO {
    private final String id;
    private final String name;
    private final String lastName;
    private final String email;
    private final Uri profilePictureUrl;

    public UserDTO(String id, String name, String lastName, String email, Uri profilePictureUrl) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Uri getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", profilePictureUrl=" + profilePictureUrl +
                '}';
    }
}
