package com.example.repo;

import com.example.models.Props;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropsRepository extends JpaRepository<Props, Long> {

    Props getPropsById (long id);

}
