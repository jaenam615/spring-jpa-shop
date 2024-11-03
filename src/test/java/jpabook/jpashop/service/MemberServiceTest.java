package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class) // JUnit 시작할 때 스프링이랑 시작하겠다는 어노테이션
@SpringBootTest // 없으면 Autowired 실패
@Transactional // 테스트 후 롤백
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception {
        //given: 이게 주어졌을 때
        Member member = new Member();
        member.setName("kim");

        //when: 이렇게 하면
        Long savedId = memberService.join(member);

        //then: 이렇게 된다
        assertEquals(member, memberRepository.findOne(savedId));
    }
    
    @Test(expected = IllegalStateException.class)
    public void 회원_중복_방지() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.join(member);
        memberService.join(member2);


        //then
        fail("예외가 발생해야 합니다.");
    }
}