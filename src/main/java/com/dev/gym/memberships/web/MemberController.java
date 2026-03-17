package com.dev.gym.memberships.web;

import com.dev.gym.memberships.dto.MemberResponse;
import com.dev.gym.memberships.internal.MembershipService;
import com.dev.gym.memberships.dto.MemberRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MembershipService membershipService;

    @PostMapping
    public ResponseEntity<Void> createMember(@Valid @RequestBody MemberRequest request) {
        membershipService.registerMember(request);
       return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<MemberResponse>> getMembers(){
        List<MemberResponse> members = membershipService.getAllMembers();
        if(members.isEmpty()){return ResponseEntity.noContent().build();}
        return ResponseEntity.ok(members);
    }

}