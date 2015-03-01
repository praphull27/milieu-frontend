package com.example.elixir;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by manojthakur on 2/28/15.
 *
 * {
 *     "name": "name",
 *     "desc": "desc",
 *     "selected": {
 *          "name": "name",
 *          "category": "cat",
 *          "desc":, "desc",
 *          "location": {
 *              "lat":78,
 *              "lng":90
 *          }
 *      }
 *     }
 *
 */
public class MeetingBean implements Parcelable{

    public String id;
    public String name;
    public String desc;
    public SuggestionBean selected;
    public List<MemberBean> members;
    public List<SuggestionBean> suggestions;
    public Map<String, List<String>> map;
    public Categories cat;
    public List<MemberBean> invited;
    public MemberBean owner;

    public MeetingBean() {

    }

    public MeetingBean(Parcel p) {
        this.id=p.readString();
        this.name=p.readString();
        this.desc=p.readString();
        this.selected = p.readParcelable(SuggestionBean.class.getClassLoader());
        int limit = p.readInt();
        this.members = new ArrayList<MemberBean>();
        for(int i = 0; i < limit; i++) {
            MemberBean bean = p.readParcelable(MemberBean.class.getClassLoader());
            this.members.add(bean);
        }
        limit = p.readInt();
        this.suggestions = new ArrayList<SuggestionBean>();
        for(int i = 0; i < limit; i++) {
            SuggestionBean bean = p.readParcelable(SuggestionBean.class.getClassLoader());
            this.suggestions.add(bean);
        }
        this.map = p.readHashMap(String.class.getClassLoader());
        this.cat = Categories.valueOf(p.readString());
        this.invited = new ArrayList<MemberBean>();
        for(int i = 0; i < limit; i++) {
            MemberBean bean = p.readParcelable(MemberBean.class.getClassLoader());
            this.invited.add(bean);
        }
        this.owner = new MemberBean(p);
    }

    public MeetingBean(String id, String name, String desc, SuggestionBean selected, List<MemberBean> members, List<SuggestionBean> suggestions, Map<String, List<String>> map, Categories cat, List<MemberBean> invited, MemberBean owner) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.selected = selected;
        this.members = members;
        this.suggestions = suggestions;
        this.map = map;
        this.cat = cat;
        this.invited = invited;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public SuggestionBean getSelected() {
        return selected;
    }

    public List<MemberBean> getMembers() {
        return members;
    }

    public List<SuggestionBean> getSuggestions() {
        return suggestions;
    }

    public Map<String, List<String>> getMap() {
        return map;
    }

    public String getId() {
        return id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
       writeMeetingToParcel(dest, this);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public MeetingBean createFromParcel(Parcel in) {
            return getMeetingFromParcel(in);
        }
        public MeetingBean[] newArray(int size) {
            return new MeetingBean[size];
        }
    };
    /*
                 * {
                 *      "name": "name",
                 *      "category": "cat",
                 *      "desc":, "desc",
                 *      "location": {
                 *          "lat":78,
                 *          "lng":90
                 *      }
                 * }
                 */
    public static class SuggestionBean implements Parcelable{
        public String id;
        public Categories category;
        public String name;
        public LatLng loc;
        public String desc;
        public double rating;

        public SuggestionBean() {

        }

        public SuggestionBean(Parcel p) {

            this.id = p.readString();
            this.name = p.readString();
            this.desc = p.readString();
            this.category = Categories.valueOf(p.readString());
            this.loc = new LatLng(p.readDouble(),p.readDouble());
            this.rating = p.readDouble();
        }

        public SuggestionBean(String id, Categories category, String name, LatLng loc, String desc, double rating) {
            this.id = id;
            this.category = category;
            this.name = name;
            this.loc = loc;
            this.desc = desc;
            this.rating = rating;
        }

        public Categories getCategory() {
            return category;
        }

        public String getName() {
            return name;
        }

        public LatLng getLoc() {
            return loc;
        }

        public String getDesc() {
            return desc;
        }

        public String getId() {
            return id;
        }

        public double getRating() {
            return rating;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            writeSuggestionToParcel(dest, this);
        }

