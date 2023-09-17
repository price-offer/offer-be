package com.offer.post.domain.sort;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SortItemRepository extends JpaRepository<SortItem, Long> {

    SortItem findByName(String name);
}
