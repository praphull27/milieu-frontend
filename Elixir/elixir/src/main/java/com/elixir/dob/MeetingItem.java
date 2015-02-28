package com.elixir.dob;

import java.util.Date;

public class MeetingItem {
	
	public final int image;
	public final String name;
	public String location;
	public Date time;
	
	public MeetingItem(int image, String name, String degree,
                       Date time) {
		super();
		this.image = image;
		this.name = name;
		this.location = degree;
		this.time = time;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public int getImage() {
		return image;
	}

	public String getName() {
		return name;
	}
}
