package tn.esprit.devops_project.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import tn.esprit.devops_project.entities.Operator;

import tn.esprit.devops_project.repositories.OperatorRepository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OperatorServiceImplTest {
    @InjectMocks
    private OperatorServiceImpl operatorService;

    @Mock
    private OperatorRepository operatorRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testRetrieveAllOperators() {
        // Arrange
        List<Operator> expectedOperators = new ArrayList<>();
        expectedOperators.add(new Operator(
                null,
                "SUP001",
                "Operator 1",
                "123",
                null
        ));
        when(operatorRepository.findAll()).thenReturn(expectedOperators);
        // Act
        List<Operator> actualOperators = operatorService.retrieveAllOperators();

        // Assert
        assertEquals(expectedOperators.size(), actualOperators.size());
    }
    @Test
    public void testAddOperator() {
        // Arrange
        Operator operatorToAdd = new Operator(null,
                "SUP001",
                "Operator 1",
                "123",
                null);
        when(operatorRepository.save(operatorToAdd)).thenReturn(operatorToAdd);
        // Act
        Operator addedOperator = operatorService.addOperator(operatorToAdd);
        // Assert
        assertEquals(operatorToAdd, addedOperator);
    }
    @Test
    void deleteOperator() {
        // Arrange
        Long operatorIdToDelete = 1L;

        // Act
        operatorService.deleteOperator(operatorIdToDelete);

        // Assert
        verify(operatorRepository, times(1)).deleteById(operatorIdToDelete);
    }

    @Test
    void updateOperator() {
        // Arrange
        Long operatorIdToUpdate = 1L; // Replace with the ID of the operator you want to update
        Operator existingOperator = new Operator(
                operatorIdToUpdate, // Use the existing operator's ID
                "SUP001", // Replace with the existing operator's fname
                "Operator 1", // Replace with the existing operator's lname
                "123", // Set password to null or initialize them if needed
                null // Set invoice to null or initialize them if needed
        );

        Operator updatedOperator = new Operator(
                operatorIdToUpdate, // Use the same ID for the existing operator
                "SUP002", // Replace with the updated fname
                "Updated Operator", // Replace with the updated lname
                "321", // Replace with the updated  password to null or initialize them if needed
               null // Set invoice to null
        );

        when(operatorRepository.findById(operatorIdToUpdate)).thenReturn(Optional.of(existingOperator));
        when(operatorRepository.save(updatedOperator)).thenReturn(updatedOperator);

        // Act
        Operator result = operatorService.updateOperator( updatedOperator);

// Assert
        assertEquals(updatedOperator, result);

    }

    @Test
    void retrieveOperator() {
        // Arrange
        Long operatorIdToRetrieve = 1L;
        Operator expectedOperator = new Operator(
                operatorIdToRetrieve,
                "SUP001",
                "Operator 1",

                null,
                null
        );
        when(operatorRepository.findById(operatorIdToRetrieve)).thenReturn(Optional.of(expectedOperator));
        // Act
        Operator retrievedOperator = operatorService.retrieveOperator(operatorIdToRetrieve);
        // Assert
        assertEquals(expectedOperator, retrievedOperator);
    }


}
