package bedu.org.budget_calculator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bedu.org.budget_calculator.dto.client.ClientDTO;
import bedu.org.budget_calculator.dto.client.CreateClientDTO;
import bedu.org.budget_calculator.dto.client.UpdateClientDTO;
import bedu.org.budget_calculator.exception.client.ClientNotFoundException;
import bedu.org.budget_calculator.mapper.ClientMapper;
import bedu.org.budget_calculator.model.Client;
import bedu.org.budget_calculator.repository.ClientRepository;


import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository repository;

    @Autowired
    private ClientMapper mapper;

    public List<ClientDTO> findAll() {
        List<Client> data = repository.findAll();
        return data.stream().map(mapper::toDTO).toList();
    }

    public ClientDTO findById(Long id) throws ClientNotFoundException {
        Optional<Client> result = repository.findById(id);

        if (result.isPresent()) {
            return mapper.toDTO(result.get());
        } else {
            throw new ClientNotFoundException(id);
        }
    }

    public ClientDTO save(CreateClientDTO data) {
        Client entity = repository.save(mapper.toModel(data));
        return mapper.toDTO(entity);
    }

    public ClientDTO update(Long id, UpdateClientDTO data) throws ClientNotFoundException {
        Client existingCliente = repository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));

        existingCliente.setName(data.getName());
        existingCliente.setLastname(data.getLastname());
        existingCliente.setPhone(data.getPhone());
        existingCliente.setEmail(data.getEmail());

        Client updatedCliente = repository.save(existingCliente);

        return mapper.toDTO(updatedCliente);
    }

    public void deleteById(Long id) throws ClientNotFoundException {
        Client existingClient = repository.findById(id)
                .orElseThrow(() -> new ClientNotFoundException(id));
        repository.deleteById(id);
    }
}
