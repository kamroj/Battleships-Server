package com.sarny.spocone.server.game;

import com.sarny.spocone.publicclasses.shot.Shot;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

/**
 * @author Kamil Rojek
 */
public class ComputerEasyTest {

    private AI computer;

    @BeforeMethod
    public void setUp() {
        computer = new ComputerEasy(AI.generateID());
    }

    @Test(invocationCount = 200)
    public void testGenerateShot_returnFieldToFireInRange0To99(){
        //Act
        Shot shot = computer.generateShot();

        //Assert
        assertTrue(shot.getField() >= 0 && shot.getField() < 100);
    }

    @Test(invocationCount = 10)
    public void testGetID_returnID(){
        //Act
        Integer id = computer.getID();

        //Assert
        assertNotNull(id);
    }

    @Test(invocationCount = 20)
    public void testGenerateShot_returnShot(){
        //Act
        Shot shot = computer.generateShot();

        //Assert
        assertNotNull(shot);
    }
}