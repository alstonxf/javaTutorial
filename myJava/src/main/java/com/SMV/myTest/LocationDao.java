package com.SMV.myTest;

import java.util.HashMap;
import java.util.List;

public interface LocationDao {
    //查询所有用户地址
    List<Location> getLocationList();
    
    //根据ID查询用户地址
    Location getLocationById(Location Location);
    //新增用户地址
    int AddLocation(Location Location);
    //根据ID修改用户地址
    int UpdateLocationById(Location Location);
    //根据用户地址ID清除用户地址
    int DeleteLocationById(Location Location);

    //根据ID查询用户地址
    Location getLocationById2(HashMap<String,Object> map);

    Location getLocationByName(HashMap<String,Object> map);

    Location getLocationByNameOrId(HashMap<String,Object> map);
}