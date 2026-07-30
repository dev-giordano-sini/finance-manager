package it.financemanager.category;

import it.financemanager.common.exception.ConflictException;
import it.financemanager.common.exception.ResourceNotFoundException;
import it.financemanager.user.CurrentUserService;
import it.financemanager.user.Role;
import it.financemanager.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {
    @Mock private CategoryRepository repository;
    @Mock private CurrentUserService currentUser;
    private CategoryService service;
    private User user;

    @BeforeEach
    void setUp() {
        service = new CategoryService(repository, currentUser);
        user = new User("user@example.com", "hash", "User", Role.USER);
        when(currentUser.get()).thenReturn(user);
    }

    @Test
    void createNormalizesInputAndPersistsCategory() {
        when(repository.saveAndFlush(org.mockito.ArgumentMatchers.any(Category.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        CategoryResponse response = service.create(new CategoryRequest("  Groceries  ", "#a1b2c3"));

        ArgumentCaptor<Category> captor = ArgumentCaptor.forClass(Category.class);
        verify(repository).saveAndFlush(captor.capture());
        assertThat(captor.getValue().getName()).isEqualTo("Groceries");
        assertThat(captor.getValue().getColor()).isEqualTo("#A1B2C3");
        assertThat(response.name()).isEqualTo("Groceries");
    }

    @Test
    void createRejectsDuplicateNameBeforeWriting() {
        when(repository.existsByUserIdAndNameIgnoreCase(null, "Groceries")).thenReturn(true);

        assertThatThrownBy(() -> service.create(new CategoryRequest("Groceries", "#A1B2C3")))
                .isInstanceOf(ConflictException.class)
                .hasMessage("Category name already exists");
        verify(repository, never()).saveAndFlush(org.mockito.ArgumentMatchers.any());
    }

    @Test
    void getDoesNotExposeAnotherUsersCategory() {
        when(repository.findByIdAndUserId(42L, null)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.get(42L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Category with id 42 was not found");
    }
}
