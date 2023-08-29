package hello.login.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.argumentresolver.Login;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

	private final MemberRepository memberRepository;
	private final SessionManager sessionManager;
	
//    @GetMapping("/")
    public String home() {
        return "/home";
    }
    
//    @GetMapping("/")
    public String homeLoginV1(@CookieValue(name="memberId", required = false) Long memberId, Model model) {
    	//@CookieValue 어노테이션으로 간편하게 해당 쿠키를 가져올 수 있다.
    	//HttpServletRequest로도 가능하다
    	
    	if(memberId == null) {
    		return "/home";
    	}
    	
    	Member loginMember = memberRepository.findById(memberId);
    	if(loginMember == null) {
    		return "/home";
    	}
    	
    	model.addAttribute("member", loginMember);
    	return "/loginHome";
    }
//    @GetMapping("/")
    public String homeLoginV2(HttpServletRequest request, Model model) {
    	
    	// 세션 관리자에 저장된 회원 정보 조회
    	Member member = (Member)sessionManager.getSession(request);
    	
    	if(member == null) {
    		return "/home";
    	}
    	
    	model.addAttribute("member", member);
    	return "/loginHome";
    }
//    @GetMapping("/")
    public String homeLoginV3(HttpServletRequest request, Model model) {
    	
    	HttpSession session = request.getSession(false);
    	if(session == null) {
    		return "/home";
    	}
    	
    	Member loginMember = (Member)session.getAttribute(SessionConst.LOGIN_MEMBER);
    	
    	// 세션에 회원 데이터가 없으면 home
    	if(loginMember == null) {
    		return "/home";
    	}
    	
    	model.addAttribute("member", loginMember);
    	return "/loginHome";
    }
//    @GetMapping("/")
    public String homeLoginV3String(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model) {
    	// @SessionAttribute는 request.getSession(false)처럼 새로운 세션을 생성하지 않는다.
    	
    	// 세션에 회원 데이터가 없으면 home
    	if(loginMember == null) {
    		return "/home";
    	}
    	
    	model.addAttribute("member", loginMember);
    	return "/loginHome";
    }
    @GetMapping("/")
    public String homeLoginV3ArgumentResolver(@Login Member loginMember, Model model) {
    	// 직접 만든 어노테이션 Login
    	if(loginMember == null) {
    		return "/home";
    	}
    	
    	model.addAttribute("member", loginMember);
    	return "/loginHome";
    }
}