package guru.springframework.springmvccourse.controllers;

import guru.springframework.springmvccourse.domain.Address;
import guru.springframework.springmvccourse.domain.Customer;
import guru.springframework.springmvccourse.services.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by yriyMitsiuk on 31.05.2018.
 */
public class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void testGetAll() throws Exception {
        List<Customer> customers = Stream.generate(Customer::new).limit(3).collect(Collectors.toList());
        when(customerService.getAll()).thenReturn((List)customers);
        mockMvc.perform(get("/customer/customers"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer/customers"))
                .andExpect(model().attribute("customers", hasSize(3)))
                .andDo(print());
    }

    @Test
    public void testGet() throws Exception {
        Integer id = 1;
        when(customerService.get(id)).thenReturn(new Customer());
        mockMvc.perform(get("/customer/view/"+id))
                .andExpect(status().isOk())
                .andExpect(view().name("customer/view"))
                .andExpect(model().attribute("customer", instanceOf(Customer.class)))
                .andDo(print());
    }

    @Test
    public void testCreate() throws Exception {
        verifyZeroInteractions(customerService);
        mockMvc.perform(get("/customer/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer/customerform"))
                .andExpect(model().attribute("customer", instanceOf(Customer.class)))
                .andDo(print());
    }

    @Test
    public void testEdit() throws Exception {
        Integer id = 1;
        when(customerService.get(id)).thenReturn(new Customer());
        mockMvc.perform(get("/customer/edit/"+id))
                .andExpect(status().isOk())
                .andExpect(view().name("customer/customerform"))
                .andExpect(model().attribute("customer", instanceOf(Customer.class)))
                .andDo(print());
    }

    @Test
    public void testSaveOrUpdate() throws Exception {
        Integer id = 1;
        MultiValueMap<String, String> params = getParams();
        Customer returnCustomer = fillCustomer(params);
        returnCustomer.setId(id);

        when(customerService.saveOrUpdate(ArgumentMatchers.<Customer>any())).thenReturn(returnCustomer);

        mockMvc.perform(post("/customer")
                .param("id", "1").params(params))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/customer/view/"+id))
                .andExpect(model().attribute("customer", hasProperty("id", is(id))))
                .andExpect(model().attribute("customer", hasProperty("firstName", is(params.getFirst("firstName")))))
                .andExpect(model().attribute("customer", hasProperty("email", is(params.getFirst("email")))))
                .andDo(print());

        ArgumentCaptor<Customer> boundCustomer = ArgumentCaptor.forClass(Customer.class);
        verify(customerService).saveOrUpdate(boundCustomer.capture());

        assertEquals(id, boundCustomer.getValue().getId());
        assertEquals(params.getFirst("firstName"), boundCustomer.getValue().getFirstName());
        assertEquals(params.getFirst("email"), boundCustomer.getValue().getEmail());
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(get("/customer/delete/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/customer/customers"))
                .andDo(print());
    }

    private MultiValueMap<String, String> getParams() {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("firstName", "Alex");
        params.add("lastName", "Barchuk");
        params.add("email", "alexBarchuk@gmail.com");
        params.add("phoneNumber", "784.254.985");
        params.add("addressLine1", "1 Little Cuba Road");
        params.add("city", "Miami");
        params.add("state", "Florida");
        params.add("zipCode", "33101");
        return params;
    }

    private Customer fillCustomer(MultiValueMap<String, String> params) {
        Customer customer = new Customer();
        customer.setFirstName(params.getFirst("firstName"));
        customer.setLastName(params.getFirst("lastName"));
        customer.setEmail(params.getFirst("email"));
        customer.setPhoneNumber(params.getFirst("phoneNumber"));
        customer.setBillingAddress(new Address());
        customer.getBillingAddress().setAddressLine1(params.getFirst("addressLine1"));
        customer.getBillingAddress().setCity(params.getFirst("city"));
        customer.getBillingAddress().setState(params.getFirst("state"));
        customer.getBillingAddress().setZipCode(params.getFirst("zipCode"));
        return customer;
    }
}