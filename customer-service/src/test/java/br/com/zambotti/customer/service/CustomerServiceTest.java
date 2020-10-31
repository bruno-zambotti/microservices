package br.com.zambotti.customer.service;

import br.com.zambotti.customer.advice.ResponseError;
import br.com.zambotti.customer.model.Address;
import br.com.zambotti.customer.model.Customer;
import br.com.zambotti.customer.model.Order;
import br.com.zambotti.customer.model.enums.AddressType;
import br.com.zambotti.customer.repository.CustomerRepository;
import br.com.zambotti.customer.service.impl.CustomerServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private CustomerServiceImpl service;

    @Before
    public void setupMock() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldCreateACustomerWithSuccess() {
        String name = "Name";
        String surname = "Surname";
        Date birthDate = new Date(1990, 12, 05, 10, 55, 12);
        Character gender = 'M';
        Set<String> phones = new HashSet<>(Arrays.asList("23325466", "23441122"));

        String street = "Some Street";
        Integer number = 1234;
        String complementet = "Nearby the XPTO's Supermarket";
        String postalCode = "00001-000";
        String city = "SAO PAULO";
        String province = "SAO PAULO";
        String country = "BRAZIL";
        Address address = new Address(street, number, complementet, postalCode, city, province, country, AddressType.ENTREGA);

        Customer customer = new Customer();

        customer.setName(name);
        customer.setSurname(surname);
        customer.setBirthDate(birthDate);
        customer.setGender(gender);
        customer.setPhones(phones);
        customer.setAddress(new HashSet<>(Arrays.asList(address)));

        when(customerRepository.save(customer)).thenReturn(customer);

        assertNotNull(this.service.createCustomer(customer));
    }

    @Test
    public void shouldUpdateACustomerWithSuccess() {
        Integer customerId = 1;
        String name = "Name";
        String surname = "Surname";
        Date birthDate = new Date(1990, 12, 05, 10, 55, 12);
        Character gender = 'M';
        Set<String> phones = new HashSet<>(Arrays.asList("23325466", "23441122"));

        String street = "Some Street";
        Integer number = 1234;
        String complementet = "Nearby the XPTO's Supermarket";
        String postalCode = "00001-000";
        String city = "SAO PAULO";
        String province = "SAO PAULO";
        String country = "BRAZIL";
        Address address = new Address(street, number, complementet, postalCode, city, province, country, AddressType.ENTREGA);

        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setName(name);
        customer.setSurname(surname);
        customer.setBirthDate(birthDate);
        customer.setGender(gender);
        customer.setPhones(phones);
        customer.setAddress(new HashSet<>(Arrays.asList(address)));

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));

        customer.setName("Other");
        customer.setSurname("Name");

        this.service.updateCustomer(customer);
    }

    @Test
    public void shouldThrowAExceptionWhenUpdateACustomerThatNotExists() {
        Integer customerId = 1;

        Customer customer = new Customer();
        customer.setId(customerId);

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        try {
            this.service.updateCustomer(customer);
        } catch (ResponseError error){
            Assert.assertEquals(HttpStatus.NOT_FOUND, error.getStatus());
            Assert.assertEquals("Cliente não encontrado", error.getMessage());
        }
    }

    @Test
    public void shouldGetAllStoredCustomersWithSuccess() {
        when(customerRepository.findAll()).thenReturn(Arrays.asList(new Customer(), new Customer()));
        Assert.assertEquals(2, this.service.getAllCustomer().size());
    }

    @Test
    public void shouldDeleteACustomerWithSuccess() {
        Integer id = 1;

        when(customerRepository.findById(id)).thenReturn(Optional.of(new Customer()));
        when(orderService.getAllOrders()).thenReturn(new ArrayList<>());

        this.service.deleteCustomer(id);
    }

    @Test
    public void shouldGetCustomerByIdWithSuccess() {
        Integer id = 1;

        when(customerRepository.findById(id)).thenReturn(Optional.of(new Customer()));

        assertNotNull(this.service.getCustomerById(id));
    }

    @Test
    public void shouldThrowAExceptionWhenDeleteACustomerThatNotExists() {
        Integer id = 1;

        when(customerRepository.findById(id)).thenReturn(Optional.empty());
        when(orderService.getAllOrders()).thenReturn(new ArrayList<>());

        try {
            this.service.deleteCustomer(id);
        } catch (ResponseError error){
            Assert.assertEquals(HttpStatus.NOT_FOUND, error.getStatus());
            Assert.assertEquals("Cliente não encontrado", error.getMessage());
        }
    }

    @Test
    public void shouldThrowAExceptionWhenDeleteACustomerWithAAssociatedOrder() {
        Integer customerId = 1;
        Integer orderId = 1;

        when(orderService.getAllOrders()).thenReturn(Arrays.asList(
                new Order(orderId, customerId, "NOVO", new HashSet<>())));

        try {
            this.service.deleteCustomer(customerId);
        } catch (ResponseError error){
            Assert.assertEquals(HttpStatus.PRECONDITION_FAILED, error.getStatus());
            Assert.assertEquals("O cliente informado possui ao menos um pedido de venda associado.",
                    error.getMessage());
        }
    }
}
