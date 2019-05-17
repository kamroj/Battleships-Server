package com.sarny.spocone.server.game_controllers;

import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.testng.Assert.assertEquals;

/**
 * @author Wojciech Makiela
 */
public class RegistrationControllerTest {

    @Test
    public void testRegister_whenRegistered2Players_persistData() {
        // arrange
        ActiveGameInitializers gameInitializers = Mockito.spy(ActiveGameInitializers.class);
        Rooms rooms = new Rooms();
        RegistrationController registrationController = new RegistrationController(gameInitializers, rooms);

        // act
        registrationController.createRoom(1);
        registrationController.joinRoom(2, 0);

        // assert
        Mockito.verify(gameInitializers)
                .addNewActiveGameInitializer(Mockito.any(), Mockito.intThat(n -> n == 1), Mockito.intThat(n -> n == 2));
    }

    @Test
    public void testRegister_whenPassedNull_return400() {
        // arrange
        RegistrationController registrationController = new RegistrationController(null, null);

        // act
        ResponseEntity<Integer> responseEntity = registrationController.createRoom(null);
        // assert
        assertEquals(responseEntity.getStatusCodeValue(), 400);
    }

    @Test
    public void testPlayVersusAi_whenPassedValidId_createNewGameVsAi() {
        // arrange
        ActiveGameInitializers gameInitializers = Mockito.spy(ActiveGameInitializers.class);
        Rooms rooms = new Rooms();
        RegistrationController registrationController = new RegistrationController(gameInitializers, rooms);

        // act
        registrationController.playVersusAi(10);

        // assert
        Mockito.verify(gameInitializers)
                .addNewActiveGameInitializer(Mockito.any(), Mockito.intThat(n -> n == 10), Mockito.anyInt());
    }

    @Test
    public void testPlayVersusAi_whenPassedNull_return400() {
        // arrange
        RegistrationController registrationController = new RegistrationController(null, null);

        // act
        ResponseEntity<Integer> responseEntity = registrationController.playVersusAi(null);

        // assert
        assertEquals(responseEntity.getStatusCodeValue(), 400);
    }

    @Test(invocationCount = 100)
    public void testCreatingRoom_when10000ThreadsCreatesRoom() throws InterruptedException {
        //Arrange
        final int NUMBER_OF_THREADS = 100;
        Rooms rooms = new Rooms();
        List<Thread> fakeRegisterCalls = new LinkedList<>();
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            Thread thread = new Thread(new RegisterTest(rooms), "Fake register " + i);
            fakeRegisterCalls.add(thread);
        }

        //Act
        for (Thread fakeRegisterCall : fakeRegisterCalls) {
            fakeRegisterCall.start();
        }
        for (Thread fakeRegisterCall : fakeRegisterCalls) {
            fakeRegisterCall.join();
        }

        int numberOfRooms = rooms.getRoomId();

        //Assert
        assertEquals(numberOfRooms, NUMBER_OF_THREADS);
    }

    class RegisterTest implements Runnable{
        private Rooms rooms;

        RegisterTest(Rooms rooms) {
            this.rooms = rooms;
        }

        @Override
        public void run() {
            rooms.register(ThreadLocalRandom.current().nextInt(0, 999999999));
        }
    }
}