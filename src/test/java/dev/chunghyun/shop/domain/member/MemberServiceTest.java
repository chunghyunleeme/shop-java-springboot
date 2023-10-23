package dev.chunghyun.shop.domain.member;

import dev.chunghyun.shop.member.Member;
import dev.chunghyun.shop.member.MemberRepository;
import dev.chunghyun.shop.member.MemberService;
import dev.chunghyun.shop.member.dto.MemberSaveRequestDto;
import dev.chunghyun.shop.exception.DuplicateMemberException;
import jakarta.persistence.EntityManager;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.Test;


@ActiveProfiles("test")
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberServiceTest {
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager em;

    @Test
    public void 회원가입() throws Exception {
        //given
        String memberName = "lee";
        String password = "test";

        MemberSaveRequestDto requestDto = new MemberSaveRequestDto(memberName, password);
        //when
        Long savedId = memberService.join(requestDto);

        //then
        Optional<Member> savedMember = memberRepository.findById(savedId);
        assertEquals(savedMember.get().getName(), memberName);
    }

    @Test(expected = DuplicateMemberException.class)
    public void 중복_회원_예외() throws DuplicateMemberException {
        //given
        String memberName = "lee";
        String password = "test";

        MemberSaveRequestDto requestDto = new MemberSaveRequestDto(memberName, password);
        MemberSaveRequestDto requestDto2 = new MemberSaveRequestDto(memberName, password);

        //when
        memberService.join(requestDto);
        memberService.join(requestDto2);

        //then
        fail("예외가 발생해야 한다.");
    }
}
