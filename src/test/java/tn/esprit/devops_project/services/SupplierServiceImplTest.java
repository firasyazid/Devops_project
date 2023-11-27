package tn.esprit.devops_project.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.devops_project.entities.Supplier;
import tn.esprit.devops_project.entities.SupplierCategory;
import tn.esprit.devops_project.repositories.SupplierRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SupplierServiceImplTest {

    @InjectMocks
    private SupplierServiceImpl supplierService;

    @Mock
    private SupplierRepository supplierRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRetrieveAllSuppliers() {
        // Arrange
        List<Supplier> expectedSuppliers = new ArrayList<>();
        expectedSuppliers.add(new Supplier(
                null,
                "SUP001",
                "Supplier 1",
                SupplierCategory.ORDINAIRE,
                null,
                null
        ));
        when(supplierRepository.findAll()).thenReturn(expectedSuppliers);
        // Act
        List<Supplier> actualSuppliers = supplierService.retrieveAllSuppliers();

        // Assert
        assertEquals(expectedSuppliers.size(), actualSuppliers.size());
    }

    @Test
    public void testAddSupplier() {
        // Arrange
        Supplier supplierToAdd = new Supplier(null,
                "SUP001",
                "Supplier 1",
                SupplierCategory.ORDINAIRE,
                null,
                null);
        when(supplierRepository.save(supplierToAdd)).thenReturn(supplierToAdd);
        // Act
        Supplier addedSupplier = supplierService.addSupplier(supplierToAdd);
        // Assert
        assertEquals(supplierToAdd, addedSupplier);
    }

    @Test
    void updateSupplier() {
        // Arrange
        Long supplierIdToUpdate = 1L; // Replace with the ID of the supplier you want to update
        Supplier existingSupplier = new Supplier(
                supplierIdToUpdate, // Use the existing supplier's ID
                "SUP001", // Replace with the existing supplier's code
                "Supplier 1", // Replace with the existing supplier's name or label
                SupplierCategory.ORDINAIRE, // Replace with the existing supplier's category
                null, // Set invoices to null or initialize them if needed
                null // Set activitySectors to null or initialize them if needed
        );

        Supplier updatedSupplier = new Supplier(
                supplierIdToUpdate, // Use the same ID for the existing supplier
                "SUP002", // Replace with the updated code
                "Updated Supplier", // Replace with the updated name or label
                SupplierCategory.CONVENTIONNE, // Replace with the updated category
                null, // Set invoices to null or initialize them if needed
                null // Set activitySectors to null or initialize them if needed
        );

        when(supplierRepository.findById(supplierIdToUpdate)).thenReturn(Optional.of(existingSupplier));
        when(supplierRepository.save(updatedSupplier)).thenReturn(updatedSupplier);

        // Act
        Supplier result = supplierService.updateSupplier(updatedSupplier);

        // Assert
        assertEquals(updatedSupplier, result);
    }

    @Test
    void deleteSupplier() {
        // Arrange
        Long supplierIdToDelete = 1L;

        // Act
        supplierService.deleteSupplier(supplierIdToDelete);

        // Assert
        verify(supplierRepository, times(1)).deleteById(supplierIdToDelete);
    }

    @Test
    void retrieveSupplier() {
        // Arrange
        Long supplierIdToRetrieve = 1L;
        Supplier expectedSupplier = new Supplier(
                supplierIdToRetrieve,
                "SUP001",
                "Supplier 1",
                SupplierCategory.ORDINAIRE,
                null,
                null
        );
        when(supplierRepository.findById(supplierIdToRetrieve)).thenReturn(Optional.of(expectedSupplier));
        // Act
        Supplier retrievedSupplier = supplierService.retrieveSupplier(supplierIdToRetrieve);
        // Assert
        assertEquals(expectedSupplier, retrievedSupplier);
    }
}