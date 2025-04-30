package com.parking.system.resident.filter;

import com.parking.system.resident.data.Resident;
import com.parking.system.resident.dto.ResidentFilterRequest;
import com.parking.system.residentcommunity.data.ResidentCommunity;
import jakarta.persistence.criteria.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
