package tn.esprit.devops_project.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.devops_project.entities.ActivitySector;
import tn.esprit.devops_project.repositories.ActivitySectorRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class ActivitySectorImplTest {

    @InjectMocks
    private ActivitySectorImpl activitySectorService;

    @Mock
    private ActivitySectorRepository activitySectorRepository;

    @BeforeEach
    public void setUp () {
        MockitoAnnotations.openMocks (this);
    }

    @Test
    public void testRetrieveAllActivitySectors () {
        // Arrange
        List<ActivitySector> expectedActivitySectors = new ArrayList<> ();
        ActivitySector sector1 = new ActivitySector ();
        sector1.setIdSecteurActivite (1L);
        sector1.setCodeSecteurActivite ("Sector 1");
        sector1.setLibelleSecteurActivite ("Label 1");
        expectedActivitySectors.add (sector1);

        ActivitySector sector2 = new ActivitySector ();
        sector2.setIdSecteurActivite (2L);
        sector2.setCodeSecteurActivite ("Sector 2");
        sector2.setLibelleSecteurActivite ("Label 2");
        expectedActivitySectors.add (sector2);

        when (activitySectorRepository.findAll ()).thenReturn (expectedActivitySectors);

        // Act
        List<ActivitySector> actualActivitySectors = activitySectorService.retrieveAllActivitySectors ();

        // Assert
        assertEquals (expectedActivitySectors.size () , actualActivitySectors.size ());
    }


    @Test
    public void testAddActivitySector () {
        // Arrange
        ActivitySector activitySectorToAdd = new ActivitySector ();
        activitySectorToAdd.setCodeSecteurActivite ("New Sector");
        activitySectorToAdd.setLibelleSecteurActivite ("New Label");
        when (activitySectorRepository.save (activitySectorToAdd)).thenReturn (activitySectorToAdd);

        // Act
        ActivitySector addedActivitySector = activitySectorService.addActivitySector (activitySectorToAdd);
        // Assert
        assertEquals (activitySectorToAdd , addedActivitySector);
    }

    @Test
    void testUpdateActivitySector () {
        // Arrange
        Long activitySectorIdToUpdate = 1L;
        ActivitySector existingActivitySector = new ActivitySector ();
        existingActivitySector.setIdSecteurActivite (activitySectorIdToUpdate);
        existingActivitySector.setCodeSecteurActivite ("Sector 1");
        existingActivitySector.setLibelleSecteurActivite ("Label 1");
        ActivitySector updatedActivitySector = new ActivitySector ();
        updatedActivitySector.setIdSecteurActivite (activitySectorIdToUpdate);
        updatedActivitySector.setCodeSecteurActivite ("Updated Sector");
        updatedActivitySector.setLibelleSecteurActivite ("Updated Label");

        when (activitySectorRepository.findById (activitySectorIdToUpdate)).thenReturn (Optional.of (existingActivitySector));
        when (activitySectorRepository.save (updatedActivitySector)).thenReturn (updatedActivitySector);

        // Act
        ActivitySector result = activitySectorService.updateActivitySector (updatedActivitySector);

        // Assert
        assertEquals (updatedActivitySector , result);
    }

    @Test
    void testDeleteActivitySector () {
        // Arrange
        Long activitySectorIdToDelete = 1L;

        // Act
        activitySectorService.deleteActivitySector (activitySectorIdToDelete);

        // Assert
        verify (activitySectorRepository , times (1)).deleteById (activitySectorIdToDelete);
    }

    @Test
    void testRetrieveActivitySector () {
        // Arrange
        Long activitySectorIdToRetrieve = 1L;
        ActivitySector expectedActivitySector = new ActivitySector ();
        expectedActivitySector.setIdSecteurActivite (activitySectorIdToRetrieve);
        expectedActivitySector.setCodeSecteurActivite ("Sector 1");
        expectedActivitySector.setLibelleSecteurActivite ("Label 1");

        when (activitySectorRepository.findById (activitySectorIdToRetrieve)).thenReturn (Optional.of (expectedActivitySector));

        // Act
        ActivitySector retrievedActivitySector = activitySectorService.retrieveActivitySector (activitySectorIdToRetrieve);

        // Assert
        assertEquals (expectedActivitySector , retrievedActivitySector);
    }
}
