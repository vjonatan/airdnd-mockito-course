package com.debuggeandoideas.airdnd.service;

import com.debuggeandoideas.airdnd.repositories.*;
import com.debuggeandoideas.airdnd.services.*;
import com.debuggeandoideas.airdnd.utils.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {

    @Mock
    private RoomRepository roomRepositoryMock;
    @InjectMocks
    private RoomService roomService;

    /*@BeforeEach
    void init(){
        roomRepositoryMock = mock(RoomRepository.class);
        roomService = new RoomService(roomRepositoryMock);
    }*/

    @Test
    @DisplayName("Should get all rooms available")
    void findAllAvailableRooms(){
        when(roomRepositoryMock.findAll())
                .thenReturn(Dummy.default_rooms);

        var expected = 4;
        var result = roomService.findAllAvailableRooms();

        Assertions.assertEquals(expected, result.size());
    }
}
