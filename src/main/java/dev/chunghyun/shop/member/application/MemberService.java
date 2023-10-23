package dev.chunghyun.shop.member.application;

import dev.chunghyun.shop.exception.SoldOutException;
import dev.chunghyun.shop.member.domain.MemberRepository;
import dev.chunghyun.shop.member.domain.Member;
import dev.chunghyun.shop.exception.DuplicateMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public Long join(CreateMemberRequest request) {
        validateDuplicateName(request.getName());

        Member member = Member.builder()
                .name(request.getName())
                .password(request.getPassword())
                .build();

        memberRepository.save(member);
        return member.getId();
    }

    @Transactional
    public Long update(Long id, UpdateMemberRequest request) {
        System.out.println("request::" + request.toString());
        Member member = getMember(id);
        member.update(request.getName());
        return id;
    }

    public List<Member> getAllMember() {
     return memberRepository.findAll();
    }

    public Member getMember(Long id) {
        Optional<Member> member = memberRepository.findOneById(id);
        if(!member.isPresent()) throw new RuntimeException("존재하지 않는 유저입니다.");
        return member.get();
    }

    private Optional<Member> getMemberByName(String name) {
        return memberRepository.findByName(name);
    }

    private void validateDuplicateName(String name) {
        Optional<Member> member = getMemberByName(name);
        boolean alreadyExistName = member.isPresent();
        if(alreadyExistName) throw new DuplicateMemberException();
    }
}
