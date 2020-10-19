package com.plugato.api_rest_gazin;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Long>{

    @Query( value = "SELECT * FROM developer d WHERE " +
            "(  ?1 IS NOT NULL  AND  d.id=?1    ) OR " +
            "(  ?2 IS NOT NULL  AND  d.nome like ?2  ) OR " +
            "(  ?3 IS NOT NULL  AND  d.sexo like ?3  ) OR " +
            "(  ?4 IS NOT NULL  AND  d.idade=?4 ) OR " +
            "(  ?5 IS NOT NULL  AND  d.hobby like ?5 ) OR " +
            "(  ?6 IS NOT NULL  AND  d.datanascimento=?6 )  "
            , nativeQuery = true )
    Page<Developer> queryWhere(Long id, String nome, String sexo, int idade, String hobby, Date dataNascimento, Pageable pageable);

}