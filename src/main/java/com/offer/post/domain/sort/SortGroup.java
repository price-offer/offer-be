package com.offer.post.domain.sort;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sort_group")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SortGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private SortType sortType;

    @OneToMany(mappedBy = "sortGroup")
    private List<SortItem> sortItems = new ArrayList<>();

    public SortGroup(SortType sortType) {
        this.sortType = sortType;
    }

    public void addSortItem(SortItem sortItem) {
        sortItems.add(sortItem);
        sortItem.setSortGroup(this);
    }
}
