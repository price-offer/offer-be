package com.offer.post.domain.sort;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class SortItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "exposure_title")
    private String exposureTitle;

    @ManyToOne
    @JoinColumn(name = "sort_group_id")
    private SortGroup sortGroup;

    public SortItem(String name, String exposureTitle) {
        this.name = name;
        this.exposureTitle = exposureTitle;
    }

    public void setSortGroup(SortGroup sortGroup) {
        this.sortGroup = sortGroup;
    }
}
