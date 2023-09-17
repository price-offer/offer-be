package com.offer.post.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.offer.post.domain.sort.SortItem;
import com.offer.post.domain.sort.SortItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class SortItemRepositoryTest {

    @Autowired
    private SortItemRepository sut;

    @Test
    void findByName() {
        // given
        String name = "RECENT_CREATED";

        SortItem saved = sut.save(new SortItem("RECENT_CREATED", "최신순"));

        // when
        SortItem found = sut.findByName(name);

        // then
        assertThat(found).isEqualTo(saved);
    }

}
