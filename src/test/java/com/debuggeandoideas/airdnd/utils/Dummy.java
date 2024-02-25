package com.debuggeandoideas.airdnd.utils;

import com.debuggeandoideas.airdnd.dto.*;

import java.time.*;
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
        add(new RoomDto("A", 2));
        add(new RoomDto("B", 0));
        add(new RoomDto("C", 2));
        add(new RoomDto("D", 0));
        add(new RoomDto("E", 0));
        add(new RoomDto("F", 3));
    }};

    public static final List<RoomDto> empty_rooms_list = new ArrayList<>() {{
        add(new RoomDto("A", 0));
    }};

    public static BookingDto bookingDto_1 = new BookingDto(
            "1.1",
            LocalDate.of(2023, 06, 10),
            LocalDate.of(2023, 06, 20),
            2,
            true
    );

    public static BookingDto bookingDto_2 = new BookingDto(
            "1.1",
            LocalDate.of(2023, 06, 10),
            LocalDate.of(2023, 06, 20),
            2,
            true
    );

    public static BookingDto bookingDto_3 = new BookingDto(
            "1.1",
            LocalDate.of(2023, 06, 10),
            LocalDate.of(2023, 06, 20),
            2,
            true
    );

    public static BookingDto bookingDto_4 = new BookingDto(
            "1.1",
            LocalDate.of(2023, 06, 10),
            LocalDate.of(2023, 06, 20),
            2,
            true
    );
}