        public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
            public SuggestionBean createFromParcel(Parcel in) {
                return getSuggestionFromParcel(in);
            }
            public SuggestionBean[] newArray(int size) {
                return new SuggestionBean[size];
            }
        };
    }

    public static class MemberBean implements Parcelable {
        public String name;
        public String id;
        public String phone;
        public String email;
        public LatLng loc;

        public MemberBean() {

        }

        public MemberBean(Parcel p) {
            this.id= p.readString();
            this.name= p.readString();
            this.email= p.readString();
            this.phone= p.readString();
            this.loc = new LatLng(p.readDouble(), p.readDouble());
        }

        public MemberBean(String name, String id, String phone, String email, LatLng loc) {
            this.name = name;
            this.id = id;
            this.phone = phone;
            this.email = email;
            this.loc = loc;
        }

        public String getName() {
            return name;
        }

        public String getId() {
            return id;
        }

        public String getPhone() {
            return phone;
        }

        public String getEmail() {
            return email;
        }

        public LatLng getLoc() {
            return loc;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            writeMemberToParcel(dest, this);
        }

        public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
            public MemberBean createFromParcel(Parcel in) {
                return getMemberFromParcel(in);
            }
            public MemberBean[] newArray(int size) {
                return new MemberBean[size];
            }
        };
    }

    public static void writeMeetingToParcel(Parcel p, MeetingBean m) {
        p.writeString(m.getId());
        p.writeString(m.getName());
        p.writeString(m.getDesc());
        p.writeParcelable(m.getSelected(),1);
        p.writeInt(m.getMembers().size());
        for(MemberBean mem: m.getMembers())
            p.writeParcelable(mem, 1);
        p.writeInt(m.getSuggestions().size());
        for(SuggestionBean mem: m.getSuggestions())
            p.writeParcelable(mem, 1);
        p.writeMap(m.getMap());
        p.writeString(m.cat.toString());
        for(MemberBean mem: m.invited)
            p.writeParcelable(mem, 1);
        p.writeParcelable(m.owner, 1);
    }

    public static MeetingBean getMeetingFromParcel(Parcel p) {
        MeetingBean ret = new MeetingBean();
        ret.id=p.readString();
        ret.name=p.readString();
        ret.desc=p.readString();
        ret.selected = p.readParcelable(SuggestionBean.class.getClassLoader());
        int limit = p.readInt();
        ret.members = new ArrayList<MemberBean>();
        for(int i = 0; i < limit; i++) {
            MemberBean bean = p.readParcelable(MemberBean.class.getClassLoader());
            ret.members.add(bean);
        }
        limit = p.readInt();
        ret.suggestions = new ArrayList<SuggestionBean>();
        for(int i = 0; i < limit; i++) {
            SuggestionBean bean = p.readParcelable(SuggestionBean.class.getClassLoader());
            ret.suggestions.add(bean);
        }
        ret.map = p.readHashMap(String.class.getClassLoader());
        ret.cat = Categories.valueOf(p.readString());
        ret.invited = new ArrayList<MemberBean>();
        for(int i = 0; i < limit; i++) {
            MemberBean bean = p.readParcelable(MemberBean.class.getClassLoader());
            ret.invited.add(bean);
        }
        ret.owner = p.readParcelable(MemberBean.class.getClassLoader());
        return ret;
    }

    public static void writeMemberToParcel(Parcel p, MemberBean mem) {
        p.writeString(mem.getId());
        p.writeString(mem.getName());
        p.writeString(mem.getEmail());
        p.writeString(mem.getPhone());
        if(mem.getLoc() != null) {
            p.writeDouble(mem.getLoc().latitude);
            p.writeDouble(mem.getLoc().longitude);
        } else {
            p.writeDouble(-1);
            p.writeDouble(-1);
        }
    }

    public static MemberBean getMemberFromParcel(Parcel p) {
        MemberBean ret = new MemberBean();
        ret.id= p.readString();
        ret.name= p.readString();
        ret.email= p.readString();
        ret.phone= p.readString();
        ret.loc = new LatLng(p.readDouble(), p.readDouble());
        return ret;
    }

    public static void writeSuggestionToParcel(Parcel p, SuggestionBean sug) {
        p.writeString(sug.getId());
        p.writeString(sug.getName());
        p.writeString(sug.getDesc());
        p.writeString(sug.getCategory().toString());
        p.writeDouble(sug.getLoc().latitude);
        p.writeDouble(sug.getLoc().longitude);
        p.writeDouble(sug.getRating());
    }

    public static SuggestionBean getSuggestionFromParcel(Parcel p) {
        SuggestionBean ret = new SuggestionBean();
        ret.id = p.readString();
        ret.name = p.readString();
        ret.desc = p.readString();
        ret.category = Categories.valueOf(p.readString());
        ret.loc = new LatLng(p.readDouble(),p.readDouble());
        ret.rating = p.readDouble();
        return ret;
    }
}


