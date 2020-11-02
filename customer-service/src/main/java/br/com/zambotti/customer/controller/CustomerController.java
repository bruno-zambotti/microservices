package br.com.zambotti.customer.controller;

import br.com.zambotti.customer.dto.AddressDTO;
import br.com.zambotti.customer.dto.CustomerRequest;
import br.com.zambotti.customer.dto.CustomerResponse;
import br.com.zambotti.customer.dto.PhoneDTO;
import br.com.zambotti.customer.model.Address;
import br.com.zambotti.customer.model.Customer;
import br.com.zambotti.customer.model.enums.AddressType;
import br.com.zambotti.customer.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static br.com.zambotti.customer.utils.UtilMethods.toDate;
import static br.com.zambotti.customer.utils.UtilMethods.toDateString;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/microservices/v1/customer")
@Api(value = "Gerenciamento de Clientes")
public class CustomerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    private CustomerService customerService;

    @Autowired
    public CustomerController(final CustomerService customerService){
        this.customerService = customerService;
    };

    @ApiOperation(value = "Consultar todos os clientes")
    @GetMapping(produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<List<CustomerResponse>> getAllCustomers(){
        LOGGER.info("Getting all customers ... ");
        List<Customer> customers = customerService.getAllCustomer();
        List<CustomerResponse> customerResponse = new ArrayList<>();
        customers.forEach(customer -> customerResponse.add(
                getCustomerResponse(customer)));

        return ResponseEntity.ok(customerResponse);
    }

    @ApiOperation(value = "Consultar um cliente específico")
    @GetMapping(value = "/{id}", produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable("id") Integer id){
        LOGGER.info("Getting a specific customer ... ");
        Customer customerResponse = customerService.getCustomerById(id);

        return ResponseEntity.ok(getCustomerResponse(customerResponse));
    }

    @ApiOperation(value = "Excluir um cliente")
    @DeleteMapping(value = "/{id}", produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<?> deleteCustomer(@PathVariable("id") Integer id){
        LOGGER.info("Deleting a specific customer ... ");
        customerService.deleteCustomer(id);

        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Alterar informações de um cliente")
    @PutMapping(value = "/{id}", produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<?> updateCustomer(@PathVariable("id") Integer id,
                                            @Valid @RequestBody CustomerRequest customerRequest){
        LOGGER.info("Updating a specific customer ... ");

        customerService.updateCustomer(getCustomer(id, customerRequest.getName(), customerRequest.getSurname(),
                toDate(customerRequest.getBirthDate()), customerRequest.getGender(), customerRequest.getAdress(),
                customerRequest.getPhones()));

        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Cadastrar um cliente")
    @PostMapping(produces = "application/json", headers = "Accept=application/json" )
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest customerRequest){
        LOGGER.info("Creating a specific customer ... ");

        //Primeiro persiste o cliente
        Customer customer = customerService.createCustomer(new Customer(customerRequest.getName(), customerRequest.getSurname(),
                toDate(customerRequest.getBirthDate()), customerRequest.getGender(), toModelPhones(customerRequest.getPhones())));

        //Em seguida persiste os endereços do cliente, após ter obtido o seu id
        customerService.updateCustomer(getCustomer(customer.getId(), customer.getName(), customer.getSurname(),
                customer.getBirthDate(), customer.getGender(), customerRequest.getAdress(), customerRequest.getPhones()));

        customer.setAddress(toModelAddresses(customer.getId(), customerRequest.getAdress()));

        return ResponseEntity
                .created(URI.create(String.format("%s/%s", "/microservices/v1/customer", customer.getId())))
                .body(getCustomerResponse(customer));
    }

    private Customer getCustomer(Integer id, String name, String surname, Date birthDate, char gender,
                                 List<AddressDTO> addresses, List<PhoneDTO> phones) {
        return new Customer(id, name, surname, birthDate, gender, toModelAddresses(id, addresses), toModelPhones(phones));
    }

    private CustomerResponse getCustomerResponse(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getSurname(),
                toDateString(customer.getBirthDate()),
                customer.getGender(),
                toAddressDTO(customer.getAddress()),
                toPhoneDTO(customer.getPhones()));
    }

    private List<PhoneDTO> toPhoneDTO(Set<String> phonesModel) {
        List<PhoneDTO> phonesDTO = new ArrayList<>();
        phonesModel.forEach(phones -> phonesDTO.add(new PhoneDTO(phones)));
        return phonesDTO;
    }

    private Set<String> toModelPhones(List<PhoneDTO> phonesDTO) {
        Set<String> phonesModel = new HashSet<>();
        phonesDTO.forEach(phones -> phonesModel.add(phones.getNumber()));
        return phonesModel;
    }

    private List<AddressDTO> toAddressDTO(Set<Address> addresses) {
        List<AddressDTO> addressDTO = new ArrayList<>();
        addresses.forEach(address -> addressDTO.add(new AddressDTO(
                address.getStreet(), address.getNumber(), address.getComplement(), address.getPostalCode(),
                address.getCity(), address.getProvince(), address.getCountry(), address.getType().getCode()
        )));
        return addressDTO;
    }

    private Set<Address> toModelAddresses(Integer id, List<AddressDTO> addressDTO) {
        Customer customer = this.customerService.getCustomerById(id);
        Set<Address> addressModel = new HashSet<>();
        addressDTO.forEach(address -> addressModel.add(new Address(address.getStreetName(),
                address.getNumber(), address.getComplement(), address.getPostalCode(), address.getCity(),
                address.getProvince(), address.getCountry(), customer, AddressType.toEnum(address.getType())
        )));
        return addressModel;
    }
}