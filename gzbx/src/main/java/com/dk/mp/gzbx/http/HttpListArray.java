package com.dk.mp.gzbx.http;

import com.dk.mp.gzbx.entity.Malfunction;
import com.dk.mp.gzbx.entity.Person;
import com.dk.mp.gzbx.entity.ProcessInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cobb on 2017/8/22.
 */

public class HttpListArray {

    /**
     * 获取报修信息
     * @return
     */
    public static Map<String,Object> getMyMalfunction(JSONObject result){
        Map<String,Object> mo = new HashMap<String, Object>();
        List<Malfunction> list = new ArrayList<Malfunction>();
        try {
            if(result != null){
                JSONArray ja = result.getJSONObject("data").getJSONArray("list");
                for(int i = 0;i<ja.length();i++){
                    Malfunction m = new Malfunction();
                    JSONObject jo = ja.getJSONObject(i);
                    m.setName(jo.getString("name"));
                    m.setTitle(jo.getString("name"));
                    m.setId(jo.getString("id"));
                    m.setDevice(jo.getString("sb"));
                    m.setAddress(jo.getString("dd"));
                    m.setDes(jo.getString("desc"));
                    m.setStatusname(jo.getString("status"));
                    m.setTime(jo.getString("time"));
                    List<String> tps = new ArrayList<>();
                    JSONArray jaa = jo.optJSONArray("tps");
                    if(jaa != null) {
                        for (int j = 0; j < jaa.length(); j++) {
                            tps.add((String) jaa.get(j));
                        }
                        m.setTps(tps);
                    }
                    list.add(m);
                }
                mo.put("list", list);
                mo.put("currentPage", result.getJSONObject("data").get("currentPage"));
                mo.put("nextPage", result.getJSONObject("data").get("nextPage"));
                mo.put("totalPages", result.getJSONObject("data").get("totalPages"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mo;
    }

    /**
     * 待我审批的数据
     * @return
     */
    public static Map<String,Object> getAwaitingApproval(JSONObject json){
        Map<String,Object> mo = new HashMap<String, Object>();
        List<Malfunction> list = new ArrayList<Malfunction>();
        try {
            if(json != null){
                JSONArray ja = json.getJSONObject("data").getJSONArray("list");
                for(int i = 0;i<ja.length();i++){
                    Malfunction m = new Malfunction();
                    JSONObject jo = ja.getJSONObject(i);
                    m.setName(jo.getString("name"));
                    m.setTitle(jo.getString("name"));
                    m.setId(jo.getString("id"));
                    m.setDevice(jo.getString("sb"));
                    m.setAddress(jo.getString("dd"));
                    m.setDes(jo.getString("desc"));
                    m.setStatusname(jo.getString("status"));
                    m.setTime(jo.getString("time"));
                    List<String> tps = new ArrayList<>();
                    JSONArray jaa = jo.optJSONArray("tps");
                    if(jaa != null) {
                        for (int j = 0; j < jaa.length(); j++) {
                            tps.add((String) jaa.get(j));
                        }
                        m.setTps(tps);
                    }
                    list.add(m);
                }
                mo.put("list", list);
                mo.put("currentPage", json.getJSONObject("data").get("currentPage"));
                mo.put("nextPage", json.getJSONObject("data").get("nextPage"));
                mo.put("totalPages", json.getJSONObject("data").get("totalPages"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mo;
    }

    /**
     * 获取已处理审批流程
     * @return
     */
    public static Map<String,Object> getSplc(JSONObject json){
        Map<String,Object> oMap = new HashMap<String, Object>();
        List<ProcessInfo> list = new ArrayList<ProcessInfo>();
        try {
            if(json != null){
                JSONArray ja = json.getJSONObject("data").getJSONArray("list");
                for(int i=0;i<ja.length();i++){
                    JSONObject jo = ja.getJSONObject(i);
                    ProcessInfo pinfo = new ProcessInfo();
                    pinfo.setId(jo.getString("id"));
                    pinfo.setName(jo.getString("name"));
                    pinfo.setTime(jo.getString("time"));
                    pinfo.setStatusname(jo.getString("status"));
                    pinfo.setRepairstatus(jo.getString("beizhu"));
                    pinfo.setStatus("1");
                    pinfo.setLc(jo.getString("lc"));
                    list.add(pinfo);
                }
                oMap.put("pinfo", list);
                oMap.put("operation", json.getJSONObject("data").get("operation"));
                oMap.put("operateState", json.getJSONObject("data").get("operateState"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return oMap;
    }

    /**
     * 获取联系人列表
     * @return
     */
    public static List<Person> getPersons(JSONObject json){
        List<Person> list = new ArrayList<Person>();
        try {
            if(json != null){
                JSONArray ja = json.getJSONArray("data");
                for(int i=0;i<ja.length();i++){
                    Person p = new Person();
                    JSONObject jo = ja.getJSONObject(i);
                    p.setId(jo.getString("id"));
                    p.setName(jo.getString("name"));
                    list.add(p);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
