package com.sarny.spocone.server.gameControllers;

import com.sarny.spocone.server.game.GameInitializer;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author Wojciech Makiela
 */
public class RegistrationControllerTest {

    @Test
    public void testRegister_whenRegistered2Players_persistData() {
        // arrange
        ActiveGameInitializers gameInitializers = Mockito.spy(ActiveGameInitializers.class);
        RegistrationController registrationController = new RegistrationController(gameInitializers);

        // act
        registrationController.createRoom(1);
        registrationController.createRoom(2);

        // assert
        Mockito.verify(gameInitializers)
                .addNewActiveGameInitializer(Mockito.any(), Mockito.intThat(n -> n == 1), Mockito.intThat(n -> n == 2));
    }

    @Test
    public void testRegister_whenPassedNull_return400(){
        // arrange
        RegistrationController registrationController = new RegistrationController(null);

        // act
        ResponseEntity<Integer> responseEntity = registrationController.createRoom(null);
        // assert
        assertEquals(responseEntity.getStatusCodeValue(), 400);
    }
}