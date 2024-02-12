package com.debuggeandoideas.airdnd.utils;

import com.debuggeandoideas.airdnd.dto.*;

import java.util.*;

public class Dummy {

    public Dummy(){

    }

    public static final Map<RoomDto, Boolean> default_rooms = new HashMap<>() {{
        put(new RoomDto("1,1", 2), true);
        put(new RoomDto("1,2", 2), true);
        put(new RoomDto("1,3", 3), false);
        put(new RoomDto("2,1", 2), true);
        put(new RoomDto("2,2", 2), false);
        put(new RoomDto("2,3", 3), true);
    }};
}
