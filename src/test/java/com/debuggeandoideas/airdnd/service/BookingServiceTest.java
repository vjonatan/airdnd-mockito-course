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
    @Spy
    private BookingRepository bookingRepositoryMock;
    @Mock
    private MailHelper mailHelperMock;
    @InjectMocks
    private BookingService bookingService;
    @Captor
    private ArgumentCaptor<String> stringCapture;

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

    @Test
    @DisplayName("get booking with doReturn")
    void booking_doReturn_SPY(){

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
    @DisplayName("get getAvailablePlaceCounts should works whith multiple returns")
    void getAvailablePlaceCounts_withMultipleReturns(){

        when(roomServiceMock.findAllAvailableRooms())
                .thenReturn(Dummy.default_rooms_list)
                .thenReturn(Dummy.empty_rooms_list);

        var expected = 7;
        var expected2 = 0;

        var result = bookingService.getAvailablePlaceCount();
        var result2 = bookingService.getAvailablePlaceCount();

        Assertions.assertAll(
                ()->Assertions.assertEquals(expected, result),
                ()->Assertions.assertEquals(expected2, result2)
        );
    }

    @Test
    @DisplayName("test unbook() with methods void")
    void unbook(){

        var id1 = "id1";
        var id2 = "id2";

        var bookingRes1 = Dummy.bookingDto_3;
        bookingRes1.setRoom(Dummy.default_rooms_list.get(1));

        var bookingRes2 = Dummy.bookingDto_3;
        bookingRes2.setRoom(Dummy.default_rooms_list.get(2));

        when(bookingRepositoryMock.findById(anyString()))
                .thenReturn(bookingRes1)
                .thenReturn(bookingRes2);

        doNothing()
                .when(roomServiceMock).unbookRoom(anyString());

        doNothing()
                .when(bookingRepositoryMock).deleteById(anyString());

        bookingService.unbook(id1);
        bookingService.unbook(id2);

        verify(roomServiceMock, times(2)).unbookRoom(anyString());
        verify(bookingRepositoryMock, times(2)).deleteById(anyString());
        verify(bookingRepositoryMock, times(2)).findById(stringCapture.capture());

        System.out.printf("capture: " + stringCapture.getAllValues());

        Assertions.assertEquals(List.of(id1, id2), stringCapture.getAllValues());
    }

    @Test
    @DisplayName("currencyConverter should works")
    void currencyConverter(){
        try(MockedStatic<CurrencyConverter> mockStatic = mockStatic(CurrencyConverter.class)){
            var expected = 900.0;

            mockStatic.when(() -> CurrencyConverter.toMx(anyDouble()))
                    .thenReturn(expected);

            var response = bookingService.calculateInMxn(Dummy.bookingDto_1);

            Assertions.assertEquals(expected, response);
        }
    }
}
