package com.jdk.optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author cheny.huang
 * @date 2018-12-29 11:04.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String mail;
    private Address address;

    public User(String mail) {
        this.mail = mail;
    }
    static class Address{
        private Country country;

        public Address(Country country) {
            this.country = country;
        }

        public Country getCountry() {
            return country;
        }

        public void setCountry(Country country) {
            this.country = country;
        }
    }

    static class Country {
        public Country(String code) {
            this.code = code;
        }

        private String code;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
