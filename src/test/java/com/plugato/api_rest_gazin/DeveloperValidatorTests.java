package com.plugato.api_rest_gazin;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.sql.Date;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DeveloperValidatorTests {

    @Mock
    private DeveloperRepository developerRepository;

    @InjectMocks
    private DeveloperService developerService;


    @Test
    public void contextLoads() {
    }

    @Test
    public void getAllPageableAndFilter() {

        Developer developerFilter = new DeveloperRequestDTO().transformToObject();

        PageRequest pageRequest = PageRequest.of(
                0,
                10,
                Sort.Direction.ASC,
                "nome");

        List<Developer> developerList = Arrays.asList(MockDeveloper(),
                MockDeveloper(),
                MockDeveloper(),
                MockDeveloper(),
                MockDeveloper());

        Page<Developer> pages = new PageImpl<Developer>(developerList, pageRequest, developerList.size());

        Map<String, String> allParams = new HashMap<String, String>();
        allParams.put("sexo", new String("f"));
        developerFilter.setSexo("f");

        when(developerRepository.queryWhere(developerFilter.getId(), developerFilter.getNome(), developerFilter.getSexo(),
                developerFilter.getIdade(), developerFilter.getHobby(), developerFilter.getDatanascimento(), pageRequest))
                .thenReturn(pages);

        when(developerRepository.findAll()).thenReturn(developerList);
        ;

        Page<Developer> pageImpl = (Page<Developer>) developerService.ControllParameterPageable(allParams);

        assertEquals(pageImpl.getContent().size(), developerList.size());

    }

    @Test
    public void getAllNotPageable() {

        Developer developerFilter = new DeveloperRequestDTO().transformToObject();

        PageRequest pageRequest = PageRequest.of(
                0,
                10,
                Sort.Direction.ASC,
                "nome");

        List<Developer> developerList = Arrays.asList(MockDeveloper(),
                MockDeveloper(),
                MockDeveloper(),
                MockDeveloper(),
                MockDeveloper());

        Page<Developer> pages = new PageImpl<Developer>(developerList, pageRequest, developerList.size());

        Map<String, String> allParams = new HashMap<String, String>();

        when(developerRepository.queryWhere(developerFilter.getId(), developerFilter.getNome(), developerFilter.getSexo(),
                developerFilter.getIdade(), developerFilter.getHobby(), developerFilter.getDatanascimento(), pageRequest))
                .thenReturn(pages);

        when(developerRepository.findAll()).thenReturn(developerList);
        ;

        List<Developer> pageImpl = (List<Developer>) developerService.ControllParameterPageable(allParams);

        assertEquals(pageImpl.size(), developerList.size());

    }

    @Test
    public void saveTest() {

        when(developerRepository.save(MockDeveloperRequestDTO().transformToObject())).thenReturn(MockDeveloper());

        developerService.save(MockDeveloperRequestDTO());

        assertEquals(MockDeveloperRequestDTO().getNome(), MockDeveloper().getNome());

    }

    @Test
    public void getByIdTest() throws Exception {
        Long id = 55L;

        when(developerRepository.findById(id)).thenReturn(java.util.Optional.ofNullable(MockDeveloper()));

        DeveloperResponseDTO seek = developerService.getById(id);

        Assert.assertSame(seek.getId(), id);

    }

    @Test
    public void putTest() throws Exception {

        Long id = 55L;

        Developer develperObj = MockDeveloperRequestDTO().transformToObject();

        when(developerRepository.findById(id)).thenReturn(java.util.Optional.ofNullable(MockDeveloper()));
        DeveloperResponseDTO seek = developerService.getById(id);//mudar para service e chamar direto

        when(developerRepository.save(MockDeveloperRequestDTO().transformToObject())).thenReturn(MockDeveloper());
        DeveloperResponseDTO developerResponseDTO = DeveloperResponseDTO.transformToDTO(developerRepository.save(develperObj));

        Assert.assertSame(seek.getId(), developerResponseDTO.getId());

    }

    @Test
    public void deleteTest() throws Exception {
        Long id = 55L;

        when(developerRepository.findById(id)).thenReturn(java.util.Optional.ofNullable(MockDeveloper()));

        DeveloperResponseDTO developerResponseDTO = developerService.delete(id);

        Assert.assertSame(developerResponseDTO.getId(), MockDeveloper().getId());

    }

    @Test
    public void postTest() throws Exception {

        Developer developer = MockDeveloper();
        developer.setNome("testePost");
        developer.setSexo("M");
        developer.setDatanascimento(Date.valueOf("1993-05-06"));

        when(developerRepository.save(developer)).thenReturn(developer);

        assertEquals(developer.getNome(), "testePost");
        assertEquals(developer.getSexo(), "M");
        assertEquals(developer.getDatanascimento(), Date.valueOf("1993-05-06"));

    }

    @Test
    public void postErroTest() throws Exception {

        Developer developer = MockDeveloper();
        developer.setNome("erro");

        when(developerRepository.save(developer)).thenReturn(developer);

        assertNotEquals(developer.getNome(), MockDeveloper().getNome());

    }

    public DeveloperResponseDTO MockDeveloperResponseDTO() {
        Developer developer = MockDeveloper();
        Long id = 55L;
        developer.setId(id);
        DeveloperResponseDTO developerResponseDTO = DeveloperResponseDTO.transformToDTO(developer);
        return developerResponseDTO;
    }

    public DeveloperRequestDTO MockDeveloperRequestDTO() {
        DeveloperRequestDTO developerRequestDTO = new DeveloperRequestDTO();
        developerRequestDTO.setNome("manoel ricardo");
        developerRequestDTO.setHobby("beber");
        developerRequestDTO.setIdade(27);
        developerRequestDTO.setSexo("M");
        developerRequestDTO.setDatanascimento(Date.valueOf("1993-06-05"));
        return developerRequestDTO;
    }

    public Developer MockDeveloper() {
        Developer developer = MockDeveloperRequestDTO().transformToObject();
        developer.setId(55L);
        developer.setNome("manoel ricardo");
        return developer;

    }

}