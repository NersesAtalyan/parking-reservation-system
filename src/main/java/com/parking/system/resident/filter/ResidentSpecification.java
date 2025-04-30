package com.parking.system.resident.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.parking.system.resident.data.Resident;
import com.parking.system.resident.dto.ResidentFilterRequest;
import com.parking.system.residentcommunity.data.ResidentCommunity;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResidentSpecification {

    private static final String EMAIL = "email";
    private static final String NAME = "name";

    public static Specification<Resident> byFilter(ResidentFilterRequest filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            Optional.ofNullable(filter.name())
                    .ifPresent(name -> predicates.add(cb.like(cb.lower(root.get(NAME)), "%" + name.toLowerCase() + "%")));

            Optional.ofNullable(filter.email())
                    .ifPresent(email -> predicates.add(cb.like(cb.lower(root.get(EMAIL)), "%" + email.toLowerCase() + "%")));

            Optional.ofNullable(filter.active())
                    .ifPresent(active -> predicates.add(cb.equal(root.get("active"), active)));

            Optional.ofNullable(filter.communityId())
                    .ifPresent(communityId -> {
                        Join<Resident, ResidentCommunity> join = root.join("communities", JoinType.INNER);
                        predicates.add(cb.and(
                                cb.equal(join.get("community").get("id"), communityId),
                                cb.isTrue(join.get("active"))
                        ));
                    });

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
