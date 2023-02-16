package com.example.demo.controllers;

import com.example.demo.entities.User;
import com.example.demo.services.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes ={ UserController.class})
@WebMvcTest(value = UserController.class,  excludeAutoConfiguration = {SecurityAutoConfiguration.class})
class UserControllerTest {
    @MockBean
    private UserServiceImpl userService;

   @Autowired
    private MockMvc mockMvc;

    @Autowired
   private WebApplicationContext webApplicationContext;

 /* @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }*/
    @Test

    void verifyDeletedUser() throws Exception {
        User user1 = new User();
        user1.setEmail("email");

        Mockito.when(userService.deleteUser("email")).thenReturn(null);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/delete/{email}","email"))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/admin/all"));
    }

    @Test
    void verifyEditForm() throws Exception {
        User user1 = new User();
        user1.setId(1);

        Mockito.when(userService.getUserById(1L)).thenReturn(user1);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/editUser/{id}",1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("edit_user"))
                .andExpect(MockMvcResultMatchers.model().attribute("user",user1));
    }

    @Test
    void VerifyUpdatedUser() throws Exception {
        User user = new User();
        this.mockMvc.perform(MockMvcRequestBuilders.post("/admin/update/{id}",1L).sessionAttr("user",user))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/admin/all"));
    }

   /* @Test
    @WithAnonymousUser
    void verifyEditOwnForm() throws Exception {
    User user1= new User();
        user1.setId(1);
        SecurityUser user = new SecurityUser(new User());
      // user1.setId(1);
        //Authentication auth = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Mockito.when(userService.getUserById(1L)).thenReturn(user.getUser());

        this.mockMvc.perform(MockMvcRequestBuilders.get("/viewOwnDetails"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("view_user_details"))
                .andExpect(MockMvcResultMatchers.model().attribute("user",user.getUser()));
    }
*/


    @Test
    void VerifyEditDetails() throws Exception {
        User user1 = new User();
        user1.setId(1);

       Mockito.when(userService.getUserById(1L)).thenReturn(user1);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/editDetails/{id}",1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("edit_own_details"))
                .andExpect(MockMvcResultMatchers.model().attribute("user",user1));
    }

    @Test
    void verifyIfGetAllUsers() throws Exception {

        User user1 = new User();
        user1.setId(1);
        User user2= new User();
        user2.setId(2);
        List<User> input = new ArrayList<>();
        input.add(user1);
        input.add(user2);

        Mockito.when(userService.getAllUsers()).thenReturn(List.of(user1,user2));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/admin/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("viewUsers"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("users"))
                .andExpect(MockMvcResultMatchers.model().attribute("users",input));

    }

    @Test
    void verifyIfPhoneAndEmailUpdated() throws Exception {
        User user = new User();
        this.mockMvc.perform(MockMvcRequestBuilders.post("/phoneAndEmail/{id}",1L).sessionAttr("user",user))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/viewOwnDetails"));

    }

    @Test
    void verifyHandleResourceNotFoundException() {
       /* UserController userController = new UserController();
        ResourceNotFoundException resourceNotFoundException= new ResourceNotFoundException("1","2","3");
Exception exception = assertThrows(RuntimeException.class, () -> userController.handleResourceNotFoundException(resourceNotFoundException));

        String expectedMessage = "1 not found with 2 : 3";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

        */
    }


}