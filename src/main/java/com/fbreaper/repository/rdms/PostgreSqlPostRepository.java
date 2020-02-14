package com.fbreaper.repository.rdms;

import com.fbreaper.domain.PostDto;
import org.springframework.data.repository.CrudRepository;

public interface PostgreSqlPostRepository extends CrudRepository<PostDto, Integer> {

}
