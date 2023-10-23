package dev.chunghyun.shop.member;

import dev.chunghyun.shop.member.dto.MemberSaveRequestDto;
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
    public Long join(MemberSaveRequestDto requestDto) {
        validateDuplicateName(requestDto.name);

        Member member = Member.builder()
                .name(requestDto.name)
                .password(requestDto.password)
                .build();

        memberRepository.save(member);
        return member.getId();
    }

    public List<Member> getAllMember() {
     return memberRepository.findAll();
    }

    private Member getMember(Long id) {
        Optional<Member> member = memberRepository.findById(id);
        if(!member.isPresent()) throw new RuntimeException();
        return member.get();
    }

    private Member getMemberByName(String name) {
        return memberRepository.findOneByName(name);
    }

    private void validateDuplicateName(String name) {
        Member member = getMemberByName(name);
        boolean alreadyExistName = member != null;
        if(alreadyExistName) throw new DuplicateMemberException();
    }
}
