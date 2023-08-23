package hello.servlet.web.servlet.servletmvc.v3;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;

@Controller
@RequestMapping("/springmvc/v3/members")
public class SpringMemberControllerV3 {

	private MemberRepository memberRepository = MemberRepository.getInstance();
	
//	@RequestMapping(value="/new-form", method = RequestMethod.GET)
	// 위에 한줄을 줄여서
	@GetMapping("/new-form")
	public String newForm() {
		return "new-from";
	}
	
//	@RequestMapping(value="/save", method=RequestMethod.POST)
	@PostMapping("/save")
	public String save(
			@RequestParam("username") String username,
			/*String username = request.getParameter("username") 과 같은 코드*/
			@RequestParam("age") int age,
			/*int age = Integer.parseInt(request.getParameter("age"))와 같은 코드*/
			/*형 변환도 해준다.*/
			Model model) {
		// 매개변수에서 처리하기 때문에 필요가 없어진다.
//		String username = request.getParameter("username");
//		int age = Integer.parseInt(request.getParameter("age"));
		
		Member member = new Member(username, age);
		memberRepository.save(member);
		
		model.addAttribute("member", member);
		
//		ModelAndView mv = new ModelAndView("save-result");
//		mv.addObject("member", member);
		return "save-result";
	}
	
//	@RequestMapping(method = RequestMethod.GET)
	@GetMapping
	public String members(Model model) {
		List<Member> members = memberRepository.findAll();
		ModelAndView mv = new ModelAndView("members");
		model.addAttribute("members", members);
		return "members";
	}
}
