package com.debuggeandoideas.airdnd.utils;

import com.debuggeandoideas.airdnd.dto.*;

import java.util.*;

import static javax.swing.UIManager.*;

public class Dummy {

    public Dummy(){

    }

    public static final Map<RoomDto, Boolean> default_rooms = new HashMap<>() {{
        put(new RoomDto("A", 2), true);
        put(new RoomDto("B", 2), true);
        put(new RoomDto("C", 3), false);
        put(new RoomDto("D", 2), true);
        put(new RoomDto("E", 2), false);
        put(new RoomDto("F", 3), true);
    }};

    public static final List<RoomDto> default_rooms_list = new ArrayList<>() {{
        new RoomDto("A", 2);
        new RoomDto("B", 2);
        new RoomDto("C", 3);
        new RoomDto("D", 2);
        new RoomDto("E", 2);
        new RoomDto("F", 3);
    }};
}
