package com.plugato.api_rest_gazin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DeveloperService {

    @Autowired(required = true)
    DeveloperRepository developerRepository;

    public DeveloperResponseDTO save(DeveloperRequestDTO developerRequestDTO) {
        Developer developer = developerRepository.save(developerRequestDTO.transformToObject());

        return DeveloperResponseDTO.transformToDTO(developer);
    }

    public URI createURI(DeveloperResponseDTO developerResponseDTO) {

        return ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/developers").path("/{id}")
                .buildAndExpand(developerResponseDTO.getId()).toUri();
    }

    public DeveloperResponseDTO delete(Long id) {
        Optional<Developer> developerDelete = developerRepository.findById(id);

        if (developerDelete.isPresent())
            developerRepository.deleteById(id);

        DeveloperResponseDTO developerResponseDTO = DeveloperResponseDTO.transformToDTO(developerDelete.get());
        return developerResponseDTO;
    }

    public Page<Developer> listDeveloper(Map<String, String> allParams, PageRequest pageRequest) {
        Developer developerFilter = new Developer();
        Page<Developer> developerList;

        if (fillFilter(developerFilter, allParams)) {
            developerList = developerRepository.queryWhere(developerFilter.getId(),
                    developerFilter.getNome(),
                    developerFilter.getSexo(),
                    developerFilter.getIdade(),
                    developerFilter.getHobby(),
                    developerFilter.getDatanascimento(), pageRequest);
        } else {
            developerList = developerRepository.findAll(pageRequest);
        }

        return developerList;
    }

    public Iterable<Developer> listAllDeveloper() {
        return developerRepository.findAll();
    }

    public DeveloperResponseDTO getById(Long id) {
        Optional<Developer> developerFind = developerRepository.findById(id);
        return DeveloperResponseDTO.transformToDTO(developerFind.get());

    }

    public DeveloperResponseDTO modeifyDeveloper(Long id, DeveloperRequestDTO developerRequestDTO) {

        Developer develperObj = developerRequestDTO.transformToObject();

        develperObj.setId(id);
        Optional<Developer> developerFind = developerRepository.findById(id);
        if (!developerFind.isPresent())
            return new DeveloperResponseDTO();

        return DeveloperResponseDTO.transformToDTO(developerRepository.save(develperObj));

    }

    public Boolean fillFilter(Developer developerFilter, Map<String, String> allParams) {

        for (Map.Entry<String, String> entry : allParams.entrySet()) {

            if (entry.getKey().equals("id")) {
                developerFilter.setId(Long.parseLong(entry.getValue()));
            }
            if (entry.getKey().equals("nome")) {
                developerFilter.setNome(entry.getValue());
            }
            if (entry.getKey().equals("sexo")) {
                developerFilter.setSexo(entry.getValue());
            }
            if (entry.getKey().equals("idade")) {
                developerFilter.setIdade(Integer.parseInt(entry.getValue()));
            }
            if (entry.getKey().equals("hobby")) {
                developerFilter.setHobby(entry.getValue());
            }
            if (entry.getKey().equals("datanascimento")) {
                //TODO:estava invertendo depois que troquei para date sql
                //                    String dataString = entry.getValue();
                //                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                developerFilter.setDatanascimento(Date.valueOf(entry.getValue()));
            }

        }
        Developer teste = new Developer();
        return !developerFilter.equals(teste);
    }

    public Iterable<Developer> ControllParameterPageable(Map<String, String> allParams) {
        int page = -1;
        int size = -1;

        for (Map.Entry<String, String> entry : allParams.entrySet()) {
            if (entry.getKey().equals("page")) {
                page = Integer.parseInt(entry.getValue());
            }
            if (entry.getKey().equals("size")) {
                size = Integer.parseInt(entry.getValue());
            }
        }

        allParams.remove("page");
        allParams.remove("size");

        if (allParams.size() < 1 && size < 0)
            return listAllDeveloper();


        if (size < 0) {
            size = 10;
            page = 0;
        }

        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.ASC,
                "nome");

        return PageableMakeResponseList(allParams, pageRequest, size);
    }

    public PageImpl PageableMakeResponseList(Map<String, String> allParams, PageRequest pageRequest, int size) {

        return new PageImpl<>(
                makeResponseList(listDeveloper(allParams, pageRequest)),
                pageRequest,
                size);
    }

    public List<DeveloperResponseDTO> makeResponseList(Page<Developer> developerList) {

        List<DeveloperResponseDTO> developerResponseList = new ArrayList<>();
        for (Developer list : developerList) {
            developerResponseList.add(DeveloperResponseDTO.transformToDTO(list));
        }

        return developerResponseList;
    }


}
