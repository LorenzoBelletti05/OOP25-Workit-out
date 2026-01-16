package it.unibo.workitout.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import it.unibo.workitout.model.user.model.impl.ActivityLevel;
import it.unibo.workitout.model.user.model.impl.HarrisBenedictStrategy;
import it.unibo.workitout.model.user.model.impl.MifflinStJeorStrategy;
import it.unibo.workitout.model.user.model.impl.Sex;
import it.unibo.workitout.model.user.model.impl.UserGoal;
import it.unibo.workitout.model.user.model.impl.UserProfile;

public class BMRStrategyTest {

    private double delta = 0.001;
    
    @Test
    void testHarrisBenedictMale(){
        UserProfile maleUser = new UserProfile("Luca", "Bianchi", 30, 170, 80, Sex.MALE, ActivityLevel.MODERATE, UserGoal.MAINTAIN_WEIGHT);
        HarrisBenedictStrategy strategy = new HarrisBenedictStrategy();
        double expectedBMR = 1805.642;

        assertEquals(expectedBMR, strategy.calculateBMR(maleUser), delta);
    }

    @Test
    void testHarrisBenedictFemale(){
        UserProfile femaleUser = new UserProfile("Lucia", "Bianchi", 25, 165, 60, Sex.FEMALE, ActivityLevel.MODERATE, UserGoal.MAINTAIN_WEIGHT);
        HarrisBenedictStrategy strategy = new HarrisBenedictStrategy();
        double expectedBMR = 1405.333;

        assertEquals(expectedBMR, strategy.calculateBMR(femaleUser));
    }

    @Test
    void testMifflinMale(){
        UserProfile maleUser = new UserProfile("Luca", "Bianchi", 30, 170, 80, Sex.MALE, ActivityLevel.MODERATE, UserGoal.MAINTAIN_WEIGHT);
        MifflinStJeorStrategy strategy = new MifflinStJeorStrategy();
        double expectedBMR = 1717.5;
        
        assertEquals(expectedBMR, strategy.calculateBMR(maleUser));
    }

    @Test
    void testMifflinfemale(){
        UserProfile femaleUser = new UserProfile("Lucia", "Bianchi", 25, 165, 60, Sex.FEMALE, ActivityLevel.MODERATE, UserGoal.MAINTAIN_WEIGHT);
        MifflinStJeorStrategy strategy = new MifflinStJeorStrategy();
        double expectedBMR = 1345.25;
        
        assertEquals(expectedBMR, strategy.calculateBMR(femaleUser));
    }
}
