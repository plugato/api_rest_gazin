package com.plugato.api_rest_gazin;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.*;

import java.sql.Date;
import java.util.*;

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
    public void listDeveloper() {



        Developer developerFilter = FakeDeveloper();
        Pageable pageable;

        PageRequest pageRequest = PageRequest.of(
                0,
                10,
                Sort.Direction.ASC,
                "nome");

        List<Developer> developerList =  Arrays.asList( FakeDeveloper(),
                                                        FakeDeveloper(),
                                                        FakeDeveloper(),
                                                        FakeDeveloper(),
                                                        FakeDeveloper() );

        Page<Developer> pages = new PageImpl<Developer>(developerList, pageRequest, developerList.size());

        when(developerRepository.queryWhere( developerFilter.getId(), developerFilter.getNome(), developerFilter.getSexo(),
                developerFilter.getIdade(), developerFilter.getHobby(), developerFilter.getDatanascimento(), pageRequest ) )
                .thenReturn( pages );

        when(developerRepository.findAll(pageRequest) ).thenReturn( pages );;

        Map<String,String> allParams = new HashMap<String,String>();
        //allParams.put( "sexo", new String( "m" ));

        PageImpl pageImpl = developerService.PageableMakeResponseList( allParams, pageRequest, 10 );
        assertEquals( pageImpl.getNumberOfElements(), developerList.size() );
    }


    @Test
    public void saveTest() {


        when(developerRepository.save( FakeDeveloperRequestDTO().transformToObject() ) ).thenReturn(FakeDeveloper());

        developerService.save( FakeDeveloperRequestDTO() );
        assertEquals(FakeDeveloperRequestDTO().getNome(), FakeDeveloper().getNome() );
    }


    @Test
    public void getByIdTest() throws Exception
    {
        Long id = 55L;

        when(developerRepository.findById(id)).thenReturn(java.util.Optional.ofNullable(FakeDeveloper()));
        DeveloperResponseDTO seek = developerService.getById( id );

        Assert.assertSame(  seek.getId(), id );

    }

    @Test
    public void putTest() throws Exception {

        Long id = 55L;

        when( developerRepository.save( FakeDeveloper() ) ).thenReturn( FakeDeveloper() );
        DeveloperResponseDTO developerResponseDTO = developerService.modeifyDeveloper( id, FakeDeveloperRequestDTO() );

        Assert.assertSame( developerResponseDTO.getNome(), FakeDeveloper().getNome()  );
    }

    @Test
    public void deleteTest() throws Exception
    {
        Long id = 55L;

        when( developerRepository.findById( id ) ).thenReturn(java.util.Optional.ofNullable(FakeDeveloper()));
        DeveloperResponseDTO developerResponseDTO = developerService.delete( id );

        Assert.assertSame( developerResponseDTO.getId(), FakeDeveloper().getId() );
   }

   @Test
   public void postTest() throws Exception {

        Developer developer = FakeDeveloper();
        developer.setNome("testePost");
        developer.setSexo("M");
        developer.setDatanascimento(Date.valueOf("1993-05-06"));

        when( developerRepository.save( developer ) ).thenReturn( developer );

        assertEquals( developer.getNome(), "testePost" );
        assertEquals( developer.getSexo(), "M" );
        assertEquals( developer.getDatanascimento(), Date.valueOf("1993-05-06") );

   }

    @Test
    public void postErroTest() throws Exception {

        Developer developer = FakeDeveloper();
        developer.setNome("erro");

        when( developerRepository.save( developer ) ).thenReturn( developer );

        assertNotEquals( developer.getNome(), FakeDeveloper().getNome() );

    }

    public DeveloperResponseDTO FakeDeveloperResponseDTO() {
        Developer developer = FakeDeveloper();
        Long id = 55L;
        developer.setId(id);
        DeveloperResponseDTO developerResponseDTO = DeveloperResponseDTO.transformToDTO(developer);
        return developerResponseDTO;
    }
    public DeveloperRequestDTO FakeDeveloperRequestDTO(){
        DeveloperRequestDTO developerRequestDTO = new DeveloperRequestDTO();
        developerRequestDTO.setNome("manoel ricardo");
        developerRequestDTO.setHobby("beber");
        developerRequestDTO.setIdade(27);
        developerRequestDTO.setSexo("M");
        developerRequestDTO.setDatanascimento(Date.valueOf("1993-06-05"));
        return developerRequestDTO;
    }
    public Developer FakeDeveloper(){
        Developer developer = FakeDeveloperRequestDTO().transformToObject();
        developer.setId( 55L );
        return  developer;

    }

}