package com.offer.msg.domain;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MsgRepository extends JpaRepository<Msg, Long> {
    @Override
    <S extends Msg> S save(S entity);

    Slice<Msg> findSliceByRoomIdOrderByCreatedAtAsc(PageRequest pageRequest, Long roomId);

    List<Msg> findAllByRoomAndSenderId(MsgRoom room, Long sellerId);

}
