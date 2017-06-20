package com.gb.statistics.webservice.repository;

import com.gb.statistics.webservice.entity.PersonPageRank;
import com.gb.statistics.webservice.entity.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

/**
 * Created by Kogut Sergey on 19.06.17.
 */
public interface RankRepository extends JpaRepository<PersonPageRank,Long> {

    @Modifying
    Site update(Site site);
}
