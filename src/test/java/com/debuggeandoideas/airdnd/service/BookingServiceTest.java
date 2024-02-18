package com.debuggeandoideas.airdnd.service;

import com.debuggeandoideas.airdnd.dto.*;
import com.debuggeandoideas.airdnd.helpers.*;
import com.debuggeandoideas.airdnd.repositories.*;
import com.debuggeandoideas.airdnd.services.*;
import com.debuggeandoideas.airdnd.utils.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.junit.jupiter.api.function.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import java.util.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {
    @Mock
    private PaymentService paymentServiceMock;
    @Mock
    private RoomService roomServiceMock;
    @Mock
    private BookingRepository bookingRepositoryMock;
    @Mock
    private MailHelper mailHelperMock;
    @InjectMocks
    private BookingService bookingService;

    @Test
    @DisplayName("get getAvailablePlaceCounts should works")
    void getAvailablePlaceCounts(){

        when(roomServiceMock.findAllAvailableRooms())
                .thenReturn(Dummy.default_rooms_list);

        var expected = 0;
        var result = bookingService.getAvailablePlaceCount();

        Assertions.assertEquals(expected, result);

    }

    @Test
    @DisplayName("get booking should works")
    void booking(){

        var roomID = UUID.randomUUID().toString();

        when(roomServiceMock.findAvailableRoom(Dummy.bookingDto_1))
                .thenReturn(Dummy.default_rooms_list.get(0));

        when(bookingRepositoryMock.save(Dummy.bookingDto_1))
                .thenReturn(roomID);

        var result = bookingService.booking(Dummy.bookingDto_1);

        Assertions.assertEquals(roomID, result);

    }

    @Test
    @DisplayName("get booking with doReturn")
    void booking_doReturn(){

        var roomID = UUID.randomUUID().toString();

        doReturn(Dummy.default_rooms_list.get(0))
                .when(roomServiceMock).findAvailableRoom(Dummy.bookingDto_2);

        //Si ocurre alguna excepcion, va a continuar como si no hubiera ocurrido
        doReturn(roomID)
                .when(bookingRepositoryMock).save(Dummy.bookingDto_2);

        doNothing()
                .when(roomServiceMock).bookRoom(anyString());

        var result = bookingService.booking(Dummy.bookingDto_2);

        verify(roomServiceMock).findAvailableRoom(any(BookingDto.class));
        verify(bookingRepositoryMock).save(any(BookingDto.class));
        verify(roomServiceMock).bookRoom(anyString());

        Assertions.assertEquals(roomID, result);

    }

    @Test
    @DisplayName("get booking with doReturn")
    void booking_doReturn_UnHappy(){

        var roomID = UUID.randomUUID().toString();

        doReturn(Dummy.default_rooms_list.get(0))
                .when(roomServiceMock).findAvailableRoom(Dummy.bookingDto_4);

        //Agrego test para probar exception
        doThrow(new IllegalArgumentException("Max 3 guest"))
                .when(paymentServiceMock).pay(any(BookingDto.class), anyDouble());

        Executable executable = ()-> bookingService.booking(Dummy.bookingDto_4);

        //assertThrows para comparar excepciones
        Assertions.assertThrows(IllegalArgumentException.class, executable);

    }
}
