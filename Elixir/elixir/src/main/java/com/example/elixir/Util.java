package com.example.elixir;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by manojthakur on 2/28/15.
 */
public class Util {

    public static MeetingBean getMeetingGivenJSON(JSONObject json) throws JSONException {

        String name = json.getString("name");
        String id = json.getString("_id");
        String desc = json.getString("desc");
        Categories cat = Categories.valueOf(json.getString("category"));
        JSONObject selected = json.optJSONObject("selected");
        String ownerJson = json.getString("owner");
        JSONArray suggestions = json.getJSONArray("suggestions");
        JSONArray members = json.getJSONArray("membersJoined");
        JSONArray invited = json.getJSONArray("membersInvited");
        List<MeetingBean.SuggestionBean> sugBeans = new ArrayList<>();
        List<MeetingBean.MemberBean> memBeans = new ArrayList<>();
        List<MeetingBean.MemberBean> invBeans = new ArrayList<>();

        MeetingBean.SuggestionBean sel = selected == null? null : getSuggestionGivenJSON(selected);

        Map<String, MeetingBean.MemberBean> map = new HashMap<>();

        for(int i = 0 ; i < invited.length(); i++) {
            MeetingBean.MemberBean bean = getMemberGivenJSON(invited.getJSONObject(i));
            invBeans.add(bean);
            map.put(bean.getId(), bean);
        }

        for(int i = 0 ; i < members.length(); i++) {
            MeetingBean.MemberBean bean = getMemberGivenJSON(members.getJSONObject(i));
            memBeans.add(bean);
            map.put(bean.getId(), bean);
        }


        for(int i = 0 ; i < suggestions.length(); i++) {
            sugBeans.add(getSuggestionGivenJSON(suggestions.getJSONObject(i)));
        }

        JSONObject mapping = json.optJSONObject("mapping");
        Map<String, List<String>> m = new HashMap<>();

        if(mapping != null) {
            Iterator<String> itr = mapping.keys();
            while (itr.hasNext()) {
                String str = itr.next();
                JSONArray jarr = mapping.getJSONArray(str);
                List<String> lst = new ArrayList<>();
                for (int i = 0; i < jarr.length(); i++) {
                    lst.add(jarr.getString(i));
                }
                m.put(str, lst);
            }
        }
        MeetingBean.MemberBean owner = map.get(ownerJson);
        return new MeetingBean(id,name,desc,sel,memBeans,sugBeans,m, cat, invBeans,owner);
    }

    public static MeetingBean.MemberBean getMemberGivenJSON(JSONObject json) throws JSONException {

        String name = json.optString("name");
        String id = json.getString("id");
        String email = json.optString("email");
        String phone = json.optString("phoneNumber");
        LatLng loc = null;
        if(json.has("loc"))
            email = json.getString("loc");
        else if(json.has("lat") && json.has("lng")) {
            loc = new LatLng(json.getDouble("lat"),json.getDouble("lng"));
        }
        return new MeetingBean.MemberBean(name,id,phone,email,loc);
    }

    public static MeetingBean.SuggestionBean getSuggestionGivenJSON(JSONObject json) throws JSONException {

        String name = json.getString("name");
        String id = json.getString("id");
        String desc = json.getString("desc");
        double rating = json.getDouble("rating");
        Categories cat = Categories.valueOf(json.getString("category"));
        LatLng loc = new LatLng(json.getJSONObject("loc").getDouble("lat"),json.getJSONObject("loc").getDouble("lng"));

        return new MeetingBean.SuggestionBean(id,cat,name,loc,desc, rating);
    }

    public static List<MeetingBean> getDummy() {
       List<MeetingBean> ret = new ArrayList<>();
       List<LatLng> locs = new ArrayList<>();

       locs.add(new LatLng(33.9425, -118.4081));
       locs.add(new LatLng(34.0219, -118.4814));
       locs.add(new LatLng(34.0300, -118.7500));
       locs.add(new LatLng(33.9908, -118.4592));
       locs.add(new LatLng(34.0722, -118.4441));


        locs.add(new LatLng(34.073620, -118.400356));
        locs.add(new LatLng(34.063053, -118.359212));
        locs.add(new LatLng(34.021122, -118.396466));
        locs.add(new LatLng(34.147785, -118.144516));
        locs.add(new LatLng(34.148972, -118.451357));

       List<MeetingBean.SuggestionBean> sugg = new ArrayList<>();
       for(int i = 0 ; i < 5; i++) {
          Categories cat = Categories.values()[i];
          sugg.add(new MeetingBean.SuggestionBean(""+i,cat,"sugname-"+i,locs.get(i),"sugdesc-"+i,9));
       }

        List<MeetingBean.MemberBean> mems = new ArrayList<>();
        for(int i = 0 ; i < 5; i++) {
            mems.add(new MeetingBean.MemberBean("user-"+i, i+"", "3109894752", "user-"+i+"@gmail.com", locs.get(i+5)));
        }

       for(int i = 0; i < 5; i++) {
           String id = i+"";
           String mname = "mname-"+i;
           String mdesc = "mdesc-"+i;
           Categories cat = Categories.values()[i];
           MeetingBean.SuggestionBean suggested = sugg.get(i);
            Map<String, List<String>> map = new HashMap<>();
           for(int j = 0 ; j < 5; j++) {
               List<String> arrList = new ArrayList<>();
               for(int k = 0 ; k < 5; k++) {
                   arrList.add(mems.get(k).getId());
               }
               map.put(sugg.get(j).getId(), arrList);
           }

           //ret.add(new MeetingBean(id,mname,mdesc, suggested, mems, sugg, map, Categories.ALL, mems));

       }
       return ret;
    }
}
