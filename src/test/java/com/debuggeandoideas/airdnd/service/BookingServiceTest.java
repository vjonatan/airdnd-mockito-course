package com.debuggeandoideas.airdnd.service;

import com.debuggeandoideas.airdnd.helpers.*;
import com.debuggeandoideas.airdnd.repositories.*;
import com.debuggeandoideas.airdnd.services.*;
import com.debuggeandoideas.airdnd.utils.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

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
}
