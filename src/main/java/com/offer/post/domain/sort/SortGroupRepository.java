package com.offer.post.domain.sort;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SortGroupRepository extends JpaRepository<SortGroup, Long> {

    SortGroup findBySortType(SortType type);
}
