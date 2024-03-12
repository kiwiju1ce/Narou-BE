package ssafy.narou.pjt.like.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Repository
public class LikeRepositoryImpl implements LikeRepositoryCustom{
}
