package com.dev.gym.memberships.internal;

import com.dev.gym.memberships.Member;
import com.dev.gym.memberships.MemberRegisteredEvent;
import com.dev.gym.memberships.MemberRepository;
import com.dev.gym.memberships.dto.MemberRequest;
import com.dev.gym.memberships.dto.MemberResponse;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MembershipService {

    private final MemberRepository memberRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public void registerMember(MemberRequest request) {

        Member member = new Member(null, request.name(), request.email() , request.planType(), true);
        memberRepository.save(member);

        //lanza el primer evento
        applicationEventPublisher.publishEvent(new MemberRegisteredEvent(
                member.getId(),
                member.getEmail(),
                member.getPlanType().name()
        ));
    }

    @Transactional(readOnly = true)
    public List<MemberResponse> getAllMembers() {
        return memberRepository.findAll().stream()
                .map(m -> new MemberResponse(
                        m.getId(),
                        m.getName(),
                        m.getEmail(),
                        m.getPlanType(),
                        m.isActive()
                )).toList();
    }
}