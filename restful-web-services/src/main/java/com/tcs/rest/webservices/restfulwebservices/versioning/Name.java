package com.tcs.rest.webservices.restfulwebservices.versioning;

public class Name {
	private String firstName;
	private String lastName;
	public Name(String firstName, String lasstName) {
		super();
		this.firstName = firstName;
		this.lastName = lasstName;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLasstName() {
		return lastName;
	}
	@Override
	public String toString() {
		return "Name [firstName=" + firstName + ", lasstName=" + lastName + "]";
	}
	
}
