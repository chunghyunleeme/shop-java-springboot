package dev.chunghyun.shop.member.api;

import dev.chunghyun.shop.member.application.CreateMemberRequest;
import dev.chunghyun.shop.member.application.MemberService;
import dev.chunghyun.shop.member.application.UpdateMemberRequest;
import dev.chunghyun.shop.member.domain.Member;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class MemberContorller {
    private final MemberService memberService;

    @PostMapping("/api/v1/members")
    public CreateMemberResponse saveMember(@RequestBody @Valid CreateMemberRequest request) {
        Long id = memberService.join(request);
        return new CreateMemberResponse(id);
    }

    @RequiredArgsConstructor
    @Data
    static class CreateMemberResponse {
        private final Long id;
    }

    @PatchMapping("/api/v1/members")
    public UpdateMemberResponse updatemember(@PathVariable("id") Long id, @RequestBody @Valid UpdateMemberRequest request) {
        Long memberId = memberService.update(id, request);
        Member member = memberService.getMember(memberId);
        return new UpdateMemberResponse(id, member.getName());
    }

    @RequiredArgsConstructor
    @Data
    static class UpdateMemberResponse {
        private final Long id;
        private final String name;
    }

    @GetMapping("/api/v1/members")
    public Result getMembers(){
        List<Member> memberList = memberService.getAllMember();
        memberList.stream()
                .map(m -> new MemberDto(m.getName()))
                .collect(Collectors.toList());

        return new Result(memberList);
    }

    @RequiredArgsConstructor
    @Data
    static class Result<T> {
        private final T data;
    }

    @RequiredArgsConstructor
    @Data
    static class MemberDto {
        private final String name;
    }
}
