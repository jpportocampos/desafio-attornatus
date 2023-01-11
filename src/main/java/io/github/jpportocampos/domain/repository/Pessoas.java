package io.github.jpportocampos.domain.repository;

import io.github.jpportocampos.domain.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Pessoas extends JpaRepository<Pessoa, Integer> {
}
