package com.rednik.login.dto;

import android.net.Uri;

/**
 * Created by mauricio on 26/11/17.
 */

public class UserDTO {
    private final String name;
    private final String lastName;
    private final String email;
    private final Uri profilePictureUrl;

    public UserDTO(String name, String lastName, String email, Uri profilePictureUrl) {
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

    @Override
    public String toString() {
        return "UserDTO{" +
                "name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", profilePictureUrl='" + profilePictureUrl + '\'' +
                '}';
    }
}
