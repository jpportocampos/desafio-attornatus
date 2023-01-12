package io.github.jpportocampos.domain.repository;

import io.github.jpportocampos.domain.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface Pessoas extends JpaRepository<Pessoa, Integer> {
    @Query("select p from Pessoa p left join fetch p.enderecos where p.id = :id")
    Optional<Pessoa> findByIdFetchItens (@Param("id") Integer id);
}
